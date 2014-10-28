package clueGame;

import java.awt.Checkbox;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class peopleBox extends JPanel {
	private Checkbox box1, box2, box3, box4, box5, box6;

	public peopleBox() {
		setLayout(new GridLayout(3, 2));
		box1 = new Checkbox("Professor Plum", false);
		box2 = new Checkbox("Mrs. White", false);
		box3 = new Checkbox("Mr. Green", false);
		box4 = new Checkbox("Mrs. Peacock", false);
		box5 = new Checkbox("Miss Scarlett", false);
		box6 = new Checkbox("Colonel Mustard", false);
		add(box1);
		add(box2);
		add(box3);
		add(box4);
		add(box5);
		add(box6);
	}
}