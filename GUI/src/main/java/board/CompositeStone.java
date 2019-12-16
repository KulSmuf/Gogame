package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeStone {
	ArrayList<Stone> stoneChain;
	ArrayList<int[]> breaths = new ArrayList<int[]>();
	
	CompositeStone( Stone firstStone ){
		stoneChain = new ArrayList<Stone>();
		stoneChain.add(firstStone);
	}
	
	public void reduceBreath( int[] cords ) {
		int[] remove = null;
		for( int[] breath: breaths ) {
			if( Arrays.equals(breath,cords) ) {
				remove = breath;
				//break;
			}
		}
		if( remove != null ) breaths.remove(remove);
	}
	
	public void gainBreath( int[] cords ) {
		boolean isIn = false;
		for( int[] breath: breaths ) {
			if( isIn = Arrays.equals(breath,cords) ) break;
		}
		if( !isIn ) breaths.add(cords);
	}
	
	public void addBreaths( List<int[]> newBreaths ) {
		for( int[] cords: newBreaths ) {
			gainBreath(cords);
		}
	}
	
	public ArrayList<Stone> getStoneChain(){
		return stoneChain;
	}
	
	public void add( Stone stone ) {
		stoneChain.add(stone);
		reduceBreath(new int[] { stone.getRow() , stone.getColumn() });
	}
	
	public int getBreaths() {
		return breaths.size();
	}
	
	public int getChainLength() {
		return stoneChain.size();
	}
	
	public void merge( CompositeStone stoneChain ) {
		this.stoneChain.addAll( stoneChain.getStoneChain() );
		for( Stone stone: stoneChain.getStoneChain() ) {
			stone.setStoneChain(this);
		}
		
		this.addBreaths( stoneChain.getBreathArray() );
		
	}
	
	public List<int[]> getBreathArray(){
		return this.breaths;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for( Stone stone: stoneChain ) {
			if( ret.length() > 0 ) ret+=" ";
			ret += stone.toString();
		}
		return ret;
	}
}
