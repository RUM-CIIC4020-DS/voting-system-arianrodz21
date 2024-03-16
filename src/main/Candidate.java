//package main;
//
//public class Candidate {
//	int id;
//	Boolean active = true;
//	String name;
//	
//	
//	
//	public Candidate(String line) {
//		String[] temp = line.split(",");
//		id = Integer.valueOf(temp[0].trim());
//		name = temp[1].trim();
//		
//	};
//	// returns the candidate’s id
//	public int getId() {
//		return this.id;
//	};
//	// Whether the candidate is still active in the election
//	public boolean isActive() {
//		return this.active; //to be fixed
//	};
//	// return the candidates name
//	public String getName() {
//		return name;
//	}; 
//	public void setActive(boolean itActive){
//		this.active = itActive;
//	}
//	
//
//}


package main;

/**
 * Represents a candidate participating in an election.
 */
public class Candidate {
    /**
     * The unique identifier of the candidate.
     */
    private int id;

    /**
     * Indicates whether the candidate is currently active.
     */
    private boolean active = true;

    /**
     * The name of the candidate.
     */
    private String name;

    /**
     * Constructs a new Candidate object using the provided input line.
     * @param line A string containing candidate information in the format "id, name".
     */
    public Candidate(String line) {
        String[] temp = line.split(",");
        id = Integer.valueOf(temp[0].trim());
        name = temp[1].trim();
    }

    /**
     * Returns the identifier of the candidate.
     * @return The candidate's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Checks whether the candidate is still active in the election.
     * @return True if the candidate is active, false otherwise.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Returns the name of the candidate.
     * @return The candidate's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the active status of the candidate.
     * @param isActive The new active status for the candidate.
     */
    public void setActive(boolean isActive) {
        this.active = isActive;
    }
}
