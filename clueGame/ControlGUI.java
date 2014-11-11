package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {

	private ClueGame game;
	public JTextField dieTextBox;
	public JTextField displayTurnBox;
	public JTextField guessTextBox;
	public JTextField responseTextBox;

	public ControlGUI(ClueGame game) {
		this.game = game;
		setSize(700, 150);
		add(comboPanel(), BorderLayout.SOUTH);
	}

	private JPanel comboPanel() {
		JPanel panel = new JPanel();
		panel.setSize(956, 200);
		panel.setLayout(new GridLayout(2, 1));

		panel.add(upperPanel(), BorderLayout.NORTH);
		panel.add(lowerPanel(), BorderLayout.SOUTH);

		return panel;
	}

	private JPanel upperPanel() {
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 3));

		upperPanel.add(createLeftN(), BorderLayout.WEST);
		upperPanel.add(createCenterN(), BorderLayout.CENTER);
		upperPanel.add(createRightN(), BorderLayout.EAST);

		return upperPanel;
	}

	private JPanel lowerPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));

		southPanel.add(createLeftC(), BorderLayout.WEST);
		southPanel.add(createRightC(), BorderLayout.EAST);
		southPanel.add(createCenterC(), BorderLayout.CENTER);

		return southPanel;
	}

	// Creating the center panel elements

	private JPanel createLeftC() {
		JPanel leftPanel = new JPanel();
		JTextField textBox = new JTextField(20);
		textBox.setEditable(false);
		JLabel name = new JLabel("Roll");
		leftPanel.setLayout(new GridLayout(2, 1));
		leftPanel.add(name);
		leftPanel.add(textBox);
		dieTextBox = textBox;
		leftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return leftPanel;
	}

	private JPanel createRightC() {
		JPanel rightPanel = new JPanel();
		JTextField textBox = new JTextField(20);
		textBox.setEditable(false);
		JLabel name = new JLabel("Guess");
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(name);
		rightPanel.add(textBox);
		guessTextBox = textBox;
		rightPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return rightPanel;
	}

	private JPanel createCenterC() {
		JPanel centerPanel = new JPanel();
		JTextField textBox = new JTextField(20);
		textBox.setEditable(false);
		JLabel name = new JLabel("Response");
		centerPanel.setLayout(new GridLayout(2, 1));
		centerPanel.add(name);
		centerPanel.add(textBox);
		responseTextBox = textBox;
		centerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return centerPanel;
	}

	// Creating the north panel elements

	private JPanel createCenterN() {
		JPanel centerPanel = new JPanel();
		JButton nextPlayer = new JButton("Next Player");

		class ButtonClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.nextPlayer();
			}
		}

		nextPlayer.addActionListener(new ButtonClickListener());

		centerPanel.setLayout(new GridLayout(1, 1));
		centerPanel.add(nextPlayer);
		return centerPanel;
	}

	private JPanel createRightN() {
		JPanel rightPanel = new JPanel();
		JButton makeAnAccusation = new JButton("Make an Accusation");
		makeAnAccusation.setSize(20, 20);
		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(makeAnAccusation);
		return rightPanel;
	}

	private JPanel createLeftN() {
		JPanel leftPanel = new JPanel();
		JLabel text = new JLabel("Whose turn?");
		JTextField turn = new JTextField(20);
		turn.setEditable(false);

		turn.setText(game.players.get(game.turn % 6).getName());

		displayTurnBox = turn;

		leftPanel.setLayout(new GridLayout(2, 1));
		leftPanel.add(text);
		leftPanel.add(turn);
		return leftPanel;
	}

	// Create the right display panel
	public JPanel createMyCards() {
		JPanel panel = new JPanel();

		JTextField firstCard = new JTextField(20);
		firstCard.setText(game.players.get(0).getCards().get(0).name);
		firstCard.setEditable(false);

		JTextField secondCard = new JTextField(20);
		secondCard.setText(game.players.get(0).getCards().get(1).name);
		secondCard.setEditable(false);

		JTextField thirdCard = new JTextField(20);
		thirdCard.setText(game.players.get(0).getCards().get(2).name);
		thirdCard.setEditable(false);

		panel.setLayout(new GridLayout(3, 1));
		panel.add(firstCard);
		panel.add(secondCard);
		panel.add(thirdCard);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards:"));

		return panel;
	}
}
