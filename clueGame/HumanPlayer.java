package clueGame;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanPlayer extends Player {
	// public List<Card> suggestion = new ArrayList<>();
	public HumanPlayer(String name, Color color, int col, int row) {
		super(name, color, col, row);
		isHuman = true;
	}

	public void createSuggestion(){
		JFrame frame = new JFrame();
		frame.setName("Make a Suggestion");
		frame.setSize(400, 400);
		frame.getContentPane().setLayout(null);
		
		
		JLabel choice = new JLabel("Person Choice");
		choice.setLocation(20, 40);
		choice.setSize(100, 25);
		frame.getContentPane().add(choice);

		JComboBox<String> personBox = new JComboBox<String>();
		personBox.addItem("Professor Plum");
		personBox.addItem("Mr. Green");
		personBox.addItem("Mrs. White");
		personBox.addItem("Mrs. Peacock");
		personBox.addItem("Miss Scarlet");
		personBox.addItem("ColonelMustard");
		personBox.setLocation(200, 40);
		personBox.setSize(150, 25);
		frame.getContentPane().add(personBox);
		
		JLabel wpnChoice = new JLabel("Weapon Choice");
		wpnChoice.setLocation(20, 140);
		wpnChoice.setSize(150, 25);
		frame.getContentPane().add(wpnChoice);

		JComboBox<String> wpnBox = new JComboBox<String>();
		wpnBox.addItem("Lead Pipe");
		wpnBox.addItem("Revolver");
		wpnBox.addItem("Knife");
		wpnBox.addItem("Candlestick");
		wpnBox.addItem("Wrench");
		wpnBox.addItem("Rope");
		wpnBox.setLocation(200, 140);
		wpnBox.setSize(150, 25);
		frame.getContentPane().add(wpnBox);
		
		JLabel roomChoice = new JLabel("Room Choice");
		roomChoice.setLocation(20, 240);
		roomChoice.setSize(150, 25);
		frame.getContentPane().add(roomChoice);

		JLabel roomIn = new JLabel();
		roomIn.setLocation(200, 240);
		roomIn.setSize(150, 25);
		frame.getContentPane().add(roomIn);

		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
