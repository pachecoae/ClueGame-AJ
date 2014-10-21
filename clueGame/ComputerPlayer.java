package clueGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ComputerPlayer extends Player {
	public char lastRoomVisited;
	public static List<Card> unseen = new ArrayList<>();
	public char currentRoom;

	public ComputerPlayer(String name, String color, int yCoord, int xCoord) {
		super(name, color, yCoord, xCoord);
	}

	public void pickLocation(Set<BoardCell> targets) {

	}

	public List<Card> createSuggestion() {
		return new ArrayList<Card>();
	}

	public void updateSeen(Card seen) {

	}

}
