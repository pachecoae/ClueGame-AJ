package clueTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	private static ClueGame game;
	private static Card profPlum;
	private static Card rope;
	private static Card hall;
	private static Player human;
	private static Player ai0;
	private static Player ai1;

	@BeforeClass
	public static void setUp() {
		profPlum = new Card("Professor Plum", CardType.PERSON);
		rope = new Card("Rope", CardType.WEAPON);
		hall = new Card("Hall", CardType.ROOM);

		human = new HumanPlayer("Professor Plum", "Purple", 0, 6);
		ai0 = new ComputerPlayer("Mrs. White", "White", 0, 11);
		ai1 = new ComputerPlayer("Mr. Green", "Green", 0, 16);
	}

	@Before
	public void setUpBefore() {
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt",
				"Cards.txt", "PlayerCards.txt");
		game.loadConfigFiles();
		game.getBoard().calcAdjacencies();
	}

	@Test
	public void testAccusation() {
		Solution solution = new Solution();
		solution.setPerson("Professor Plum");
		solution.setRoom("Mrs. White");
		solution.setWeapon("Mr. Green");
		// Assert.assertTrue();
		// Assert.assertFalse();
		// Assert.assertFalse();
		// Assert.assertFalse();
	}

}
