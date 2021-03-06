package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import board.CompositeStone;
import board.Stone;

public class Bot extends Player {
	
	private Stone[][] stoneBoard;
	private List<int[]> myStones = new ArrayList<int[]>();
	private List<int[]> enemyStones = new ArrayList<int[]>();
	
	@Override
	public void run(){
		synchronized(message) {
			try {
				message.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		color = 'W';
		stoneBoard = board.getStones();
		startGame();
	}
	
	@Override
	public void startGame() {
		Thread opponentListener = new OpponentListener();
		opponentListener.start();
				
		while(true) {
			synchronized( message ) {
				try {
					message.wait();
				} catch (InterruptedException e) {
					System.out.println("Bot interrupted waiting for message");
				}
			}
			
			if( interpretPlayerMove() ) return;
			if( makeMove() ) return;
		}
	}

	public Stone getStone( int[] cords ) {
		return stoneBoard[ cords[0] ][ cords[1] ];
	}
	
	public int[] getEndangeredStones() {
		for( int[] cords: myStones ) {
			if( getStone(cords).getBreaths() == 1 ) return cords;
		}
		return null;
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
	
	public int checkNumberOfBreaths( int[] cords ) {
		List<int[]> breaths = new ArrayList<int[]>();
		int[] direction = new int[] {1,0};
		
		for(int i=0;i<4;i++) {
			try {
				Stone neighbour = getStone( addVector( cords, direction ) );
				if( neighbour == null ) breaths.add( addVector( cords, direction ) );
				else if( board.getStoneColor( neighbour ) == 'W' ) {
					List<int[]> newBreaths = new ArrayList<int[]>();
					for( int[] breath: neighbour.getStoneChain().getBreathArray() ) {
						
						if( !Arrays.equals(breath, cords) ) {
							boolean isIn = false;
							for( int[] myBreath: breaths ) {
								if( isIn = ( Arrays.equals(breath, myBreath) ) ) break;
							}
							if( !isIn ) newBreaths.add(breath);
						}
					}
					breaths.addAll( newBreaths );
				}
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			turn( direction );
		}
		return breaths.size();
	}
	
	public boolean tryProtect( int[] cords ) {
		
		int[] possibleMove = getStone( cords ).getStoneChain().getBreathArray().get(0);
		int breaths = checkNumberOfBreaths(possibleMove);
		
		if( breaths > 2 ) {
			String changes = board.makeMove( possibleMove[0], possibleMove[1] );
			applyChanges( changes, possibleMove );
			synchronized( opponent.flag ) {
				opponent.flag.notify();
			}
		}
		return breaths > 2;
	}
	
	public List< CompositeStone[] > splitByLeastBreaths( List<CompositeStone> list ){
		List< CompositeStone[] > splittedList = new ArrayList< CompositeStone[] >();
		List< CompositeStone > listtosplit = new ArrayList< CompositeStone >();
		
		int breaths = 0;
		
		for( CompositeStone chain: list ) {
			if( breaths != chain.getBreaths() ) {
				breaths = chain.getBreaths();
				if( listtosplit.size() > 0 ) {
					CompositeStone[] table = new CompositeStone[ listtosplit.size() ];
					for( int i=0; i< listtosplit.size(); i++ ) {
						table[i] = listtosplit.get(i);
					}
					
					splittedList.add(  table );
					listtosplit.clear();
				}
			}
			listtosplit.add(chain);
		}
		if( listtosplit.size() > 0 ) {
			CompositeStone[] table = new CompositeStone[ listtosplit.size() ];
			for( int i=0; i< listtosplit.size(); i++ ) {
				table[i] = listtosplit.get(i);
			}
			
			splittedList.add(  table );
		}
		
		return splittedList;
	}
	
	public List<int[][]> splitByMostBreaths( List< int[] > list ){
		List< int[][] > splittedList = new ArrayList< int[][] >();
		List< int[] > listtosplit = new ArrayList< int[] >();
		
		int breaths = -1;
		
		for( int[] move: list ) {
			if( breaths != checkNumberOfBreaths( move ) ) {
				breaths = checkNumberOfBreaths( move );
				if( listtosplit.size() > 0 ) {
					int[][] table = new int[ listtosplit.size() ][];
					for( int i=0; i< listtosplit.size(); i++ ) {
						table[i] = listtosplit.get(i);
					}
					splittedList.add( table );
					listtosplit.clear();
				}
			}
			listtosplit.add(move);
		}
		if( listtosplit.size() > 0 ) {
			int[][] table = new int[ listtosplit.size() ][];
			for( int i=0; i< listtosplit.size(); i++ ) {
				table[i] = listtosplit.get(i);
			}
			splittedList.add( table );
		}
		
		return splittedList;
	}
	
	public List<int[][]> getMovesByBreaths( CompositeStone[] chains ){
		List< int[] > moves = new ArrayList< int[] >();
		
		for(int i=0 ; i<chains.length; i++ ) {
			moves.addAll( chains[i].getBreathArray() );
		}
		SortMovesByBreaths sort = new SortMovesByBreaths();
		moves.sort(sort);
		
		return  splitByMostBreaths(moves);
	}
	
	public void applyChanges( String changes, int[] move ) {
		myStones.add( move );
		
		if( changes.length() > 1 ) {
			String[] splittedChanges = changes.split(" ");
			
			int n = Integer.parseInt( splittedChanges[0] );
			for( int i=0; i<n; i++ ) {
				for( int j=0; j< this.enemyStones.size(); j++ ) {
					if( Arrays.equals(parseCords( splittedChanges[i+1].split(",") ) , this.enemyStones.get(j)) ) {
						this.enemyStones.remove(j);
						j--;
					};
				}
			}
		}
	}
	
	public boolean pass() {
		if( board.pass() ) {
			String score = board.countTerritory();
			board.setMessage(score);
			synchronized( opponent.flag ) {
				opponent.flag.notify();
			}
			return true;
		}
		board.setMessage("pass");
		synchronized( opponent.flag ) {
			opponent.flag.notify();
		}
		return false;
	}

	public int checkNumberOfFree( int[] cords ) {
		int free = 0;
		int[] direction = new int[] {1,0};
		
		for(int i=0;i<4;i++) {
			try {
				Stone neighbour = getStone( addVector( cords, direction ) );
				if( neighbour == null ) free++;
			}
			catch( ArrayIndexOutOfBoundsException e ) {}
			turn( direction );
		}
		return free;
	}
	
	public boolean tryDefence(){
		
		List< int[] > posMoves = new ArrayList<int[]>();
		
		for( int i=0; i<board.getStones().length; i++ ) {
			for( int j=0;j<board.getStones().length;j++ ) {
				if( board.checkCorrectness( i , j  ) ) posMoves.add( new int[] {i,j} );
			}
		}
		
		SortMovesByBreaths sort = new SortMovesByBreaths();
		posMoves.sort(sort);
		
		for( int[] move: posMoves ) {
			if( board.checkCorrectness( move[0],move[1] ) && checkNumberOfFree(move) > 1 ) {
				String changes = board.makeMove( move[0],move[1] );
				applyChanges( changes, move );
				synchronized( opponent.flag ) {
					opponent.flag.notify();
				}
				return true;
			}
		}

		return false;
	}
	
	public boolean attack() {
		//board.getColor();
		
		List< int[][] > possibleMoves = new ArrayList<int[][]>();
		List< CompositeStone > enemyStones = new ArrayList<CompositeStone>();
		
		if( this.enemyStones.size() == 0 ) return pass();
				
		for( int[] eCords: this.enemyStones ) {
			boolean isIn = false;
			for( CompositeStone stoneChain: enemyStones ) {
				if( isIn = stoneChain.equals( getStone( eCords ).getStoneChain() ) ) break;
			}
			if( !isIn ) enemyStones.add( getStone( eCords ).getStoneChain() );
		}
				
		SortByBreaths sort = new SortByBreaths();
		enemyStones.sort(sort);
				
		List< CompositeStone[] > weakChains = splitByLeastBreaths( enemyStones );
				
		for( CompositeStone[] chains: weakChains ) {
			possibleMoves.addAll( getMovesByBreaths(chains)  );
		}
		
		for( int[][] moves: possibleMoves ) {
			List< int[] > moveList = new ArrayList<int[]>();
			for( int[] move: moves ) {
				moveList.add(move);
			}
			
			boolean finished = false;
			Random RNG = new Random();
			
			while( moveList.size() > 0 ) {
				int n = RNG.nextInt( moveList.size() );
				if( finished = board.checkCorrectness( moves[n][0] , moves[n][1]  ) ) {
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					String changes = board.makeMove( moves[n][0], moves[n][1] );
					applyChanges( changes, moves[n] );
					
					synchronized( opponent.flag ) {
						opponent.flag.notify();
					}
					
					break;
				}
				else {
					moveList.remove(n);
				}
			}
			
			if( finished ) return false;
		}
		
		if( tryDefence() ) return false;
		
		return pass();
	}
	
	public boolean makeMove() {
		int[] endangeredStone = getEndangeredStones();
		
		if( endangeredStone != null ) {
			boolean moved = tryProtect( endangeredStone );
			if( !moved ) endangeredStone = null;
		}
		if( endangeredStone == null ) {
			return attack();
		}
		return false;
	}

	public boolean interpretPlayerMove() {
		String respond = board.getMessageLog();
		
		if( respond.contains("exit") ) return true;
		
		if( respond.contains(",") ) {
			String[] arrayedRespond = respond.split(" ");
			int[] enemyCords = parseCords( arrayedRespond[0].split(",") );
			enemyStones.add(enemyCords);
			
			int n = Integer.parseInt( arrayedRespond[1] );
			for(int i=0;i<n; i++ ) {
				int[] droppedCord = parseCords( arrayedRespond[i+2].split(",") );
				int[] remove = null;
				
				for( int[] cord: myStones ) {
					if( Arrays.equals(cord, droppedCord) ) {
						remove = cord;
					}
				}
				myStones.remove(remove);
			}
		}
		return false;
	}
	
	class SortByBreaths implements Comparator<CompositeStone> 
	{ 
	    public int compare(CompositeStone a, CompositeStone b) 
	    { 
	        return a.getBreaths() - b.getBreaths(); 
	    } 
	} 
	
	class SortMovesByBreaths implements Comparator<int[]> 
	{ 
	    public int compare(int[] a, int[] b) 
	    { 
	        return checkNumberOfBreaths(b) - checkNumberOfBreaths(a); 
	    } 
	} 
}
