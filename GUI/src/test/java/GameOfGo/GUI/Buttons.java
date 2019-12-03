package GameOfGo.GUI;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Test;

import GameOfGo.GUI.GUI.MyPanel;

public class Buttons {
	MyPanel panel;
	@Test
	public void test() {
		boolean ponad_x = false;
		boolean ponad_y = false;
		panel = new GUI().new MyPanel(9);
		for(JButton p:panel.przyciski) {
			if(p.getBounds().getX()>9*40) {
				ponad_x = true;
			}
			if(p.getBounds().getY()>9*40) {
				ponad_y = true;
			}
		}
		assertTrue(ponad_y==false && ponad_x==false);
		
		panel = new GUI().new MyPanel(13);
		for(JButton p:panel.przyciski) {
			if(p.getBounds().getX()>13*40) {
				ponad_x = true;
			}
			if(p.getBounds().getY()>13*40) {
				ponad_y = true;
			}
		}
		assertTrue(ponad_y==false && ponad_x==false);
		
		panel = new GUI().new MyPanel(19);
		for(JButton p:panel.przyciski) {
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
