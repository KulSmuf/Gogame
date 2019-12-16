package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StoneTest {
	@Test
	public void testStoneMerge() {
		List<int[]> breaths = new ArrayList<int[]>();
		int[] cords = {0,1};
		breaths.add(cords);
		cords = new int[] {1,0};
		breaths.add(cords);
		
		Stone stone1 = new Stone( 0,0, breaths );
		
		breaths = new ArrayList<int[]>();
		cords = new int[] {0,1};
		breaths.add(cords);
		cords = new int[] {1,2};
		breaths.add(cords);
		cords = new int[] {0,3};
		breaths.add(cords);
		
		Stone stone2 = new Stone( 0,2, breaths );
		
		breaths = new ArrayList<int[]>();
		cords = new int[] {1,1};
		breaths.add(cords);
		cords = new int[] {2,0};
		breaths.add(cords);
		
		Stone stone3 = new Stone( 1,0, breaths, stone1.getStoneChain() );
		
		assertEquals( stone1.getStoneChain(),stone3.getStoneChain() );
		
		stone1.getStoneChain().merge(stone2.getStoneChain());
		
		breaths = new ArrayList<int[]>();
		cords = new int[] {1,1};
		breaths.add(cords);
		
		Stone stone4 = new Stone( 0,1, breaths, stone1.getStoneChain() );
		
		assertEquals( stone4.getStoneChain().getChainLength(), 4 );
		assertEquals( stone4.getStoneChain().getBreaths(), 4 );
		
		stone4.getStoneChain().reduceBreath(new int[] {1,1});
		
		assertEquals( stone4.getStoneChain().getBreaths(), 3 );
	}
}
