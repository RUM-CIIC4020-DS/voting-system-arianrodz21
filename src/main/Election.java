package main;

import data_structures.ArrayList;
import interfaces.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.IOException;

import main.Election;

/**
 * Represents an Election with methods to conduct an election, tally votes, and determine a winner.
 */
public class Election {

	int totalBallots;
	int validBallots;
	int invalidBallots;
	int blankBallots;
	
	Candidate winner;

	List<Candidate> candidates = new ArrayList<Candidate>();
	List<String> eliminatedCandidates = new ArrayList<String>();
	List<List<Ballot>> ballots2dArray = new ArrayList<List<Ballot>>();

	/**
     * Constructor that implements the election logic using the files candidates.csv
     * and ballots.csv as input. (Default constructor)
     */
	public Election() {
		this("candidates.csv","ballots.csv");
	}
	
	/**
     * Constructor that receives the name of the candidate and ballot files and applies
     * the election logic. Note: The files should be found in the input folder.
     * @param candidates_filename The name of the file containing candidate information.
     * @param ballot_filename The name of the file containing ballot information.
     */
	public Election(String candidates_filename, String ballot_filename) {
		try {
			// goes through candidate file and extracts the information into the candidates list and determines the size of ballots2dArray
			BufferedReader brCF = new BufferedReader(new FileReader("inputFiles/" + candidates_filename));
			String dataCF;
			while((dataCF = brCF.readLine()) != null) {
				candidates.add(new Candidate(dataCF));
				ballots2dArray.add(new ArrayList<Ballot>());
			}
			// goes through ballots file and creates the ballots placing them into the ballots2dArray
			BufferedReader brBF = new BufferedReader(new FileReader("inputFiles/" + ballot_filename));
			String dataBF;
			while((dataBF = brBF.readLine()) != null) {
				Ballot ballot = new Ballot(dataBF, candidates);
				if(ballot.getBallotType() == 0) {
					int votedCandidate = ballot.getCandidateByRank(1);
					ballots2dArray.get(votedCandidate-1).add(ballot);
					validBallots++;
				}
				else if(ballot.getBallotType() == 1) {
					blankBallots++;
				}
				else if(ballot.getBallotType() == 2){
					invalidBallots++;
				}				
			}
			brCF.close();
			brBF.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
		// Election logic to determine the winner and eliminate candidates
		int mostVotesIndex = 0;
		int eliminationRank;
		int roundNumber = 0;
		int maxVotes;

		List<Integer> leastVotesIds = new ArrayList<Integer>(); //The ids of all the candidates with the least mount of votes

		while(winner == null) {
			roundNumber++;
			maxVotes = -1;
			mostVotesIndex = 0;
			for(int i = 0; i < ballots2dArray.size() ; i++) {
				if(ballots2dArray.get(i).size()>maxVotes) {
					mostVotesIndex = i;
					maxVotes = ballots2dArray.get(i).size();
				}
			}
			// checks if the candidate with the most votes has more than 50% of the votes
			if (maxVotes>(validBallots/2.0)) {
				winner = candidates.get(mostVotesIndex);
				break;
			}
			// if not, starts looking for which candidate to eliminate
			List<Integer> activeCandidates = new ArrayList<Integer>();
			for(Candidate c: candidates) {
				if (c.isActive()) {
					activeCandidates.add(c.getId());
				}
			}
			eliminationRank = 1; // in the case of a tie, we will go a rank lower and try again
			int least = validBallots+1;
			// looks for the lowest number of votes a candidate received 
			for(int i = 0 ; i < ballots2dArray.size(); i++) {
				if(ballots2dArray.get(i).size() < least && candidates.get(i).isActive()) {
					least = ballots2dArray.get(i).size();
				}
			}
			// Finds out what candidates have the least amount of votes
			leastVotesIds.clear();
			for(int i = 0; i < activeCandidates.size(); i++) {
				int candidateIndex = activeCandidates.get(i)-1;
				if(ballots2dArray.get(candidateIndex).size() == least) {
					leastVotesIds.add(activeCandidates.get(i));
				}
			}
			// if only one candidate has the lowest number of votes, we eliminate them.
			if(leastVotesIds.size() == 1) {
				int eliminatedId = leastVotesIds.first();
				Candidate eliminated = candidates.get(eliminatedId-1);
				eliminatedCandidates.add(eliminated.getName() + "-" + ballots2dArray.get(eliminatedId-1).size());
				eliminated.setActive(false);
				// eliminate the candidate only from the ballots they were ranked #1 and move the ballots to the right place in ballots2dArray
				for (Ballot ballot: ballots2dArray.get(eliminatedId-1)) {
					ballot.eliminate(eliminatedId);
					int topCandidate = ballot.getCandidateByRank(1);
					//  make sure we eliminate from the ballots until the candidate ranked #1 is an active candidate
					while (topCandidate != -1 && !candidates.get(topCandidate-1).isActive()) {
						ballot.eliminate(topCandidate);
						topCandidate = ballot.getCandidateByRank(1);
					}
					// move the ballot to the list it belongs to
					if (topCandidate != -1) {
						ballots2dArray.get(topCandidate-1).add(ballot);
					}
				}
				ballots2dArray.get(eliminatedId-1).clear();
				continue; // we go back to the top to see if we can find a winner
			}
			else { // move on to next rank with the candidates
				eliminationRank++;
				activeCandidates.clear();
				for (Integer id: leastVotesIds) {
					activeCandidates.add(id);
				}
			}
			
			// coudn't eliminate based on #1's, so we go a rank higher and try again
			while(eliminationRank <= activeCandidates.size()) {
				List<Integer> activeCandidatesVotes = tallyVotesFromRank(activeCandidates, eliminationRank);
				least = validBallots+1;
				for(int i = 0; i < activeCandidatesVotes.size();i++ ) {
					int candidateId = activeCandidates.get(i);
					if(candidates.get(candidateId-1).isActive() && activeCandidatesVotes.get(i) < least) {
						least = activeCandidatesVotes.get(i);
					}
				}
				// Find out what candidates have the least amount of votes of the eliminationRank
				leastVotesIds.clear();
				for(int i = 0; i < activeCandidates.size(); i++) {
					if(activeCandidatesVotes.get(i) == least) {
						leastVotesIds.add(activeCandidates.get(i));
					}
				}
				// if single candidate, eliminate
				if(leastVotesIds.size() == 1) {
					int eliminatedId = leastVotesIds.first();
					Candidate eliminated = candidates.get(eliminatedId-1);
					eliminatedCandidates.add(eliminated.getName() + "-" + ballots2dArray.get(eliminatedId-1).size());
					eliminated.setActive(false);
					// eliminate the candidate from the ballots where that candidate was ranked #1
					// and move them to the appropriate location in ballots2dArray
					for(Ballot ballot: ballots2dArray.get(eliminatedId-1)) {
						ballot.eliminate(eliminatedId);
						int topCandidate = ballot.getCandidateByRank(1);
						//  make sure we eliminate from the ballots until we are left with an active candidate at rank #1
						while(topCandidate != -1 && !candidates.get(topCandidate-1).isActive()) {
							ballot.eliminate(topCandidate);
							topCandidate = ballot.getCandidateByRank(1);
						}
						// move the ballot to the list it belongs to
						if (topCandidate != -1) {
							ballots2dArray.get(topCandidate-1).add(ballot);
						}
					}
					ballots2dArray.get(eliminatedId-1).clear();
					break;
				}
				else { // move on to next rank with the candidates
					eliminationRank++;
					activeCandidates.clear();
					for (Integer id: leastVotesIds) {
						activeCandidates.add(id);
					}
				}
			}
		
			// We couldn't tie break on votes, so now we try with Ids
			if(eliminationRank > activeCandidates.size()) {
				Integer deletedId = leastVotesIds.first();
				for(int i = 1; i < leastVotesIds.size(); i ++) {
					if(leastVotesIds.get(i) > deletedId) {
						deletedId = leastVotesIds.get(i);
					}
				}
				candidates.get(deletedId-1).setActive(false);
				eliminatedCandidates.add(candidates.get(deletedId-1).getName()+"-"+ ballots2dArray.get(deletedId-1).size());
				// Now eliminate the candidate
				for (Ballot ballot: ballots2dArray.get(deletedId-1)) {
					ballot.eliminate(deletedId);
					int topCandidate = ballot.getCandidateByRank(1);
					//  make sure we eliminate from the ballots until the rank 1 is an active candidate
					while (topCandidate != -1 && !candidates.get(topCandidate-1).isActive()) {
						ballot.eliminate(topCandidate);
						topCandidate = ballot.getCandidateByRank(1);
					}
					// move the ballot to the list it belongs to
					if (topCandidate != -1) {
						ballots2dArray.get(topCandidate-1).add(ballot);
					}
				}
				ballots2dArray.get(deletedId-1).clear();
			}
		}
		int winningVotes = ballots2dArray.get(mostVotesIndex).size();

		try {
			// print out the winner
			 String output = String.format("outputFiles/%s%d.txt", winner.getName(), winningVotes);
			 BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			 bw.write(String.format("Number of ballots: %d\n", getTotalBallots()));
	            bw.write(String.format("Number of blank ballots: %d\n", getTotalBlankBallots()));
	            bw.write(String.format("Number of invalid ballots: %d\n", getTotalInvalidBallots()));
	            for(int i = 1; i < roundNumber; i++) {
	            	String[] elim = eliminatedCandidates.get(i-1).split("-");
	            	bw.write(String.format("Round %d: %s was eliminated with %s #1's\n", i, elim[0], elim[1]));
	            }
	        	if(winner != null) {
	        		bw.write(String.format("Winner: %s wins with %d #1's", winner.getName(), winningVotes));
	        	}
	            bw.close();
//			 Constructing the output file path
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	// Helper method to tally votes for candidates based on a specific rank
    // Returns a list of votes for each candidate
	private List<Integer> tallyVotesFromRank(List<Integer> idsToFindVotesFor, int rank){
		List<Integer> votesForIds = new ArrayList<Integer>();
		for (Integer i: idsToFindVotesFor) {
			votesForIds.add(0);
		}
		for (List<Ballot> ballotList: ballots2dArray) {
			for (Ballot ballot: ballotList) {
				Integer id = ballot.getCandidateByRank(rank);
				if (id != -1 && candidates.get(id-1).isActive()) {
					Integer indexOfId = idsToFindVotesFor.firstIndex(id);
					if (indexOfId != -1) {
						Integer currentVotes = votesForIds.get(indexOfId);
						votesForIds.set(indexOfId, currentVotes+1);
					}

				}
				
			}
		}
		
		return votesForIds;
	}

	/**
     * Gets the name of the winner of the election.
     * @return The name of the winner.
     */
	public String getWinner() {
		return winner.getName();
	}
	
	/**
     * Gets the total amount of ballots submitted.
     * @return The total amount of ballots.
     */
	public int getTotalBallots() {
		return validBallots+blankBallots+invalidBallots;
	}
	
	/**
     * Gets the total amount of invalid ballots.
     * @return The total amount of invalid ballots.
     */
	public int getTotalInvalidBallots() {
		return invalidBallots;
	}
	
	/**
     * Gets the total amount of blank ballots.
     * @return The total amount of blank ballots.
     */
	public int getTotalBlankBallots() {
		return blankBallots;
	}
	
	/**
     * Gets the total amount of valid ballots.
     * @return The total amount of valid ballots.
     */
	public int getTotalValidBallots() {
		return validBallots;
		
	}
	
	/**
     * Gets the list of names for the eliminated candidates with the numbers of 1s they had,
     * in order of elimination.
     * @return The list of eliminated candidates.
     */
	public List<String> getEliminatedCandidates(){
		return eliminatedCandidates;
	}
	
	/**
     * Gets the list of candidates participating in the election.
     * @return The list of candidates.
     */
	public List<Candidate> getCandidates(){
		return candidates;
	}
	
	/**
     * Gets the list of all ballots cast in the election.
     * @return The list of ballots.
     */
	public List<Ballot> getBallots(){
		List<Ballot> ballots = new ArrayList<Ballot>();
		for (List<Ballot> ballotList: ballots2dArray) {
			for (Ballot ballot: ballotList) {
				ballots.add(ballot);			}
		}
		return ballots;
	}
	
	/**
     * Prints the distribution of ballots including total, blank, invalid, and valid ballots,
     * and each candidate's tally by rank.
     */
	public void printBallotDistribution() {
	 System.out.println("Total ballots:" + getTotalBallots());
	 System.out.println("Total blank ballots:" + getTotalBlankBallots());
	 System.out.println("Total invalid ballots:" + getTotalInvalidBallots());
	 System.out.println("Total valid ballots:" + getTotalValidBallots());
	 System.out.println(getEliminatedCandidates());
	 for(Candidate c: this.getCandidates()) {
	 System.out.print(c.getName().substring(0, c.getName().indexOf(" ")) + "\t");
		 for(Ballot b: this.getBallots()) {
		 int rank = b.getRankByCandidate(c.getId());
		 String tableline = "| " + ((rank != -1) ? rank: " ") + " ";
		 System.out.print(tableline);
	 }
	 System.out.println("|");
	 }
	}

}
