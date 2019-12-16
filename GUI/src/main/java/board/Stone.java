package board;

import java.util.List;

public class Stone {
	private int r;
	private int c;
	private CompositeStone stoneChain;
	
	public Stone(int r, int c, List<int[]> breaths, CompositeStone stoneChain) {
		this.r = r;
		this.c = c;
		this.stoneChain = stoneChain;
		stoneChain.add(this);
		stoneChain.addBreaths(breaths);
	}
	
	public Stone(int r, int c, List<int[]> breaths) {
		this.r = r;
		this.c = c;
		this.stoneChain = new CompositeStone(this);
		stoneChain.addBreaths(breaths);
	}
	
	public CompositeStone getStoneChain() {
		return stoneChain;
	}
	
	public int getBreaths() {
		return stoneChain.getBreaths();
	}
	
	public void setStoneChain( CompositeStone stoneChain ) {
		this.stoneChain = stoneChain;
	}
	
	public int getRow() {
		return r;
	}
	
	public int getColumn() {
		return c;
	}
	
	@Override
	public String toString() {
		return ( Integer.toString(r) + "," + Integer.toString(c) );
	}
}
