package experiment;

import java.util.*;

public class IntBoard {
	public static int NUM_ROWS = 4;
	public static int NUM_COLS = 4;

	private BoardCell[][] board;
	private HashSet<BoardCell> targets;
	private Map<BoardCell, LinkedList<BoardCell>> adjList;
	private Set<BoardCell> visited;

	// Constructor
	public IntBoard() {
		board = new BoardCell[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new BoardCell(i, j);
			}
		}
	}

	// "Getters"
	public HashSet<BoardCell> getTargets() {
		return targets;
	}

	public LinkedList<BoardCell> getAdjList(BoardCell cell) {
		return adjList.get(cell);
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}

	// Target-Finding Algorithm
	public void calcAdjacencies() {
		adjList = new HashMap<BoardCell, LinkedList<BoardCell>>();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				// Add the current cell to the Map and add the LinkedList
				adjList.put(board[i][j], new LinkedList<BoardCell>());
				// Four possible cases for each cell
				if (i - 1 > -1)
					adjList.get(board[i][j]).add(board[i - 1][j]);
				if (j - 1 > -1)
					adjList.get(board[i][j]).add(board[i][j - 1]);
				if (i + 1 < NUM_ROWS)
					adjList.get(board[i][j]).add(board[i + 1][j]);
				if (j + 1 < NUM_COLS)
					adjList.get(board[i][j]).add(board[i][j + 1]);
			}
		}
	}

	public void calcTargets(BoardCell initialCell, int moves) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(initialCell);
		findAllTargets(initialCell, moves);
	}

	public void findAllTargets(BoardCell cell, int moves) {
		LinkedList<BoardCell> adjCells = nonVisitedAdjCells(cell);
		for (BoardCell c : adjCells) {
			visited.add(c);
			if (moves == 1)
				targets.add(c);
			else
				findAllTargets(c, moves - 1);
			visited.remove(c);
		}

	}

	public LinkedList<BoardCell> nonVisitedAdjCells(BoardCell cell) {
		LinkedList<BoardCell> nonVisitedCells = new LinkedList<BoardCell>();
		LinkedList<BoardCell> adjList = getAdjList(cell);
		for (BoardCell c : adjList) {
			if (!visited.contains(c)) {
				nonVisitedCells.add(c);
			}
		}
		return nonVisitedCells;
	}
}
