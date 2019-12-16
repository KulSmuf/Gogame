package board;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private String messageLog;
	
	private char currentPlayer;
	private char[][] board;
	private Stone[][] stoneBoard;
	private boolean pass = false;
	private int[] ko = null;

	public Board( int size ){
		board = new char[size][size];
		stoneBoard = new Stone[size][size];
		currentPlayer = 'B';
		
		for( int i=0;i<size;i++ ) {
			for( int j=0;j<size;j++ ) {
				board[i][j] = 'E';
				stoneBoard[i][j] = null;
			}
		}
	}
	
	public boolean pass() {
		return !( pass = !pass );
	}
	
	public void removeField( int[] cords, int nr, int nc ) {
		try {
			if( board[nr][nc] != 'E' ) stoneBoard[nr][nc].getStoneChain().gainBreath(cords);
		}
		catch( ArrayIndexOutOfBoundsException e ) {}
	}
	
	public void removeStoneChain( CompositeStone stoneChain ) {
		for( Stone stone:stoneChain.getStoneChain() ) {
			int r = stone.getRow();
			int c = stone.getColumn();
			int[] cords = {r,c};
			board[r][c] = 'E';
			stoneBoard[r][c] = null;
			
			// up
			removeField( cords,r-1,c );
			// down
			removeField( cords,r+1,c );
			// left
			removeField( cords,r,c-1 );
			// right
			removeField( cords,r,c+1 );
			
			/*
			if( r != 0 ) {
				int nr = r-1;
				int nc = c;
				if( board[nr][nc] != 'E' ) stoneBoard[nr][nc].gainBreath();
			}
			
			// down
			if ( r != size-1 ) {
				int nr = r+1;
				int nc = c;
				if( board[nr][nc] != 'E' ) stoneBoard[nr][nc].gainBreath();
			}
			
			// left
			if( c != 0 ) {
				int nr = r;
				int nc = c-1;
				if( board[nr][nc] != 'E' ) stoneBoard[nr][nc].gainBreath();
			}
			
			// right
			if ( c != size-1 ) {
				int nr = r;
				int nc = c+1;
				if( board[nr][nc] != 'E' ) stoneBoard[nr][nc].gainBreath();
			}
			*/
		}
	}
	
	public int updateField( List<int[]> breaths, int[] cords, int[] newCords, CompositeStone[] stoneChain, String[] ret) {
		int capturedStones = 0;
		int nr = newCords[0];
		int nc = newCords[1];
		
		try {
			if( board[nr][nc] == 'E' ) breaths.add(newCords);
			else {
				stoneBoard[nr][nc].getStoneChain().reduceBreath(cords);
				if( board[nr][nc] == currentPlayer ) {
					stoneChain[0] = stoneBoard[nr][nc].getStoneChain();
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 0 ) {
					if( ret[0].length() > 0 ) ret[0]+=" ";
					ret[0]+=stoneBoard[nr][nc].getStoneChain().toString();
					capturedStones+=stoneBoard[nr][nc].getStoneChain().getChainLength();
					if( capturedStones == 1 ) ko = new int[] {nr,nc};
					removeStoneChain( stoneBoard[nr][nc].getStoneChain() );
					breaths.add(newCords);
				}
			}
		}
		catch( ArrayIndexOutOfBoundsException e ) {}
		return capturedStones;
	}
	
	public String makeMove(int r, int c) {
		ko = null;
		int[] cords = {r,c};
		int[] newCords;
		pass = false;
		
		List<int[]> breaths = new ArrayList<int[]>();
		int capturedStones = 0;
		String[] ret = new String[] {""};
		
		CompositeStone[] stoneChain = new CompositeStone[1];
		stoneChain[0] = null;
		
		// up
		newCords = new int[]{ r-1,c };
		capturedStones += updateField(breaths, cords, newCords, stoneChain, ret);
		// down
		newCords = new int[]{ r+1,c };
		capturedStones += updateField(breaths, cords, newCords, stoneChain, ret);
		// left
		newCords = new int[]{ r,c-1 };
		capturedStones += updateField(breaths, cords, newCords, stoneChain, ret);
		// right
		newCords = new int[]{ r,c+1 };
		capturedStones += updateField(breaths, cords, newCords, stoneChain, ret);
		
		// conclusion
		if( stoneChain[0] == null ) stoneBoard[r][c] = new Stone( r,c, breaths );
		else stoneBoard[r][c] = new Stone( r,c,breaths, stoneChain[0] );
		board[r][c] = currentPlayer;
		
		if( capturedStones == 0 ) ret[0] = "0";
		else ret[0] = Integer.toString(capturedStones) + " " + ret[0];
		
		messageLog = Integer.toString(r) + "," + Integer.toString(c) + " " + ret[0];
		
		if( currentPlayer == 'W' ) currentPlayer = 'B';
		else currentPlayer = 'W';
		
		if( ret[0].compareTo("0") == 0 ) ret[0] = "1";
		return ret[0];
	}

	public boolean checkField( int nr, int nc ) {
		try {
			if( board[nr][nc] == 'E' ) return true;
			else if( board[nr][nc] == currentPlayer ) {
				if( stoneBoard[nr][nc].getBreaths() > 1 ) return true;
			}
			else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 1 ) return true;
		}
		catch( ArrayIndexOutOfBoundsException e ) {}
		
		return false;
	}
	
	public boolean checkCorrectness(int r, int c) {
		if( board[r][c] == 'E' ) {
			if( ko != null && ko[0] == r && ko[1] == c ) return false;
			
			// up
			if( checkField(r-1, c) ) return true;
			// down
			if( checkField(r+1, c) ) return true;
			// left
			if( checkField(r, c-1) ) return true;
			// right
			if( checkField(r, c+1) ) return true;
		}
		return false;
	}
	
	public String getMessageLog() {
		return messageLog;
	}
	
	public void setMessage(String message) {
		this.messageLog = message;
	}
}
