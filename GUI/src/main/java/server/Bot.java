package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import board.Board;
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
			makeMove();
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
		int[] direction = new int[] {0,1};
		turn( direction );
		for(int i=0;i<4;i++) {
			try {
				Stone neighbour = getStone( addVector( cords, direction ) );
				if( neighbour == null ) breaths.add( addVector( cords, direction ) );
				else if( board.getStoneColor( neighbour ) == 'W' ) {
					System.out.println(" sąsiad good for cord: " + cords[0] + " " + cords[1]);
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
			catch( ArrayIndexOutOfBoundsException e ) {
				
			}
		}
		return breaths.size();
	}
	
	public boolean tryProtect( int[] cords ) {
		
		int[] possibleMove = getStone( cords ).getStoneChain().getBreathArray().get(0);
		int breaths = checkNumberOfBreaths(possibleMove);
		
		if( breaths > 2 ) board.makeMove(possibleMove[0], possibleMove[1]);
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
					Object[] tableOb = listtosplit.toArray();
					CompositeStone[] table = new CompositeStone[ tableOb.length ];
					for( int i=0; i< tableOb.length; i++ ) {
						table[i] = (CompositeStone) tableOb[i];
					}
					
					splittedList.add(  table );
					listtosplit.clear();
				}
			}
			listtosplit.add(chain);
		}
		if( listtosplit.size() > 0 ) {
			Object[] tableOb = listtosplit.toArray();
			CompositeStone[] table = new CompositeStone[ tableOb.length ];
			for( int i=0; i< tableOb.length; i++ ) {
				table[i] = (CompositeStone) tableOb[i];
			}
			splittedList.add( table );
		}
		
		return splittedList;
	}
	
	public List<int[][]> splitByMostBreaths( List< int[] > list ){
		List< int[][] > splittedList = new ArrayList< int[][] >();
		List< int[] > listtosplit = new ArrayList< int[] >();
		
		int breaths = 0;
		
		for( int[] move: list ) {
			if( breaths != checkNumberOfBreaths( move ) ) {
				breaths = checkNumberOfBreaths( move );
				if( listtosplit.size() > 0 ) {
					Object[] tableOb = listtosplit.toArray();
					int[][] table = new int[ tableOb.length ][];
					for( int i=0; i< tableOb.length; i++ ) {
						table[i] = (int[]) tableOb[i];
					}
					splittedList.add( table );
					listtosplit.clear();
				}
			}
			listtosplit.add(move);
		}
		if( listtosplit.size() > 0 ) {
			Object[] tableOb = listtosplit.toArray();
			int[][] table = new int[ tableOb.length ][];
			for( int i=0; i< tableOb.length; i++ ) {
				table[i] = (int[]) tableOb[i];
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
	
	public void pass() {
		board.pass();
		board.setMessage("pass");
	}
	
	public void attack() {
		List< int[][] > possibleMoves = new ArrayList<int[][]>();
		List< CompositeStone > enemyStones = new ArrayList<CompositeStone>();
		
		if( this.enemyStones.size() == 0 ) {
			pass();
			return;
		}
		
		System.out.println("this.enemyStones.size: " + this.enemyStones.size());
		
		for( int[] eCords: this.enemyStones ) {
			boolean isIn = false;
			for( CompositeStone stoneChain: enemyStones ) {
				if( isIn = stoneChain.equals( getStone( eCords ).getStoneChain() ) ) break;
			}
			if( !isIn ) enemyStones.add( getStone( eCords ).getStoneChain() );
		}
		
		System.out.println("enemyStones.size: "+enemyStones.size());
		
		SortByBreaths sort = new SortByBreaths();
		enemyStones.sort(sort);
		
		System.out.println("enemyStones.size: "+enemyStones.size());
		
		List< CompositeStone[] > weakChains = splitByLeastBreaths( enemyStones );
		
		System.out.println("weakChains.size: "+weakChains.size());
		
		for( CompositeStone[] chains: weakChains ) {
			possibleMoves.addAll( getMovesByBreaths(chains)  );
		}
		
		System.out.println("bot possible moves size: "+possibleMoves.size());
		for( int[][] moves: possibleMoves ) {
			List< int[] > moveList = Arrays.asList( moves );
			boolean finished = false;
			Random RNG = new Random();
			
			System.out.println("bot move size: " + moveList.size());
			while( moveList.size() > 0 ) {
				int n = RNG.nextInt( moveList.size() );
				System.out.println(" bot possible moves: " + moves[n][0] + " " + moves[n][1] );
				if( finished = board.checkCorrectness( moves[n][0] , moves[n][1]  ) ) {
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
			
			if( finished ) return;
		}
		pass();
		return;
	}
	
	public void makeMove() {
		int[] endangeredStone = getEndangeredStones();
		
		if( endangeredStone != null ) {
			boolean moved = tryProtect( endangeredStone );
			if( !moved ) endangeredStone = null;
		}
		if( endangeredStone == null ) {
			System.out.println("bot attack");
			attack();
		}
		
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
