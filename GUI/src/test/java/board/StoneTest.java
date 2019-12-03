package board;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoneTest {
	@Test
	public void StoneMergeTest() {
		Stone stone1 = new Stone( 0,0,2 );
		Stone stone2 = new Stone( 0,2,3 );
		Stone stone3 = new Stone( 1,0,2, stone1.getStoneChain() );
		
		assertEquals( stone1.getStoneChain(),stone3.getStoneChain() );
		
		stone1.getStoneChain().merge(stone2.getStoneChain());
		Stone stone4 = new Stone( 0,1,1, stone1.getStoneChain() );
		
		assertEquals( stone4.getStoneChain().getChainLength(), 4 );
	}
}
