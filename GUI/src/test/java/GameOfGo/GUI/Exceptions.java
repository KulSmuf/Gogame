package GameOfGo.GUI;

import GameOfGo.GUI.GUI.MyPanel;
import org.junit.Test;

public class Exceptions {
	GUI testowe;
	static GUI.MyPanel p;
	//sprawdza czy mozna nic nie przekazac
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIllegalArgumentExceptionOnWrongParameters() {
		GUI testowe = new GUI();
		testowe.setSizeoftheboard(9);
		p = new GUI().new MyPanel(9);
		testowe.setMPanel(p);
	    p.addkamien(" ");
	}
	//sprawdza czy mozna wyjsc poza rozmiar planszy
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIllegalArgumentExceptionOnWrongParameters2() {
		GUI testowe = new GUI();
		testowe.setSizeoftheboard(9);
		p = new GUI().new MyPanel(9);
		testowe.setMPanel(p);
	    p.addkamien("20x20");
	}
	
	
	
}
