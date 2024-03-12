package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.ArrayList;
import interfaces.List;

import main.Election;


public class Election {
	int invalidBallots;
	int blankBallots;
	int totalBallots;
	Candidate winner = null;
	List<Candidate> candidates = new ArrayList<Candidate>();
	List<Ballot> validBallots = new ArrayList<Ballot>();
	List<Ballot> ballots = new ArrayList<Ballot>();
	List<String> eliminatedCandidates = new ArrayList<String>();
	List<List<Integer>> candidateRanks;
	int maxVotes;
	
	
	public Election() throws IOException {
		this("candidates.csv", "ballots.csv");
	};
	/* Constructor that receives the name of the candidate and ballot files and applies
	the election logic. Note: The files should be found in the input folder. */
	public Election(String candidates_filename, String ballot_filename) throws IOException {
		//reads the candidates information from the file
		BufferedReader brCF = new BufferedReader(new FileReader("inputFiles/" + candidates_filename));
		String dataCF;
		while((dataCF = brCF.readLine()) != null) {
			candidates.add(new Candidate(dataCF));
		}
		BufferedReader brBF = new BufferedReader(new FileReader("inputFiles/" + ballot_filename));
		String dataBF;
		while((dataBF = brBF.readLine()) != null) {
			Ballot ballot = new Ballot(dataBF,candidates);
			ballots.add(ballot);
			if (ballot.getBallotType() == 0) {
				validBallots.add(ballot);
			}
		}
		brCF.close();
		brBF.close();
		int mostVotesIndex;
		int currentRank;
		
		List<Candidate> upForElimination = new ArrayList<Candidate>();
		int roundNumber = 0;
		int maxVotes;
		List<Candidate> activeCandidates = new ArrayList<Candidate>();
		for (Candidate c: candidates) {
			activeCandidates.add(c);
		}
		//the rounds until a winner is declared
		while (winner == null) {	
			roundNumber++;
			//  [3, 4, 12]
			// check if winner given current votes
			List<Integer> currentVotes = computeScoresForRank(validBallots,activeCandidates,1);
			// find the candidate with the most votes
			maxVotes = 0;
			mostVotesIndex = 0;
			for (int idx = 0; idx < currentVotes.size(); idx++) {
				if (currentVotes.get(idx) > maxVotes){
					mostVotesIndex = idx;
					maxVotes = currentVotes.get(idx);
			
				}
			}
			// do they have more than 50% of votes?
			if (maxVotes > (validBallots.size() / 2.0)) {
				winner = candidates.get(mostVotesIndex);
				break;
			}
			
			// no? run elimination algorithm
				List<Candidate> candidatesInElimRound = new ArrayList<Candidate>();
				for (Candidate c: activeCandidates) {
					candidatesInElimRound.add(c);
				}
				currentRank = 1;
				while (currentRank <= activeCandidates.size()) {
					List<Integer> currentElimVotes = computeScoresForRank(validBallots,candidatesInElimRound,currentRank);
					// find what is the lowest number of votes for the current rank
					int leastVotes = validBallots.size()+1; 
					for (int idx = 0; idx< currentElimVotes.size(); idx++) {
						if (currentElimVotes.get(idx) < leastVotes){
							leastVotes = currentElimVotes.get(idx);
						}
					}
					// find what candidates have the lowest number of votes
					upForElimination.clear();
					for (int idx = 0; idx< currentElimVotes.size(); idx++) {
						if (currentElimVotes.get(idx) == leastVotes){
							upForElimination.add(candidatesInElimRound.get(idx));
						}
					}
					if (upForElimination.size() == 1) {
						// delete them and move on to next round
						eliminatedCandidates.add(upForElimination.get(0).getName() + "-" + computeScoreForRank(validBallots,upForElimination.get(0),1));
//						System.out.println(upForElimination.get(0).getName() + "-" + computeScoreForRank(validBallots,upForElimination.get(0),1));
						activeCandidates.remove(upForElimination.first());
						for (Ballot ballot: validBallots) {
							ballot.eliminate(upForElimination.first().getId());
						}
						break;
					} else { // move on to next rank with the candidates
						currentRank++;
						candidatesInElimRound.clear();
						for (Candidate c: upForElimination) {
							candidatesInElimRound.add(c);
						}
					}
				}
				if (currentRank > activeCandidates.size()){
					// ah fork, we have a tie all the way to the end, decide based on highest id
					Candidate toEliminate = upForElimination.first();
					for (int idx = 1; idx< upForElimination.size(); idx++) {
						if (upForElimination.get(idx).getId() > toEliminate.getId()) {
							toEliminate = upForElimination.get(idx);
						}
					}
					eliminatedCandidates.add(toEliminate.getName() + "-" + computeScoreForRank(validBallots,toEliminate,1));
//					System.out.println(toEliminate.getName() + "-" + computeScoreForRank(validBallots,toEliminate,1));
					activeCandidates.remove(toEliminate);
					for (Ballot ballot: validBallots) {
						ballot.eliminate(toEliminate.getId());
					}
				}
			}

		};
	private List<Integer> computeScoresForRank(List<Ballot> ballots, List<Candidate> candidates, int rank){
		List<Integer> currentVotes = new ArrayList<Integer>();
		for (Candidate c: candidates) {
			currentVotes.add(0);
		}
		// tally up the votes
		for (Ballot ballot: ballots) {
			int cId = ballot.getCandidateByRank(rank);
			for (int idx = 0; idx< candidates.size(); idx++) {
				if (candidates.get(idx).getId() == cId) {
					currentVotes.set(idx, currentVotes.get(idx)+1);
				}
			}
		}
		return currentVotes;
	}
	
	private int computeScoreForRank(List<Ballot> ballots, Candidate candidate, int rank){
		// tally up the votes
		int votes = 0;
		for (Ballot ballot: ballots) {
			if (ballot.getRankByCandidate(candidate.getId()) == rank) {
				votes++;
			}
		}
		return votes;
	}
	
	
	
	// returns the name of the winner of the election
	public String getWinner() {
//		System.out.println("winner = "+winner.getName());
		return winner.getName();
	};
	// returns the total amount of ballots submitted
	public int getTotalBallots() {
//		System.out.println("ballots at the end:" + ballots.size());
		return ballots.size();
		
	};
	// returns the total amount of invalid ballots
	public int getTotalInvalidBallots() {
		for(int i = 0; i < ballots.size(); i++) {
			if(ballots.get(i).getBallotType() == 2) {
				invalidBallots += 1;
			}
			
		}
//		System.out.println("invalid ballots at the end:" + invalidBallots);
		return invalidBallots;
	};
	// returns the total amount of blank ballots
	public int getTotalBlankBallots() {
		for(int i = 0; i < ballots.size(); i++) {
			if(ballots.get(i).getBallotType() == 1) {
				blankBallots += 1;
			}
			
		}
//		System.out.println("blank ballots at the end:" + blankBallots);
		return blankBallots;
	};
	
	
	// returns the total amount of valid ballots
	public int getTotalValidBallots() {
		return validBallots.size();
	};
	/* List of names for the eliminated candidates with the numbers of 1s they had,
	must be in order of elimination. Format should be <candidate name>-<number of 1s
	when eliminated>*/
	public List<String> getEliminatedCandidates(){
		return eliminatedCandidates;
	};
	
}
