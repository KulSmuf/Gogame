package GameOfGo.GUI;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class Exceptions {
	
	GUI gui = mock(GUI.class);
	MyPanel p;
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
	
	
	
}
