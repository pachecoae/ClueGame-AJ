package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int MAX_ROWS = 50;
	public static int MAX_COLS = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Set<BoardCell> visited;
	private static Map<Character, String> rooms;
	private Set<BoardCell> targetList;
	private Map<BoardCell, LinkedList<BoardCell>> adjList;
	private ArrayList<Player> players;
	private DetectiveNotes dNotes = new DetectiveNotes();
	private JTextField turn;
	private ClueGame game;
	private int diceRoll;
	private boolean badClick;

	// private myDialog dialog;

	public Board(final ClueGame clueGame) {
		this.game = clueGame;
		badClick = false;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				for (BoardCell b : targetList) {
					if (e.getY() <= b.getPixelCol() + 30 && e.getY() >= b.getPixelCol()) {
						if (e.getX() <= b.getPixelRow() + 30 && e.getX() >= b.getPixelRow()) {
							game.players.get(0).row = b.getRow();
							game.players.get(0).col = b.getCol();
							game.players.get(0).pixelRow = b.getPixelRow();
							game.players.get(0).pixelCol = b.getPixelCol();
							game.repaint();
							game.canMove = true;
							badClick = false;
							if(board[game.players.get(0).getRow()][game.players.get(0).getCol()].isRoom()){
								((HumanPlayer) game.players.get(0)).createSuggestion(rooms.get(((RoomCell) board[game.players.get(0).getRow()][game.players.get(0).getCol()]).getInitial()), game);
							}
							break;
						}
					} else {
						badClick = true;
					}
				}
				if (badClick == true) {
					JOptionPane.showMessageDialog(new JOptionPane(),
							"Invalid selection. Please select a valid target.", "Invalid Selection Made",
							JOptionPane.ERROR_MESSAGE);
					badClick = false;
				}
			}
		});
	}

	public void movePlayer(Player p) {
		calcTargets(p.row, p.col, diceRoll());
		if (!p.isHuman()) {
			((ComputerPlayer) p).pickLocation(targetList);
			repaint();
			if (board[p.row][p.col].isRoom()) {
				List<Card> suggestion = ((ComputerPlayer) p).createSuggestion();
				game.handleSuggestion(suggestion.get(1).name, suggestion.get(0).name, suggestion.get(2).name, p);
			}
		}
		repaint();
		game.controlGUI.dieTextBox.setText("" + diceRoll);
		game.controlGUI.displayTurnBox.setText(game.players.get(game.turn % 6).getName());
	}

	public int diceRoll() {
		Random rand = new Random();
		diceRoll = rand.nextInt(6) + 1;
		return diceRoll;
	}

	public void drawFrame() {
		// Create a JFrame
		JFrame f = new JFrame();
		f.setSize(900, 850);
		f.setTitle("Clue Board");
		f.setLayout(null);

		// Add JPanel to your JFrame
		JPanel panel = new JPanel();
		panel.setLocation(0, 0);
		panel.setSize(750, 750);

		panel.setVisible(true);

		f.add(panel);
		// f.setJMenuBar(createMenuBar());

		// Necessary at end
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(this);
		// dialog.setVisible(true);
		f.setVisible(true);
		f.repaint();
	}

	public void drawNames(Graphics g) {
		g.drawString("Dungeon", 30, 30);
		g.drawString("Bedroom", 30, 10 * 30);
		g.drawString("Library", 2 * 30, 18 * 30);
		g.drawString("Bathroom", 8 * 30 - 20, 10 * 30);
		g.drawString("Theatre", 8 * 30, 18 * 30);
		g.drawString("Study", 21 * 30, 13 * 30);
		g.drawString("Kitchen", 8 * 30, 1 * 30);
		g.drawString("Workshop", 18 * 30, 1 * 30);
		g.drawString("Dining Room", 12 * 30, 5 * 30);
		g.drawString("Closet", 13 * 30, 9 * 30);
	}

	// In that class, override paintComponent(Graphics g) method.
	@Override
	public void paintComponent(Graphics g) {
		// Inside the method, use draw commands as needed to create your image.
		// paintComponent will be called automatically when the screen needs to be redrawn (first displayed, minimized,
		// maximized).
		// Inside paintComponent, call super.paintComponent for required housekeeping.
		super.paintComponent(g);
		// If you need to update the drawing, do not call paintComponent directly. Instead call repaint().

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				board[i][j].draw(g, this);
			}
		}

		Color temp = g.getColor();

		if (game.turn % 6 == 0 && game.turn > 1) {
			g.setColor(Color.GREEN);
			for (BoardCell b : targetList) {
				g.fillRect(b.getPixelRow(), b.getPixelCol(), 30, 30);
			}
			g.setColor(temp);
		}

		for (Player p : players) {
			p.draw(g);
		}

		drawNames(g);
	}

	public void loadBoardConfig(String mapFile) throws FileNotFoundException, BadConfigFormatException {
		board = new BoardCell[MAX_ROWS][MAX_COLS];
		FileReader reader = new FileReader(mapFile);
		Scanner fileIn = new Scanner(reader);
		// Set numCols and numRows initially to 0
		numRows = 0;
		numColumns = 0;

		// Read board configuration file line by line

		while (fileIn.hasNextLine()) {
			// Read the first line
			String newLine = fileIn.nextLine();
			// Split the row into string "cells"
			String[] parts = newLine.split(",");
			// Set the numCols initially, or check to make sure it's the same
			if (numColumns == 0)
				numColumns = parts.length;
			else if (parts.length != numColumns) {
				fileIn.close();
				throw new BadConfigFormatException("Column length mismatch!");
			}
			// Loop through the parts array to initialize cells
			for (int i = 0; i < parts.length; i++) {
				// Check first to make sure the first letter is an initial
				if (!rooms.containsKey(parts[i].charAt(0))) {
					fileIn.close();
					throw new BadConfigFormatException("Bad room initial");
				}
				if (parts[i].equals("W")) {
					board[numRows][i] = new WalkwayCell(numRows, i);
				} else
					board[numRows][i] = new RoomCell(numRows, i, parts[i]);
			}
			// Update rows
			numRows++;
		}
		fileIn.close();
	}

	// Adjacency and Target Finding
	public void calcAdjacencies() {
		adjList = new HashMap<BoardCell, LinkedList<BoardCell>>();
		// Calculate a list for each cell on the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell currentCell = board[i][j];
				// Simple case, if cell is a doorway then only adj is direction of doorway
				if (currentCell.isDoorway()) {
					// Convert BoardCell to RoomCell
					RoomCell currentRoomCell = (RoomCell) currentCell;
					// Create the LinkedList and add the appropriate direction
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (currentRoomCell.getDoorDirection() == DoorDirection.DOWN)
						currentAdjList.add(board[i + 1][j]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.RIGHT)
						currentAdjList.add(board[i][j + 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.LEFT)
						currentAdjList.add(board[i][j - 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.RIGHT)
						currentAdjList.add(board[i - 1][j]);
					// Add the pair to the adjList map
					adjList.put(currentCell, currentAdjList);
				} else if (currentCell.isRoom()) {
					// Add an empty list
					adjList.put(currentCell, new LinkedList<BoardCell>());
				} else if (currentCell.isWalkway()) {
					// Create the linked list and add all directions if they are
					// allowed moves
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (isViableMove(i + 1, j, DoorDirection.UP))
						currentAdjList.add(board[i + 1][j]);
					if (isViableMove(i - 1, j, DoorDirection.DOWN))
						currentAdjList.add(board[i - 1][j]);
					if (isViableMove(i, j + 1, DoorDirection.LEFT))
						currentAdjList.add(board[i][j + 1]);
					if (isViableMove(i, j - 1, DoorDirection.RIGHT))
						currentAdjList.add(board[i][j - 1]);
					// Add the linked list to the map
					adjList.put(board[i][j], currentAdjList);
				}
			}
		}
	}

	public boolean isViableMove(int row, int col, DoorDirection allowedDoorDirection) {
		// If the cell is out of bounds return false
		if (row < 0 || row >= numRows)
			return false;
		if (col < 0 || col >= numColumns)
			return false;
		// If cell is a room cell, check allowed direction
		if (getCellAt(row, col).isRoom()) {
			if (getCellAt(row, col).isDoorway()) {
				RoomCell aRoomCell = (RoomCell) getCellAt(row, col);
				if (allowedDoorDirection == aRoomCell.getDoorDirection())
					return true;
			}
			// Not a doorway, return false
			return false;
		}
		// Must be a walkway
		return true;
	}

	public void calcTargets(int row, int col, int moves) {
		targetList = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(getCellAt(row, col));
		findAllTargets(getCellAt(row, col), moves);

	}

	public void findAllTargets(BoardCell cell, int moves) {
		LinkedList<BoardCell> adjCells = nonVisitedAdjCells(cell);
		for (BoardCell c : adjCells) {
			visited.add(c);
			if (moves == 1 || c.isDoorway())
				targetList.add(c);
			else
				findAllTargets(c, moves - 1);
			visited.remove(c);
		}
	}

	public LinkedList<BoardCell> nonVisitedAdjCells(BoardCell cell) {
		LinkedList<BoardCell> nonVisitedCells = new LinkedList<BoardCell>();
		LinkedList<BoardCell> adjLinkedList = adjList.get(cell);
		for (BoardCell c : adjLinkedList) {
			if (!visited.contains(c)) {
				nonVisitedCells.add(c);
			}
		}
		return nonVisitedCells;
	}

	// Getters
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public RoomCell getRoomCellAt(int row, int col) {
		return (RoomCell) board[row][col];
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjList.get(board[row][col]);
	}

	public Set<BoardCell> getTargets() {
		return targetList;
	}

	// Setters
	public void setRooms(Map<Character, String> rooms) {
		Board.rooms = rooms;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;

	}

}
