package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.ArrayList;
import interfaces.List;

import main.Ballot;
import main.Candidate;
import main.Election;


public class Election {
	int invalidBallots;
	int blankBallots;
	int validBallots;
	List<Candidate> candidates = new ArrayList<Candidate>();
	List<Ballot> ballot = new ArrayList<Ballot>();
	
	
	public Election() {
		
	}
	/* Constructor that receives the name of the candidate and ballot files and applies
	the election logic. Note: The files should be found in the input folder. */
	public Election(String candidates_filename, String ballot_filename) throws IOException {
		BufferedReader brCF = new BufferedReader(new FileReader("inputFiles/" + candidates_filename));
		String dataCF;
		while((dataCF = brCF.readLine()) != null) {
			candidates.add(new Candidate(dataCF));
		}
		BufferedReader brBF = new BufferedReader(new FileReader("inputFiles/" + ballot_filename));
		String dataBF;
		while((dataBF = brBF.readLine()) != null) {
			ballot.add(new Ballot(dataBF,candidates));
		}
	};
	// returns the name of the winner of the election
	public String getWinner() {
		
	};
	// returns the total amount of ballots submitted
	public int getTotalBallots() {
		return ballot.size();
		
	};
	// returns the total amount of invalid ballots
	public int getTotalInvalidBallots() {
		for(int i = 0; i < ballot.size(); i++) {
			if(ballot.get(i).getBallotType() == 2) {
				invalidBallots += 1;
			}
			
		}
		return invalidBallots;
	};
	// returns the total amount of blank ballots
	public int getTotalBlankBallots() {
		for(int i = 0; i < ballot.size(); i++) {
			if(ballot.get(i).getBallotType() == 1) {
				blankBallots += 1;
			}
			
		}
		return blankBallots;
	};
	
	
	// returns the total amount of valid ballots
	public int getTotalValidBallots() {
		for(int i = 0; i < ballot.size(); i++) {
			if(ballot.get(i).getBallotType() == 0) {
				validBallots += 1;
			}
			
		}
		return validBallots;
	};
	/* List of names for the eliminated candidates with the numbers of 1s they had,
	must be in order of elimination. Format should be <candidate name>-<number of 1s
	when eliminated>*/
	public List<String> getEliminatedCandidates(){
		
	};

}
