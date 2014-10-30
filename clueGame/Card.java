package clueGame;

public class Card {
	public String name;
	public CardType type;

	public enum CardType {
		ROOM, WEAPON, PERSON;
	}

	public Card(String newName, CardType cardType) {
		name = newName;
		type = cardType;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((type == null) ? 0 : type.hashCode());
//		return result;
//	}

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
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

}
