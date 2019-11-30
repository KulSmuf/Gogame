package GameOfGo.GUI;

import java.awt.Dimension;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.*;

public class SetSizeFrame extends JFrame
{
	public SetSizeFrame() {
		super("Hello World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500, 300));
		setResizable(false);
		//pack();
		//setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	
	
}