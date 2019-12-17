package GameOfGo.GUI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class Maintest {

	GUI gui = mock(GUI.class);	
	@Test
	public void test() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		MainFrame frame = new MainFrame(gui);
		assertTrue(frame.getSiza()==400);
		
		when(gui.Getsizeoftheboard()).thenReturn(13);
		MainFrame frame1 = new MainFrame(gui);
		assertTrue(frame1.getSiza()==560);
		
		when(gui.Getsizeoftheboard()).thenReturn(19);
		MainFrame frame2 = new MainFrame(gui);
		assertTrue(frame2.getSiza()==800);
	}
}