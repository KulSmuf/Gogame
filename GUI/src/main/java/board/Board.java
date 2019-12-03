package board;

public class Board {
	
	private String messageLog;
	
	private int size;
	private char currentPlayer;
	private char[][] board;
	private Stone[][] stoneBoard;

	public Board( int size ){
		this.size = size;
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
	
	public void removeStoneChain( CompositeStone stoneChain ) {
		for( Stone stone:stoneChain.getStoneChain() ) {
			int r = stone.getRow();
			int c = stone.getColumn();
			board[r][c] = 'E';
			stoneBoard[r][c] = null;
			
			// up
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
		}
	}
	
	public String makeMove(int r, int c) {
				
		int breaths = 0;
		int capturedStones = 0;
		String ret = "";
		
		CompositeStone stoneChain = null;
		
		// up
		if( r != 0 ) {
			int nr = r-1;
			int nc = c;
			if( board[nr][nc] == 'E' ) breaths++;
			else {
				stoneBoard[nr][nc].reduceBreath();
				if( board[nr][nc] == currentPlayer ) {
					stoneChain = stoneBoard[nr][nc].getStoneChain();
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 0 ) {
					if( ret.length() > 0 ) ret+=" ";
					ret+=stoneBoard[nr][nc].getStoneChain().toString();
					capturedStones+=stoneBoard[nr][nc].getStoneChain().getChainLength();
					removeStoneChain( stoneBoard[nr][nc].getStoneChain() );
					breaths++;
				}
			}
		}
		
		// down
		if ( r != size-1 ) {
			int nr = r+1;
			int nc = c;
			if( board[nr][nc] == 'E' ) breaths++;
			else {
				stoneBoard[nr][nc].reduceBreath();
				if( board[nr][nc] == currentPlayer ) {
					if( stoneChain == null ) stoneChain = stoneBoard[nr][nc].getStoneChain();
					else stoneChain.merge( stoneBoard[nr][nc].getStoneChain() );
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 0 ) {
					if( ret.length() > 0 ) ret+=" ";
					ret+=stoneBoard[nr][nc].getStoneChain().toString();
					capturedStones+=stoneBoard[nr][nc].getStoneChain().getChainLength();
					removeStoneChain( stoneBoard[nr][nc].getStoneChain() );
					breaths++;
				}
			}
		}
		
		// left
		if( c != 0 ) {
			int nr = r;
			int nc = c-1;
			if( board[nr][nc] == 'E' ) breaths++;
			else {
				stoneBoard[nr][nc].reduceBreath();
				if( board[nr][nc] == currentPlayer ) {
					if( stoneChain == null ) stoneChain = stoneBoard[nr][nc].getStoneChain();
					else stoneChain.merge( stoneBoard[nr][nc].getStoneChain() );
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 0 ) {
					if( ret.length() > 0 ) ret+=" ";
					ret+=stoneBoard[nr][nc].getStoneChain().toString();
					capturedStones+=stoneBoard[nr][nc].getStoneChain().getChainLength();
					removeStoneChain( stoneBoard[nr][nc].getStoneChain() );
					breaths++;
				}
			}
		}
		
		// right
		if ( c != size-1 ) {
			int nr = r;
			int nc = c+1;
			if( board[nr][nc] == 'E' ) breaths++;
			else {
				stoneBoard[nr][nc].reduceBreath();
				if( board[nr][nc] == currentPlayer ) {
					if( stoneChain == null ) stoneChain = stoneBoard[nr][nc].getStoneChain();
					else stoneChain.merge( stoneBoard[nr][nc].getStoneChain() );
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 0 ) {
					if( ret.length() > 0 ) ret+=" ";
					ret+=stoneBoard[nr][nc].getStoneChain().toString();
					capturedStones+=stoneBoard[nr][nc].getStoneChain().getChainLength();
					removeStoneChain( stoneBoard[nr][nc].getStoneChain() );
					breaths++;
				}
			}
		}
		
		// conclusion
		if( stoneChain == null ) stoneBoard[r][c] = new Stone( r,c, breaths );
		else stoneBoard[r][c] = new Stone( r,c,breaths, stoneChain );
		board[r][c] = currentPlayer;
		
		if( capturedStones == 0 ) ret = "0";
		else ret = Integer.toString(capturedStones) + " " + ret;
		
		messageLog = Integer.toString(r) + "," + Integer.toString(c) + " " + ret;
		
		if( currentPlayer == 'W' ) currentPlayer = 'B';
		else currentPlayer = 'W';
		
		if( ret.compareTo("0") == 0 ) ret = "1";
		return ret;
	}
	
	public boolean checkCorrectness(int r, int c) {
		if( board[r][c] == 'E' ) {
			
			// up
			if ( r != 0 ) {
				int nr = r-1;
				int nc = c;
				
				if( board[nr][nc] == 'E' ) return true;
				else if( board[nr][nc] == currentPlayer ) {
					if( stoneBoard[nr][nc].getBreaths() > 1 ) return true;
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 1 ) return true;
			}
			
			// down
			if ( r != size-1 ) {
				int nr = r+1;
				int nc = c;
				
				if( board[nr][nc] == 'E' ) return true;
				else if( board[nr][nc] == currentPlayer ) {
					if( stoneBoard[nr][nc].getBreaths() > 1 ) return true;
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 1 ) return true;
			}
			
			// left
			if ( c != 0 ) {
				int nr = r;
				int nc = c-1;
				
				if( board[nr][nc] == 'E' ) return true;
				else if( board[nr][nc] == currentPlayer ) {
					if( stoneBoard[nr][nc].getBreaths() > 1 ) return true;
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 1 ) return true;
			}
			
			// right
			if ( c != size-1 ) {
				int nr = r;
				int nc = c+1;
				
				if( board[nr][nc] == 'E' ) return true;
				else if( board[nr][nc] == currentPlayer ) {
					if( stoneBoard[nr][nc].getBreaths() > 1 ) return true;
				}
				else if( stoneBoard[nr][nc].getStoneChain().getBreaths() == 1 ) return true;
			}
		}
		return false;
	}
	
	public char whichPlayerTurn() {
		return currentPlayer;
	}
	
	public String getMessageLog() {
		return messageLog;
	}
	
	public void setExitMessage() {
		this.messageLog = "exit";
	}
}
