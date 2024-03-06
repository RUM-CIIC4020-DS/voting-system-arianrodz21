package main;

public class Candidate {
	int id;
	Boolean active = true;
	String name;
	
	
	
	public Candidate(String line) {
		String[] temp = line.split(",");
		id = Integer.valueOf(temp[0].trim());
		name = temp[1].trim();
		
	};
	// returns the candidate’s id
	public int getId() {
		return this.id;
	};
	// Whether the candidate is still active in the election
	public boolean isActive() {
		return this.active; //to be fixed
	};
	// return the candidates name
	public String getName() {
		return name;
	}; 
	public void setActive(boolean itActive){
		this.active = itActive;
	}
	

}
