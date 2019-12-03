package server;

public class Board {
	
	private char currentPlayer;
	private char[][] board;

	Board( int size ){
		board = new char[size][size];
		currentPlayer = 'B';
	}
	
	public char whichPlayerTurn() {
		return currentPlayer;
	}
}
