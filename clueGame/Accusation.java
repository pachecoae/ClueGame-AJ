package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Accusation extends JDialog {
	private static final long serialVersionUID = 1L;
	ClueGame game;
	private boolean isCorrect;

	public Accusation(ClueGame game) {
		this.game = game;
		setTitle("Make an Accusation");
		setSize(400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fillPanel();
	}

	private void fillPanel() {
		JLabel choice = new JLabel("Person Choice");
		choice.setLocation(20, 40);
		choice.setSize(100, 25);
		getContentPane().add(choice);

		final JComboBox<String> personBox = new JComboBox<String>();
		personBox.addItem("Professor Plum");
		personBox.addItem("Mr. Green");
		personBox.addItem("Mrs. White");
		personBox.addItem("Mrs. Peacock");
		personBox.addItem("Miss Scarlet");
		personBox.addItem("ColonelMustard");
		personBox.setLocation(200, 40);
		personBox.setSize(150, 25);
		getContentPane().add(personBox);

		JLabel wpnChoice = new JLabel("Weapon Choice");
		wpnChoice.setLocation(20, 140);
		wpnChoice.setSize(150, 25);
		getContentPane().add(wpnChoice);

		final JComboBox<String> wpnBox = new JComboBox<String>();
		wpnBox.addItem("Lead Pipe");
		wpnBox.addItem("Revolver");
		wpnBox.addItem("Knife");
		wpnBox.addItem("Candlestick");
		wpnBox.addItem("Wrench");
		wpnBox.addItem("Rope");
		wpnBox.setLocation(200, 140);
		wpnBox.setSize(150, 25);
		getContentPane().add(wpnBox);

		JLabel roomChoice = new JLabel("Room Choice");
		roomChoice.setLocation(20, 240);
		roomChoice.setSize(150, 25);
		getContentPane().add(roomChoice);

		final JComboBox<String> roomBox = new JComboBox<String>();
		roomBox.addItem("Conservatory");
		roomBox.addItem("Kitchen");
		roomBox.addItem("Ballroom");
		roomBox.addItem("Billiard Room");
		roomBox.addItem("Library");
		roomBox.addItem("Study");
		roomBox.addItem("Dining Room");
		roomBox.addItem("Lounge");
		roomBox.setLocation(200, 240);
		roomBox.setSize(150, 25);
		getContentPane().add(roomBox);

		JButton submit = new JButton("Make Accusation!");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Solution guess = new Solution(personBox.getSelectedItem().toString(), wpnBox.getSelectedItem()
						.toString(), roomBox.getSelectedItem().toString());
				if(game.checkAccusation(guess)){
					JOptionPane.showMessageDialog(new JOptionPane(),
							"Congratulations! Your accusation was correct and you caught the murderer!", "You Win!", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
					
				}
				else{
					JOptionPane.showMessageDialog(new JOptionPane(),
							"Sorry! Your accusation was wrong!", "Wrong!", JOptionPane.ERROR_MESSAGE);
					game.turn++;
					game.getBoard().movePlayer(game.players.get(game.turn % 6));
					dispose();
				}
				
			}
		});

		submit.setSize(150, 30);
		submit.setLocation(100, 300);
		getContentPane().add(submit);

		setVisible(true);
	}
}
