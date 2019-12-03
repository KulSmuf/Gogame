package board;

import java.util.ArrayList;

public class CompositeStone {
	ArrayList<Stone> stoneChain;
	
	CompositeStone( Stone firstStone ){
		stoneChain = new ArrayList<Stone>();
		stoneChain.add(firstStone);
	}
	
	public ArrayList<Stone> getStoneChain(){
		return stoneChain;
	}
	
	public void add( Stone stone ) {
		stoneChain.add(stone);
	}
	
	public int getBreaths() {
		int sum = 0;
		for( Stone stone: stoneChain ) {
			sum += stone.getBreaths();
		}
		return sum;
	}
	
	public int getChainLength() {
		return stoneChain.size();
	}
	
	public void merge( CompositeStone stoneChain) {
		this.stoneChain.addAll( stoneChain.getStoneChain() );
		for( Stone stone: stoneChain.getStoneChain() ) {
			stone.setStoneChain(this);
		}
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
