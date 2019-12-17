package GameOfGo.GUI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import javax.swing.JButton;

import org.junit.Test;


public class Buttons {
	GUI gui = mock(GUI.class);
	MyPanel panel;
	@Test
	public void test() {
		boolean ponad_x = false;
		boolean ponad_y = false;
		panel = new MyPanel(9,gui);
		for(JButton p:panel.stones) {
			if(p.getBounds().getX()>9*40) {
				ponad_x = true;
			}
			if(p.getBounds().getY()>9*40) {
				ponad_y = true;
			}
		}
		assertTrue(ponad_y==false && ponad_x==false);
		
		panel = new MyPanel(13,gui);
		for(JButton p:panel.stones) {
			if(p.getBounds().getX()>13*40) {
				ponad_x = true;
			}
			if(p.getBounds().getY()>13*40) {
				ponad_y = true;
			}
		}
		assertTrue(ponad_y==false && ponad_x==false);
		
		panel = new MyPanel(19, gui);
		for(JButton p:panel.stones) {
			if(p.getBounds().getX()>19*40) {
				ponad_x = true;
			}
			if(p.getBounds().getY()>19*40) {
				ponad_y = true;
			}
		}
		assertTrue(ponad_y==false && ponad_x==false);
	}

}

