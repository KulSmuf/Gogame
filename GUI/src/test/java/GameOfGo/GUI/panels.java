package GameOfGo.GUI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class panels {
	
	//tworzymy mocka
	GUI gui = mock(GUI.class);
	
	MyPanel panel =  new MyPanel(9,gui);
	SecondPanel pan = new SecondPanel(9,gui);
	 
	
	@Test(expected=IllegalArgumentException.class)	
	public void test_except2() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		panel.addstone("53,53");
	}
	
	@Test(expected=IllegalArgumentException.class)	
	public void test_except1() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		panel.addstone("");
	}
	
	@Test
	public void test_butt() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		panel.addstone("7,7 0");
		assertTrue(panel.opponent_stones.size()>0);
	}
	
	@Test
	public void test_visibility() {
		//when(gui.Getsizeoftheboard()).thenReturn(9);
		//panel.addstone("7,7 0");
		pan.turaPrzeciwnika();
		assertTrue(pan.kogotura.isVisible());
		assertTrue(pan.kogotura2.isVisible());
	}


}
