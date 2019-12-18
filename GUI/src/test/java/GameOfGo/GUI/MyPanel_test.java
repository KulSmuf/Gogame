package GameOfGo.GUI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


	
	@RunWith(MockitoJUnitRunner.class)
	public class MyPanel_test {
	 
	    @Mock
	    private GUI gui;
	    @Test
	    public void test_ExitWindow() {
	        gui.initExitWindow("exit");
	        verify(gui).initExitWindow("exit");
	    }
	 
	    @Test
	    public void test_FirstFrame() {
	    	gui.initFirstFrame();
	        verify(gui).initFirstFrame();
	    }
	    
	    @Test
	    public void test_makeMove() {
	    	gui.setActive(true);
	        verify(gui).setActive(true);
	    }
	    
	    @Test
	    public void test_makeMove1() {
	    	gui.makeMove("pass");
	        verify(gui).makeMove("pass");
	    }
	}


