package clueTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {
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
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt",
				"PlayerCards.txt");
		game.loadConfigFiles();
		game.getBoard().calcAdjacencies();
	}

	/*
	 * How can we test loading the people from file?
	 * 
	 * I check the human player and 2 of the computer players (the first and last in my file) to
	 * ensure that they have the correct:
	 * 
	 * Name, Color, Starting location
	 * 
	 * NOTE: You will determine the format for the file(s) you load during this lab. This is
	 * discussed more in Part II.
	 */

	@Test
	public void testLoadingPeopleFromFile() {
		Assert.assertEquals(6, game.players.size());

		Assert.assertEquals(human, game.players.get(0));
		Assert.assertEquals(ai0, game.players.get(1));
		Assert.assertEquals(ai1, game.players.get(2));
	}

	@Test
	public void testLoadingCardsFromFile() {

		// We check the deck size and make sure the correct number of weapons,
		// people, and room cards are contained within the deck. We then check
		// one weapon, room, and person to make sure they are contained within
		// the deck.

		List<Card> deck = game.getDeck();
		Assert.assertEquals(23, deck.size());

		int numWeps = 0;
		int numRooms = 0;
		int numChars = 0;
		for (Card c : deck) {
			switch (c.type) {
			case WEAPON:
				numWeps++;
				break;
			case PERSON:
				numChars++;
				break;
			case ROOM:
				numRooms++;
				break;
			}
		}

		Assert.assertEquals(6, numWeps);
		Assert.assertEquals(6, numChars);
		Assert.assertEquals(11, numRooms);
		Assert.assertEquals(true, deck.contains(profPlum));
		Assert.assertEquals(true, deck.contains(rope));
		Assert.assertEquals(true, deck.contains(hall));
	}

	/*
	 * How can we test the deal?
	 * 
	 * To be a valid deal, we need to ensure that:
	 * 
	 * all cards are dealt. all players have roughly the same number of cards. one card is not given
	 * to two different players.
	 */

	@Test
	public void testDeal() {
		game.deal();

		Player playerA = game.players.get(0);
		Assert.assertEquals(5, playerA.getCards().size());
		int max = playerA.getCards().size();

		for (Player p : game.players) {
			if (p.getCards().size() > max && p.getCards().size() < max - 1) {
				Assert.fail("");
			}
		}

		for (int i = 0; i < game.players.size(); i++) {
			List<Card> cards = game.players.get(i).getCards();
			for (int j = 1; j < game.players.size(); j++) {
				if (i == j) {
					continue;
				}

				cards.retainAll(game.players.get(j).getCards());
				if (cards.size() != 0) {
					Assert.fail("Player " + i + " and player " + j + " had the same card");
				}
			}
		}

	}
}
