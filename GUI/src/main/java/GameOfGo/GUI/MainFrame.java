package GameOfGo.GUI;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	GUI gui;
	//private FlowLayout layout;
	//GridBagConstraints gbc = new GridBagConstraints();
	private int size = 0;
	public MainFrame(GUI gui) {
		super("Hello World");
		this.gui = gui;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		/*setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 1;
		//gc.weighty = 1;
		//pierwszy panel
		//gc.anchor = GridBagConstraints.WEST;
		//gc.weightx = 1.5;
		//gc.weighty = 1.5;
		
		
		MyPanel panel = new MyPanel(GUI.this.sizeofboard);
		panel.setPreferredSize(new Dimension(this.size, this.size));
		add(panel, gc);
		*/
		//drugi panel
		//gc.weightx = 1;
		//gc.weighty = 1.5;
		
		//gc.gridx = 1;
		//gc.gridy = 0;
		
		//add(new SecondPanel(GUI.this.sizeofboard), gc);
	}
}

