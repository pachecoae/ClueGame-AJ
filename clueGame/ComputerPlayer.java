package clueGame;

import java.util.ArrayList;
import java.util.Collections;
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
		for (BoardCell b : targets) {
			if (b.isDoorway()) {
				if (b instanceof RoomCell) {
					if (((RoomCell) b).getInitial() == lastRoomVisited) {
						continue;
					}
					row = b.getRow();
					col = b.getCol();
					currentRoom = ((RoomCell) b).getInitial();
					lastRoomVisited = currentRoom;
					// TODO createSuggestion()?
					return;
				}
			}
		}
		List<BoardCell> targetList = new ArrayList<>(targets);
		Collections.shuffle(targetList);
		BoardCell b = targetList.get(0);
		row = b.getRow();
		col = b.getCol();
		currentRoom = 'W';
	}

	public List<Card> createSuggestion() {
		List<Card> suggestion = new ArrayList<>();

		// Add current room to the suggestion.
		String roomName = Board.getRooms().get(currentRoom);
		suggestion.add(new Card(roomName, Card.CardType.ROOM));
		// Pick person and weapon.
		Collections.shuffle(unseen);
		for (Card c : unseen) {
			if (c.type.equals(Card.CardType.PERSON)) {
				suggestion.add(c);
				break;
			}
		}
		for (Card c : unseen) {
			if (c.type.equals(Card.CardType.WEAPON)) {
				suggestion.add(c);
				break;
			}
		}
		return suggestion;
	}

	public void updateSeen(Card seen) {

	}

}
