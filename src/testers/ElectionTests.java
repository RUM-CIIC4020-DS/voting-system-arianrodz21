package testers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.Election;

@Nested
@DisplayName("Election Tests")
public class ElectionTests {
	@Test
	@DisplayName("Creating Election")
	public void testConstructor1() {
		Election election = new Election();
		
		assertAll(
				() -> assertTrue(election.getWinner().equals("Pepe Perez"), "Didn't get Correct winner."),
				() -> assertTrue(election.getTotalBallots() == 15,"Didn't count correct amount of ballots"),
				() -> assertTrue(election.getTotalBlankBallots() == 3, "Didn't count correct amount of blank ballots"),
				() -> assertTrue(election.getTotalInvalidBallots() == 2, "Didn't count correct amount of invalid ballots"),
				() -> assertTrue(election.getTotalBlankBallots() == 3, "Didn't count correct amount of blank ballots"),
				() -> assertTrue(election.getTotalValidBallots() == 10, "Didn't count correct amount of valid ballots"),
				() -> assertTrue(election.getEliminatedCandidates().get(0).equals("Lola Mento-1"), "Didn't return correct eliminated candidate and/or count for this position"),
				() -> assertTrue(election.getEliminatedCandidates().get(1).equals("Juan Lopez-1"), "Didn't return correct eliminated candidate and/or count for this position"),
				() -> assertTrue(election.getEliminatedCandidates().get(2).equals("Pucho Avellanet-3"), "Didn't return correct eliminated candidate and/or count for this position")
				);
		

	}
	@Test
	@DisplayName("Creating Election 2")
	public void testConstructor2() throws IOException {
		Election election = new Election("candidates.csv", "ballots.csv");
		
		assertAll(
				() -> assertTrue(election.getWinner().equals("Pepe Perez"), "Didn't get Correct winner."),
				() -> assertTrue(election.getTotalBallots() == 15,"Didn't count correct amount of ballots"),
				() -> assertTrue(election.getTotalBlankBallots() == 3, "Didn't count correct amount of blank ballots"),
				() -> assertTrue(election.getTotalInvalidBallots() == 2, "Didn't count correct amount of invalid ballots"),
				() -> assertTrue(election.getTotalBlankBallots() == 3, "Didn't count correct amount of blank ballots"),
				() -> assertTrue(election.getTotalValidBallots() == 10, "Didn't count correct amount of valid ballots"),
				() -> assertTrue(election.getEliminatedCandidates().get(0).equals("Lola Mento-1"), "Didn't return correct eliminated candidate and/or count for this position"),
				() -> assertTrue(election.getEliminatedCandidates().get(1).equals("Juan Lopez-1"), "Didn't return correct eliminated candidate and/or count for this position"),
				() -> assertTrue(election.getEliminatedCandidates().get(2).equals("Pucho Avellanet-3"), "Didn't return correct eliminated candidate and/or count for this position")
				);
		

	}
	
}
