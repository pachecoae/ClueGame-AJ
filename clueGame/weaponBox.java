package clueGame;

import java.awt.Checkbox;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class weaponBox extends JPanel {
	private Checkbox box1, box2, box3, box4, box5, box6;

	public weaponBox() {
		setLayout(new GridLayout(3, 2));
		box1 = new Checkbox("CandleStick", false);
		box2 = new Checkbox("Wrench", false);
		box3 = new Checkbox("Rope", false);
		box4 = new Checkbox("Revolver", false);
		box5 = new Checkbox("Knife", false);
		box6 = new Checkbox("Lead Pipe", false);
		add(box1);
		add(box2);
		add(box3);
		add(box4);
		add(box5);
		add(box6);
	}
}