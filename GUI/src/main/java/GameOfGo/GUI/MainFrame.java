package GameOfGo.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	GUI gui;
	//private FlowLayout layout;
	//GridBagConstraints gbc = new GridBagConstraints();
	private int size = 0;
	
	public int getSiza() {
		return this.size;
	}
	public MainFrame(final GUI gui) {
		//setLayout(new BorderLayout());
		super("Hello World");
		this.gui = gui;
		switch(gui.Getsizeoftheboard()) {
		case 9:
			setPreferredSize(new Dimension(800, 440));
			this.size = 400;
			break;
		case 13:
			setPreferredSize(new Dimension(1120, 600));
			this.size = 560;
			break;
		case 19:
			setPreferredSize(new Dimension(1600, 840));
			this.size = 800;
			break;
		}
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        gui.player.sendCommand("exit");
		        System.exit(0);
		    }
		});
	}
}

