package sandbox;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class sandbox {
	
	@Test
	public void sandboxTest() {
		char test = 0;
		int a = 0;
		char test2 = (char) a++;
		if( test == test2 ) System.out.println("1 true");
		test = 1;
		if( test == test2 ) System.out.println("2 true");
	}
}
