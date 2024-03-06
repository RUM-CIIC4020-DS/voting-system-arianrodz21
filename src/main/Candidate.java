package main;

public class Candidate {
	int id;
	Boolean active = true;
	String name;
	
	
	public Candidate(String line) {
		String[] temp = line.split(",");
		id = Integer.valueOf(temp[0]);
		name = temp[1];
		
	};
	// returns the candidate’s id
	public int getId() {
		return id;
	};
	// Whether the candidate is still active in the election
	public boolean isActive() {
		return active; //to be fixed
	};
	// return the candidates name
	public String getName() {
		return name;
	}; 

}
