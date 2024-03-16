//package main;
//
//import data_structures.ArrayList;
//import interfaces.List;
//
//public class Ballot {
//	List<Candidate>  candidates = new ArrayList<Candidate>();
//	int ballotNumber;
//	int validType = 0;
//	List<Integer> ranks = new ArrayList<Integer>();
//	List<Integer> ids = new ArrayList<Integer>();
//	
//	/* Creates a ballot based on the line it receives. The format for line is
//	id#,candidate_name:candidate_rank . It also receives a List of all the candidates in the
//	elections.*/
//	public Ballot(String line, List<Candidate> candidates) {
//		for(int i = 0; i < candidates.size(); i++) {
//			this.candidates.add(candidates.get(i));
//			ranks.add(0);
//			ids.add(candidates.get(i).getId());
//		}
//		
//		//split the line on (",") and make the value an integer
//		String[] temp = line.split(",");
//		ballotNumber = Integer.valueOf(temp[0]);
//		
//		// checks if the ballot is blank
//		if(temp.length == 1) {
//			validType = 1;
//			
//		}
//
//		//adds ranks of each candidate to the ranks list and checks for duplicate candidates
//		if(validType == 0) {
//			for(int i = 1; i < temp.length; i++) {
//				String[] temp2 = temp[i].split(":");
//				for(int j = 0; j < candidates.size(); j++) {
//					if(candidates.get(j).getId() == Integer.valueOf(temp2[0])) {
//						if(ranks.get(j) != 0) {
//							validType = 2; 
//							break;
//						}
//						ranks.set(j, Integer.valueOf(temp2[1]));
//						break;
//					}
//				}
//			}
//				
//			
//			// checks if the ballot is invalid, by checking if there are duplicate ranks
//			for(int i = 0; i < ranks.size()-1; i++) {
//				for(int j = i+1; j < ranks.size(); j++) {
//	                if(ranks.get(i) != 0 && ranks.get(i).equals(ranks.get(j))) {
//	                    validType = 2;
//	                    break;
//	                }	        
//			    }
//			}
//			
//			//checks that no ranks are skipped
//			for(int i = 0; i < ranks.size();i++) {
//				if(ranks.get(i) > 1 && !ranks.contains(ranks.get(i)-1)) validType = 2;	
//			}
//		}
//		
//	};
//	//Checks if it has duplicates
//	// Returns the ballot number
//	public int getBallotNum() {
//		return ballotNumber;
//	};
//	//Returns the rank for that candidate, if no rank is available return -1
//	public int getRankByCandidate(int candidateID) {
//		for(int i = 0; i < ids.size(); i++) {
//			if(ids.get(i) == candidateID) {
//				if(ranks.get(i) == 0) break;
//				return ranks.get(i);
//			}
//		}
//		return -1;
//		
//		
//	};
//	//Returns the candidate with that rank, if no candidate is available return -1.
//	public int getCandidateByRank(int rank){
//		for(int i = 0; i < ranks.size(); i++) {
//			if(ranks.get(i) == rank) {
//				return ids.get(i);
//			}
//		}
//		return -1;
//		
//	};
//	// Eliminates the candidate with the given id
//	public boolean eliminate(int candidateId) {
//		for(int i = 0; i < ids.size(); i++) {
//			if(ids.get(i) == candidateId) {
//				candidates.get(i).setActive(false);
//				ids.remove(i);
//				for(int j = 0; j < ranks.size(); j++){
//					if(ranks.get(j) > ranks.get(i)){
//						ranks.set(j, ranks.get(j) - 1);
//					}
//				}
//				ranks.remove(i);
//				return true;
//			}
//		}
//		return false;
//		
//	};
//	/* Returns an integer that indicates if the ballot is: 0 – valid, 1 – blank or 2 -
//	invalid */
//	public int getBallotType() {
//		return validType; 
//	}; 
//}


package main;

import data_structures.ArrayList;
import interfaces.List;

/**
 * Represents a ballot used in an election, containing candidate rankings.
 */
public class Ballot {
    /**
     * The list of candidates on the ballot.
     */
    private List<Candidate> candidates = new ArrayList<Candidate>();

    /**
     * The number associated with this ballot.
     */
    private int ballotNumber;

    /**
     * An integer indicating the validity of the ballot:
     * 0 - valid
     * 1 - blank
     * 2 - invalid
     */
    private int validType = 0;

    /**
     * The list of ranks assigned to each candidate on the ballot.
     */
    private List<Integer> ranks = new ArrayList<Integer>();

    /**
     * The list of candidate IDs present on the ballot.
     */
    private List<Integer> ids = new ArrayList<Integer>();

    
    /**
     * Constructs a ballot object based on the provided input line and list of candidates.
     * @param line A string containing ballot information in the format "ballotNumber,id1:rank1,id2:rank2,...".
     * @param candidates A list of all candidates in the election.
     */
    public Ballot(String line, List<Candidate> candidates) {
        // Initialize the candidates list, ranks list, and ids list
        for(int i = 0; i < candidates.size(); i++) {
            this.candidates.add(candidates.get(i));
            ranks.add(0);
            ids.add(candidates.get(i).getId());
        }
        
        // Parse the input line
        String[] temp = line.split(",");
        ballotNumber = Integer.valueOf(temp[0]);
        
        // Check if the ballot is blank
        if(temp.length == 1) {
            validType = 1;
        }

        // Process each candidate rank
        if(validType == 0) {
            for(int i = 1; i < temp.length; i++) {
                String[] temp2 = temp[i].split(":");
                for(int j = 0; j < candidates.size(); j++) {
                    if(candidates.get(j).getId() == Integer.valueOf(temp2[0])) {
                        if(ranks.get(j) != 0) {
                            validType = 2; 
                            break;
                        }
                        ranks.set(j, Integer.valueOf(temp2[1]));
                        break;
                    }
                }
            }
                
            // Check for duplicate ranks
            for(int i = 0; i < ranks.size()-1; i++) {
                for(int j = i+1; j < ranks.size(); j++) {
                    if(ranks.get(i) != 0 && ranks.get(i).equals(ranks.get(j))) {
                        validType = 2;
                        break;
                    }	        
                }
            }
            
            // Check for skipped ranks
            for(int i = 0; i < ranks.size(); i++) {
                if(ranks.get(i) > 1 && !ranks.contains(ranks.get(i)-1)) validType = 2;	
            }
        }
    }

    /**
     * Retrieves the ballot number associated with this ballot.
     * @return The ballot number.
     */
    public int getBallotNum() {
        return ballotNumber;
    }

    /**
     * Retrieves the rank assigned to a candidate with the given ID on this ballot.
     * @param candidateID The ID of the candidate.
     * @return The rank assigned to the candidate, or -1 if no rank is available.
     */
    public int getRankByCandidate(int candidateID) {
        for(int i = 0; i < ids.size(); i++) {
            if(ids.get(i) == candidateID) {
                if(ranks.get(i) == 0) break;
                return ranks.get(i);
            }
        }
        return -1;
    }

    /**
     * Retrieves the ID of the candidate with the given rank on this ballot.
     * @param rank The rank of the candidate.
     * @return The ID of the candidate with the specified rank, or -1 if no candidate is found.
     */
    public int getCandidateByRank(int rank) {
        for(int i = 0; i < ranks.size(); i++) {
            if(ranks.get(i) == rank) {
                return ids.get(i);
            }
        }
        return -1;
    }

    /**
     * Eliminates the candidate with the given ID from the ballot.
     * @param candidateId The ID of the candidate to eliminate.
     * @return True if the candidate was successfully eliminated, false otherwise.
     */
    public boolean eliminate(int candidateId) {
        for(int i = 0; i < ids.size(); i++) {
            if(ids.get(i) == candidateId) {
                candidates.get(i).setActive(false);
                ids.remove(i);
                for(int j = 0; j < ranks.size(); j++){
                    if(ranks.get(j) > ranks.get(i)){
                        ranks.set(j, ranks.get(j) - 1);
                    }
                }
                ranks.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the type of the ballot.
     * @return An integer indicating the validity of the ballot: 0 for valid, 1 for blank, 2 for invalid.
     */
    public int getBallotType() {
        return validType; 
    }
}
