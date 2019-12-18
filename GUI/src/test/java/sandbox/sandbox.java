package sandbox;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class sandbox {
	
	@Test
	public void sandboxTest() {
		
		int[] test = {1,2};
		test[-1] =1;
		
		List<int[]> board = new ArrayList<int[]>();
		
		int[] add= {1,2};
		add = null;
		add = new int[]{1,2};
		board.add( add );
		int[] add2= {1,2};
		boolean contain = false;
		for( int[] cords : board ) {
			if( contain = Arrays.equals(cords,add2) ) break;
		}
		System.out.println(contain);
	}
}
