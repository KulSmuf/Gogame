package GameOfGo.GUI;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.Test;

public class Exceptions {
	
	GUI gui = mock(GUI.class);
	MyPanel p;
	SecondPanel panel;
	//sprawdza czy mozna nic nie przekazac
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIllegalArgumentExceptionOnWrongParameters() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		p = new MyPanel(9,gui);
	    p.addstone("");
	}
	//sprawdza czy mozna wyjsc poza rozmiar planszy
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIllegalArgumentExceptionOnWrongParameters2() {
		when(gui.Getsizeoftheboard()).thenReturn(9);
		p = new MyPanel(9,gui);
	    p.addstone("20,20");
	}
	
	@Test
	public void test_gui1() {
	       assertNull(gui.getTimer());
	}
	
	@Test
	public void test_actionP() {
		panel = new SecondPanel(9,gui);
		when(gui.isActive()).thenReturn(true);
		panel.actionPerformed(new ActionEvent(panel,0,"pass"));
		
		
	}
	
	
	
	
	
}
