package clueGame;

import java.awt.Checkbox;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class roomBox extends JPanel {
	private Checkbox box1, box2, box3, box4, box5, box6, box7, box8, box9;

	public roomBox() {
		setLayout(new GridLayout(5, 2));
		box1 = new Checkbox("Study", false);
		box2 = new Checkbox("Kitchen", false);
		box3 = new Checkbox("Hall", false);
		box4 = new Checkbox("Conservatory", false);
		box5 = new Checkbox("Lounge", false);
		box6 = new Checkbox("Ballroom", false);
		box7 = new Checkbox("Dining Room", false);
		box8 = new Checkbox("Library", false);
		box9 = new Checkbox("Billiard Room", false);
		add(box1);
		add(box2);
		add(box3);
		add(box4);
		add(box5);
		add(box6);
		add(box7);
		add(box8);
		add(box9);
	}
}
