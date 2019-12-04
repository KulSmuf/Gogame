package GameOfGo.GUI;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingFrame extends JFrame{
	
	WaitingFrame(){
		//super();
		setPreferredSize(new Dimension(400,400));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton zrezygnuj = new JButton("Zrezygnuj");
		JLabel oczekiwanie = new JLabel("Oczekiwanie na serwer");
		JPanel panel = new JPanel();
		
		panel.add(zrezygnuj);
		panel.add(oczekiwanie);
		
		this.getContentPane().add(panel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}