package board;

public class Stone {
	int r;
	int c;
	int breaths;
	CompositeStone stoneChain;
	
	public Stone(int r, int c, int breaths, CompositeStone stoneChain) {
		this.r = r;
		this.c = c;
		this.breaths = breaths;
		this.stoneChain = stoneChain;
		stoneChain.add(this);
	}
	
	public Stone(int r, int c, int breaths) {
		this.r = r;
		this.c = c;
		this.breaths = breaths;
		this.stoneChain = new CompositeStone(this);
	}
	
	public void reduceBreath() {
		breaths--;
	}

	public CompositeStone getStoneChain() {
		return stoneChain;
	}
	
	public int getBreaths() {
		return breaths;
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
