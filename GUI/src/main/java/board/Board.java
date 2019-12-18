package board;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private String messageLog;
	public int size;
	private char currentPlayer;
	private char[][] board;
	private Stone[][] stoneBoard;
	private boolean pass = false;
	private int[] ko = null;

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
	
	public boolean pass() {
		if( currentPlayer == 'W' ) currentPlayer = 'B';
		else currentPlayer = 'W';
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
					if( stoneChain[0] == null ) stoneChain[0] = stoneBoard[nr][nc].getStoneChain();
					else stoneChain[0].merge(stoneBoard[nr][nc].getStoneChain());
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
		System.out.println(" tura gracza: " + currentPlayer);
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

	public Stone[][] getStones(){
		return stoneBoard;
	}
	
	public char getStoneColor( Stone stone ) {
		return board[  stone.getRow() ][ stone.getColumn() ];
	}
	
	public void getColor() {
		for( int i=0;i<9;i++ ) {
			for( int j=0;j<9;j++ ) {
				if( board[j][i] > 'A' ) System.out.print( board[j][i] );
				else System.out.print( (char)( board[j][i] + '0' ) );
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public int[] getDirection( int[] A, int[] B ) {
		return new int[] { B[0] - A[0], B[1] - A[1] };
	}
	
	public void turn( int[] direction ) {
		int x = direction[0];
		int y = direction[1];
		
		direction[0] = -y;
		direction[1] = x;
	}
	
	public int[] addVector( int[] A, int[] Vec ) {
		return new int[] { A[0] + Vec[0], A[1] + Vec[1] };
	}
	
	public void colorBoard( int[] cords, int[] BeyeNum, int[] WeyeNum ) {
		char thisStatus = 'U';
		List< int[] > uncheckedFields = new ArrayList<int[]>();
		
		uncheckedFields.add( cords );
		int[] field;
		do {
			field = uncheckedFields.get(0);
			uncheckedFields.remove(0);
			board[ field[0] ][ field[ 1 ] ] = 'U';
			int r;
			int c;
			char neighbourStatus;
			int[] vec = new int[] {0,1};
			
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' ) uncheckedFields.add( new int[] {r,c} );
				if( neighbourStatus == 'B' || neighbourStatus == 'W' ) {
					if( thisStatus != 'U' && thisStatus != neighbourStatus ) {
						thisStatus = 'N';
						break;
					}
					else thisStatus = neighbourStatus;
				}
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn( vec );
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' ) uncheckedFields.add( new int[] {r,c} );
				if( neighbourStatus == 'B' || neighbourStatus == 'W' ) {
					if( thisStatus != 'U' && thisStatus != neighbourStatus ) {
						thisStatus = 'N';
						break;
					}
					else thisStatus = neighbourStatus;
				}
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn( vec );
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' ) uncheckedFields.add( new int[] {r,c} );
				if( neighbourStatus == 'B' || neighbourStatus == 'W' ) {
					if( thisStatus != 'U' && thisStatus != neighbourStatus ) {
						thisStatus = 'N';
						break;
					}
					else thisStatus = neighbourStatus;
				}
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn( vec );
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' ) uncheckedFields.add( new int[] {r,c} );
				if( neighbourStatus == 'B' || neighbourStatus == 'W' ) {
					if( thisStatus != 'U' && thisStatus != neighbourStatus ) {
						thisStatus = 'N';
						break;
					}
					else thisStatus = neighbourStatus;
				}
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
		}
		while( uncheckedFields.size() > 0 );
		
		if( thisStatus == 'U' ) return;
		if( thisStatus == 'W' ) thisStatus = (char) (1+2*(WeyeNum[0]++));
		if( thisStatus == 'B' ) thisStatus = (char) (2*(BeyeNum[0]++));
		
		uncheckedFields.add( cords );
		do {
			field = uncheckedFields.get(0);
			board[ field[0] ][ field[ 1 ] ] = thisStatus;
			uncheckedFields.remove(0);
			int r;
			int c;
			char neighbourStatus;
			int[] vec = new int[] {0,1};
			
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' || neighbourStatus == 'U' ) uncheckedFields.add( new int[] {r,c} );
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn(vec);
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' || neighbourStatus == 'U' ) uncheckedFields.add( new int[] {r,c} );
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn(vec);
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' || neighbourStatus == 'U' ) uncheckedFields.add( new int[] {r,c} );
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			
			turn(vec);
			try {
				r = field[0] + vec[0];
				c = field[1] + vec[1];
				neighbourStatus = board[ r ][ c ];
				if( neighbourStatus == 'E' || neighbourStatus == 'U' ) uncheckedFields.add( new int[] {r,c} );
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
		}
		while( uncheckedFields.size() > 0 );
	}
	
	public void addStoneChain( List< CompositeStone > stoneChains, int i, int j ) {
		if( stoneBoard[i][j] == null ) return;
		CompositeStone newChain = stoneBoard[i][j].getStoneChain();
		boolean isIn = false;
		for( CompositeStone stoneChain: stoneChains ) {
			if( isIn = stoneChain.equals( newChain ) ) break;
		}
		if( !isIn ) stoneChains.add(newChain);
	}
	
	public boolean isDead( CompositeStone chain ) {
		List< int[] > breaths = chain.getBreathArray();
		char eye = 'a';
		
		for( int[] breath: breaths ) {
			if( board[ breath[0] ][ breath[1] ] < 'A' ) {
				if( eye != 'a' && eye != board[ breath[0] ][ breath[1] ] ) return false;
				else eye = board[ breath[0] ][ breath[1] ];
			}
		}
		
		for( Stone stone: chain.getStoneChain() ) {
			board[ stone.getRow() ][ stone.getColumn() ] = 'D';
		}
		
		return true;
	}
	
	public String countTerritory() {
		// Nobody, Undefined, Dead, Eye(number)
		String ret = "exit";
		List<CompositeStone> stoneChains = new ArrayList<CompositeStone>();
		int[] BeyeNum = new int[] {0};
		int[] WeyeNum = new int[] {0};
		
		getColor();
		
		for( int i=0; i<board.length; i++ ) {
			for( int j=0; j<board.length; j++ ) {
				if( board[i][j] == 'E' ) colorBoard( new int[] {i,j}, BeyeNum, WeyeNum );
				addStoneChain( stoneChains, i,j );
			}
		}
		
		getColor();
		
		boolean finish = false;
		while( !finish ) {
			int n = stoneChains.size();
			for( int i=0; i<stoneChains.size(); i++ ) {
				if( isDead( stoneChains.get(i) ) ) stoneChains.remove(i--);
			}
			finish = n == stoneChains.size();
			
			getColor();
			
			for( int i=0; i<board.length; i++ ) {
				for( int j=0; j<board.length; j++ ) {
					if( board[i][j] != 'W' && board[i][j] != 'B' ) board[i][j] ='E';
				}
			}
			
			getColor();
			
			BeyeNum = new int[] {0};
			WeyeNum = new int[] {0};
			for( int i=0; i<board.length; i++ ) {
				for( int j=0; j<board.length; j++ ) {
					if( board[i][j] == 'E' ) colorBoard( new int[] {i,j}, BeyeNum, WeyeNum );
				}
			}
			getColor();
		}
		int Bscore = 0;
		int Wscore = 0;
		
		for( int i=0; i<board.length; i++ ) {
			for( int j=0; j<board.length; j++ ) {
				if( board[i][j] == 'N'|| board[i][j] == 'U' ) continue;
				if( board[i][j]%2 == 0 ) Bscore++;
				if( board[i][j]%2 == 1 ) Wscore++;
			}
		}
		ret = "exit " + Bscore + ':' + Wscore;
		return ret;
	}
	
	public void setMessage(String message) {
		this.messageLog = message;
	}
}
