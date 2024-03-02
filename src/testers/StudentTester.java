package testers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import data_structures.ArrayList;
import interfaces.List;
import main.Ballot;
import main.Candidate;
import main.Election;

public class StudentTester {
	

	@Nested
	@DisplayName("Student Tests")
	public class StudentTests {
		@Nested
		@DisplayName("Candidate Tests")
		public class CandidatesTests {
			@Test
			@DisplayName("Creating Candidate Part 1")
			public void testConstructor1() {
				Candidate candidate = new Candidate("12,Ron Stoppable");
	
				assertAll(
						() -> assertTrue(candidate.getId() == 12, "Didn't assign id properly."),
						() -> assertTrue(candidate.getName().equals("Ron Stoppable"),"Didn't assign correct name to candidate."),
						() -> assertTrue(candidate.isActive(), "Candidate should be active by default")
						);
				
	
			}
			@Test
			@DisplayName("Creating Candidate Part 2")
			public void testConstructor2() {
				Candidate candidate = new Candidate("158,Kim Possible");
	
				assertAll(
						() -> assertTrue(candidate.getId() == 158, "Didn't assign id properly."),
						() -> assertTrue(candidate.getName().equals("Kim Possible"),"Didn't assign correct name to candidate."),
						() -> assertTrue(candidate.isActive(), "Candidate should be active by default")
						);
	
			}
			
		}
		@Nested
		@DisplayName("Ballot Tests")
		public class BallotTests{
			@Test
			@DisplayName("Creating Ballot Part 1")
			public void testConstructor1() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("2,7:1,5:2,1:3,9:4,4:5,2:6,8:7,6:8,10:9,3:10", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 2, "Didn't assign id properly."),
						() -> assertTrue(ballot.getCandidateByRank(2) == 5,"Didn't assign correct candidate to rank."),
						() -> assertTrue(ballot.getCandidateByRank(9) == 10,"Didn't assign correct candidate to rank."),
						() -> assertTrue(ballot.getRankByCandidate(7) == 1, "Didn't assign corret rank to candidate"),
						() -> assertTrue(ballot.getRankByCandidate(2) == 6, "Didn't assign corret rank to candidate")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 2")
			public void testConstructor2() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("2158,7:1,5:2,1:3,9:4,4:5,2:6,8:7,6:8,10:9,3:10", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 2158, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 0,"Didn't assign correct ballot type to a valid ballot."),
						() -> assertTrue(ballot.getCandidateByRank(10) == 3,"Didn't assign correct candidate to rank."),
						() -> assertTrue(ballot.getCandidateByRank(6) == 2,"Didn't assign correct candidate to rank."),
						() -> assertTrue(ballot.getRankByCandidate(10) == 9, "Didn't assign corret rank to candidate"),
						() -> assertTrue(ballot.getRankByCandidate(1) == 3, "Didn't assign corret rank to candidate")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 3: Not n ballots")
			public void testConstructor3() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("489,9:1,7:2,10:3,5:4", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 489, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 0,"Didn't assign correct ballot type to a valid ballot."),
						() -> assertTrue(ballot.getCandidateByRank(3) == 10,"Didn't assign correct candidate to rank."),
						() -> assertTrue(ballot.getCandidateByRank(6) == -1,"Didn't return -1 for rank that's not in the ballot."),
						() -> assertTrue(ballot.getRankByCandidate(7) == 2, "Didn't assign corret rank to candidate"),
						() -> assertTrue(ballot.getRankByCandidate(3) == -1, "Didn't return -1 for candidate that's not in the ballot.")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 4: Invalid ballots 1")
			public void testConstructor4() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("582,7:1,5:2,1:3,9:4,4:5,2:9", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 582, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 2,"Didn't assign correct ballot type to an invalid ballot (values skipped in rank).")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 5: Invalid ballots 2")
			public void testConstructor5() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("1256,7:1,5:2,1:3,9:4,4:5,7:6", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 1256, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 2,"Didn't assign correct ballot type to an invalid ballot (dublicate candidate).")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 5: Invalid ballots 3")
			public void testConstructor6() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("1256,7:1,5:2,1:3,9:1,4:5,2:6", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 1256, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 2,"Didn't assign correct ballot type to an invalid ballot (duplicate rank).")
						);
	
			}
			@Test
			@DisplayName("Creating Ballot Part 6: Blank ballot")
			public void testConstructor7() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("1256", candidates);
	
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 1256, "Didn't assign id properly."),
						() -> assertTrue(ballot.getBallotType() == 1,"Didn't assign correct ballot type to a blank ballot.")
						);
	
			}
			@Test
			@DisplayName("Testing eliminate")
			public void testEliminate() {
				List<Candidate> candidates = new ArrayList<Candidate>(10);
				candidates.add(new Candidate("1,Ron Stoppable"));
				candidates.add(new Candidate("2,Kim Possible"));
				candidates.add(new Candidate("3,Rufus Stoppable"));
				candidates.add(new Candidate("4,Ben Tenison"));
				candidates.add(new Candidate("5,Timmy Turner"));
				candidates.add(new Candidate("6,Danny Fenton"));
				candidates.add(new Candidate("7,Tommy Pickles"));
				candidates.add(new Candidate("8,P Lankton"));
				candidates.add(new Candidate("9,Jimmy Neutron"));
				candidates.add(new Candidate("10,Luz Noceda"));
				Ballot ballot = new Ballot("126,7:1,5:2,1:3,9:4,6:5,2:6", candidates);
	
				ballot.eliminate(6);
				assertAll(
						() -> assertTrue(ballot.getBallotNum() == 126, "Didn't assign id properly."),
						() -> assertTrue(ballot.getRankByCandidate(6) == -1,"Didn't remove candidate from ballot."),
						() -> assertTrue(ballot.getCandidateByRank(5) == 2,"Didn't update ranks from candidates after the removed candidate.")
						);
	
			}
			
		}
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
			public void testConstructor2() {
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
	}

}
