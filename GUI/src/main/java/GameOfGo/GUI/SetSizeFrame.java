package GameOfGo.GUI;

import java.awt.Dimension;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.*;

public class SetSizeFrame extends JFrame
{
	GUI gui;
	public SetSizeFrame(final GUI gui) {
		super("Hello World");
		this.gui = gui;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500, 300));
		setResizable(false);
		//pack();
		//setLocationRelativeTo(null);
		//setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if(gui.player != null) {
		         gui.player.sendCommand("exit");
		    	}
		        System.exit(0);
		    }
		});
	}
	
	
	
}