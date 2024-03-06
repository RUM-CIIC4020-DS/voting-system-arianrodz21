package main;

public class Election {
	/* Constructor that receives the name of the candidate and ballot files and applies
	the election logic. Note: The files should be found in the input folder. */
	public Election(String candidates_filename, String ballot_filename) {};
	// returns the name of the winner of the election
	public String getWinner() {};
	// returns the total amount of ballots submitted
	public int getTotalBallots() {};
	// returns the total amount of invalid ballots
	public int getTotalInvalidBallots() {};
	// returns the total amount of blank ballots
	public int getTotalBlankBallots() {};
	// returns the total amount of valid ballots
	public int getTotalValidBallots() {};
	/* List of names for the eliminated candidates with the numbers of 1s they had,
	must be in order of elimination. Format should be <candidate name>-<number of 1s
	when eliminated>*/
	public List<String> getEliminatedCandidates(){};

}
