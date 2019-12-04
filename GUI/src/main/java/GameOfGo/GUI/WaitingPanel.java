package GameOfGo.GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WaitingPanel extends JPanel{
	GUI gui;
	
	JButton zrezygnuj;
	JLabel oczekiwanie;
	public WaitingPanel(final GUI gui) {
		this.gui = gui;
		setLayout(new GridLayout(2,1));
		
		oczekiwanie = new JLabel("Oczekiwanie na serwer");
		add(oczekiwanie);
		
		zrezygnuj = new JButton("Zrezygnuj");
		add(zrezygnuj);
		zrezygnuj.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                gui.gracz.sendCommand("quit");
	                //wyjscie z aplikacji
	                gui.getWframe().dispose();
	                System.exit(0);
	            }
	        });
	}
	
}