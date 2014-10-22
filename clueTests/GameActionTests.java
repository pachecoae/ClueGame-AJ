package clueTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
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
	private static Card dungeon;
	private static Card missScarlet;

	private static HumanPlayer human;
	private static ComputerPlayer ai0;
	private static ComputerPlayer ai1;

	// private static Solution gameSolution;

	@BeforeClass
	public static void setUp() {
		profPlum = new Card("Professor Plum", CardType.PERSON);
		missScarlet = new Card("Miss Scarlet", CardType.PERSON);
		rope = new Card("Rope", CardType.WEAPON);
		dungeon = new Card("Dungeon", CardType.ROOM);

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

	@Test
	public void testAccusation() {
		// Setting up the game's solution
		game.solution = new Solution("Professor Plum", "Rope", "Hall");

		// Set up the asserted correct solutions and then check to see if the accusation is correct
		Solution correctSolution = new Solution("Professor Plum", "Rope", "Hall");
		Assert.assertTrue(game.checkAccusation(correctSolution));

		Solution wrongPerson = new Solution("Not the Professor Plum", "Rope", "Hall");
		Assert.assertFalse(game.checkAccusation(wrongPerson));

		Solution wrongWeapon = new Solution("Professor Plum", "Not the Rope", "Hall");
		Assert.assertFalse(game.checkAccusation(wrongWeapon));

		Solution wrongRoom = new Solution("Professor Plum", "Rope", "Not the Hall");
		Assert.assertFalse(game.checkAccusation(wrongRoom));
	}

	// - Disprove a suggestion. Tests include:

	// - Tests that one player returns the only possible card (one person, one room, one weapon).

	// - A test that one player randomly chooses between two possible cards.

	// - A test that players are queried in order.

	// - Tests involving the human player.

	// - A test that the player whose turn it is does not return a card.

	@Test
	public void testDisproveSuggestion() {
		// Setting up the game's solution
		game.solution = new Solution("Mr. Green", "Rope", "Hall");

		ComputerPlayer Frank = new ComputerPlayer("Professor Plum", "Purple", 0, 0);

		Card knife = new Card("Knife", CardType.WEAPON);
		Card wrench = new Card("Wrench", CardType.WEAPON);
		Card profPlum = new Card("Professor Plum", CardType.PERSON);
		Card mrsWhite = new Card("mrs. White", CardType.PERSON);
		Card conservatory = new Card("Conservatory", CardType.ROOM);
		Card kitchen = new Card("Kitchen", CardType.ROOM);

		List<Card> Frankscards;

		Frankscards = Frank.getCards();
		Frankscards.add(knife);
		Frankscards.add(wrench);
		Frankscards.add(profPlum);
		Frankscards.add(mrsWhite);
		Frankscards.add(conservatory);
		Frankscards.add(kitchen);

		Frank.setCards(Frankscards);

		// Test for when 1 card in "Frank's" hand disproves the suggestion
		Assert.assertEquals(Frank.disproveSuggestion("Professor Plum", "Billiard Room", "Rope"),
				profPlum);

		// Test for when 2 cards in "Frank's" hand disproves the suggestion

		boolean seenPlum = false;
		boolean seenConservatory = false;
		for (int i = 0; i < 1000; i++) {
			Card disprove = Frank.disproveSuggestion("Professor Plum", "Conservatory", "Rope");
			Assert.assertNotNull(disprove);

			if (disprove.equals(profPlum)) {
				seenPlum = true;
			} else if (disprove.equals(conservatory)) {
				seenConservatory = true;
			} else {
				Assert.fail("Expected to see either Professor Plum or the Conservatory, not neither.");
			}
			if (seenPlum == true && seenConservatory == true) {
				break;
			}
		}
		Assert.assertTrue(seenPlum);
		Assert.assertTrue(seenConservatory);

	}

	// @Test
	// public void disproveSuggestion() {
	// // Solution solution = new Solution();
	// // solution.setPerson(profPlum.name);
	// // solution.setRoom(dungeon.name);
	// // solution.setWeapon(rope.name);
	//
	// // Tests that one player returns the only possible card (one person, one room, one weapon).
	//
	// // ComputerPlayer.unseen.add(profPlum);
	// // Assert.assertTrue(ai0.createSuggestion().contains(profPlum));
	// // Assert.assertTrue(ai0.createSuggestion().contains(rope));
	// // Assert.assertTrue(ai0.createSuggestion().contains(dungeon));
	//
	// // A test that one player randomly chooses between two possible cards.
	//
	// // ComputerPlayer.unseen.clear();
	// // ComputerPlayer.unseen.add(profPlum);
	// // ComputerPlayer.unseen.add(missScarlet);
	//
	// // Assert.assertTrue(ai0.createSuggestion().contains(profPlum) ||
	// // ai0.createSuggestion().contains(missScarlet);
	// // Assert.assertTrue(ai0.createSuggestion().contains(rope));
	// // Assert.assertTrue(ai0.createSuggestion().contains(dungeon));
	//
	// // A test that players are queried in order.
	//
	// // Tests involving the human player.
	//
	// // A test that the player whose turn it is does not return a card.
	// }

	// - Select a target. Tests include:

	// - A set of targets that include a room.

	// - A random selection from a set of targets that don't include a room.

	// - A test that considers the last visited room.

	@Test
	public void selectTarget() {
		Set<BoardCell> targetList;

		// Create a list of targets that include a room.
		game.getBoard().calcTargets(21, 5, 1);
		targetList = game.getBoard().getTargets();
		ai0.pickLocation(targetList);
		Assert.assertEquals('L', ai0.currentRoom);

		// Create a list of targets that do not include a room.
		game.getBoard().calcTargets(6, 5, 1);
		targetList = game.getBoard().getTargets();
		ai0.pickLocation(targetList);
		Assert.assertEquals('W', ai0.currentRoom);

		// Set the last visited room to 'L'.
		ai0.lastRoomVisited = 'L';

		// Create a list of targets that include the last visited room.
		game.getBoard().calcTargets(21, 5, 1);
		targetList = game.getBoard().getTargets();
		ai0.pickLocation(targetList);
		Assert.assertEquals('W', ai0.currentRoom);
	}

	@Test
	public void makeSuggestion() {
		List<Card> unseenCards = new ArrayList<Card>();
		unseenCards.add(profPlum);
		unseenCards.add(rope);
		ComputerPlayer.unseen = unseenCards;
		ai0.currentRoom = 'D';
		List<Card> cardList = ai0.createSuggestion();

		Assert.assertEquals(3, cardList.size());
		Assert.assertTrue(cardList.contains(profPlum));
		Assert.assertTrue(cardList.contains(rope));
		Assert.assertTrue(cardList.contains(dungeon));

		unseenCards.clear();
		unseenCards.add(missScarlet);
		unseenCards.add(profPlum);
		unseenCards.add(rope);

		boolean seenPlum = false;
		boolean seenScarlet = false;
		for (int i = 0; i < 1000; i++) {
			cardList = ai0.createSuggestion();

			Assert.assertEquals(3, cardList.size());
			Assert.assertTrue(cardList.contains(rope));
			Assert.assertTrue(cardList.contains(dungeon));

			if (cardList.contains(profPlum)) {
				seenPlum = true;
			} else if (cardList.contains(missScarlet)) {
				seenScarlet = true;
			} else {
				Assert.fail("Expected to see either Professor Plum or Miss Scarlet, not neither.");
			}
			if (seenPlum == true && seenScarlet == true) {
				break;
			}
		}
		Assert.assertTrue(seenPlum);
		Assert.assertTrue(seenScarlet);

	}
}
