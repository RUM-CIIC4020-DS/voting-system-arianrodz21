package main;

import data_structures.ArrayList;
import interfaces.List;

public class Ballot {
	List<Candidate> Candidates;
	int ballotNumber;
	int validType = 0;
	List<List<Integer>> cRanksID = new ArrayList<List<Integer>>();
	List<Integer> ranks = new ArrayList<Integer>();
	List<Integer> ids = new ArrayList<Integer>();
	/* Creates a ballot based on the line it receives. The format for line is
	id#,candidate_name:candidate_rank . It also receives a List of all the candidates in the
	elections.*/
	public Ballot(String line, List<Candidate> candidates) {
		String[] temp = line.split(",");
		ballotNumber = Integer.valueOf(temp[0]);
		
		if(temp.length == 1) {
			validType = 1;
			
		}

		for(int i = 1; i < temp.length; i++) {
			String[] temp2 = temp[i].split(":");
			int id = Integer.valueOf(temp2[0]);
			int rank = Integer.valueOf(temp2[1]);
			
			List<Integer> ranksID = new ArrayList<Integer>();
			
			ranksID.add(id);
			ids.add(id);
			ranksID.add(rank);
			ranks.add(rank);
			cRanksID.add(ranksID);
			
			if(i != rank) {
				validType = 2;
			}
		
			for (int j = 0; j < ranks.size(); j++) {
		            for (int h = j + 1; h < ranks.size(); h++) {
		                if (ranks.get(j).equals(ranks.get(h))) {
		                    validType = 2;
		                }
		            }
		        }
			for (int j = 0; j < ids.size(); j++) {
	            for (int h = j + 1; h < ids.size(); h++) {
	                if (ids.get(j).equals(ids.get(h))) {
	                    validType = 2;
	                }
	            }
	        }
			
		        
		    }
			
		
		
		
	};
	//Checks if it has duplicates
	// Returns the ballot number
	public int getBallotNum() {
		return ballotNumber;
	};
	//Returns the rank for that candidate, if no rank is available return -1
	public int getRankByCandidate(int candidateID) {
		for(List<Integer> data : cRanksID) {
			if(data.get(0).equals(candidateID)) {
				return data.get(1);
			}
		}
		return -1;
		
		
	};
	//Returns the candidate with that rank, if no candidate is available return -1.
	public int getCandidateByRank(int rank){
		for(List<Integer> data : cRanksID) {
			if(data.get(1).equals(rank)) {
				return data.get(0);
			}
		}
		return -1;
		
	};
	// Eliminates the candidate with the given id
	public boolean eliminate(int candidateId) {
		for(List<Integer> data: cRanksID) {
			if(data.get(0).equals(candidateId)) {
				int byeCandidateRank = data.get(1);
				this.Candidates.get(1).setActive(false);
				cRanksID.remove(data);
				
				for(List<Integer> nData: cRanksID) {
					if(nData.get(1) > byeCandidateRank) {
						nData.set(1, nData.get(1) - 1);
					}
				}
				return true;
			}
			
		}
		return false;
		
	};
	/* Returns an integer that indicates if the ballot is: 0 – valid, 1 – blank or 2 -
	invalid */
	public int getBallotType() {
		return validType; 
	}; 
}
