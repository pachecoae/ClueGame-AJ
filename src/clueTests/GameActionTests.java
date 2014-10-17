package clueTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.ClueGame;
import clueGame.Solution;

public class GameActionTests {
	private static ClueGame game;

	// private static Card profPlum;
	// private static Card rope;
	// private static Card hall;
	// private static Player human;
	// private static Player ai0;
	// private static Player ai1;

	// private static Solution gameSolution;

	// @BeforeClass
	// public static void setUp() {
	// profPlum = new Card("Professor Plum", CardType.PERSON);
	// rope = new Card("Rope", CardType.WEAPON);
	// hall = new Card("Hall", CardType.ROOM);
	//
	// human = new HumanPlayer("Professor Plum", "Purple", 0, 6);
	// ai0 = new ComputerPlayer("Mrs. White", "White", 0, 11);
	// ai1 = new ComputerPlayer("Mr. Green", "Green", 0, 16);
	// }

	@Before
	public void setUpBefore() {
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt",
				"PlayerCards.txt");
		game.loadConfigFiles();
		game.getBoard().calcAdjacencies();
	}

	@Test
	public void testAccusation() {
		// Setting up the game's solution
		game.solution = new Solution("Professor Plum", "Rope", "Hall");

		// Set up the asserted correct solutions and then check to see if the accusation is correct
		Solution correctSolution = new Solution("Professor Plum", "Rope", "Hall");
		Assert.assertEquals(true, game.checkAccusation(correctSolution));

		Solution wrongPerson = new Solution("Not the Professor Plum", "Rope", "Hall");
		Assert.assertEquals(false, game.checkAccusation(wrongPerson));

		Solution wrongWeapon = new Solution("Professor Plum", "Not the Rope", "Hall");
		Assert.assertEquals(false, game.checkAccusation(wrongWeapon));

		Solution wrongRoom = new Solution("Professor Plum", "Rope", "Not the Hall");
		Assert.assertEquals(false, game.checkAccusation(wrongRoom));
	}
}
