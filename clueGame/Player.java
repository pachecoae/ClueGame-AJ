package clueGame;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
	private String name;
	private List<Card> myCards;
	private String color;
	protected int col;
	protected int row;

	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		myCards = new ArrayList<>();
	}

	public void loadPlayers(String filename) throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		ArrayList<Player> playerList = new ArrayList<Player>();
		ComputerPlayer compPlayer;
		HumanPlayer humPlayer;

		String s;
		String[] arr;
		int linenum = 0;

		while ((reader.readLine()) != null) {
			s = reader.readLine();
			arr = s.split(",");
			// [0] = name, [1] = color, [2] = y-coord, [3] = x-coord
			int yCoord = Integer.parseInt(arr[2]);
			int xCoord = Integer.parseInt(arr[3]);

			if (linenum == 0) {
				// The first player in the file will be the human player.
				humPlayer = new HumanPlayer(arr[0], arr[1], yCoord, xCoord);
				playerList.add(humPlayer);
			} else {
				compPlayer = new ComputerPlayer(arr[0], arr[1], yCoord, xCoord);
				playerList.add(compPlayer);
			}
			linenum++;
		}
		reader.close();
	}

	public Card disproveSuggestion(String person, String room, String weapon) {
		// Shuffle the cards so that the returned card is random.
		Collections.shuffle(myCards);
		// Make sure that only one card is returned.
		for (Card c : myCards) {
			if (c.name == person) {
				return c;
			} else if (c.name == room) {
				return c;
			} else if (c.name == weapon) {
				return c;
			}
		}
		// If there are no matches, return null.
		return null;
	}

	public List<Card> getCards() {
		return myCards;
	}

	public void setCards(Collection<Card> cards) {
		myCards = new ArrayList<>(cards);
	}

	public String getName() {
		return name;
	}

	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined }
		}
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (col != other.col) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
