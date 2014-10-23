package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;

	public ComputerPlayer(String name, String color, int yCoord, int xCoord) {
		super(name, color, yCoord, xCoord);
	}

	public void pickLocation(Set<BoardCell> targets) {

	}

	public void createSuggestion() {

	}

	public void updateSeen(Card seen) {

	}

}
