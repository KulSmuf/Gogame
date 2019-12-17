package GameOfGo.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SecondPanel extends JPanel implements ActionListener{
	JButton pass;
	JButton surr;
	JLabel kogotura2;
	JLabel kogotura;
	JLabel number_of_host;
	
	int number_hosts=0;
	
	GUI gui;
	
	int width;
	int height;
	public SecondPanel(int size, GUI gui) {
		this.gui = gui;
		//setBackground(Color.black);
		/*switch(size) {
		case 9:
			setMinimumSize(new Dimension(250, 300));
			width = 150;
			height = 400;
			break;
		case 13:
			setPreferredSize(new Dimension(290, 560));
			width = 290;
			height = 560;
			break;
		case 19:
			setPreferredSize(new Dimension(450, 800));
			width = 450;
			height = 800;
		}*/
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		kogotura = new JLabel("Tura przeciwnika");
		//kogotura.setPreferredSize(new Dimension(width, (int)0.25*height));
		
		kogotura2 = new JLabel("Twoja tura");
		//kogotura2.setPreferredSize(new Dimension(width, (int)0.25*height));
		
		JLabel jency = new JLabel("Ilosc jencow");
		//jency.setPreferredSize(new Dimension(width, (int)0.25*height));
		
		number_of_host = new JLabel("0");
		
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(kogotura, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(kogotura2, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(jency, c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(number_of_host, c);
		
		pass = new JButton("Spasuj");
		pass.addActionListener(this);
		
		surr = new JButton("Poddaj się");
		surr.addActionListener(this);
		
		c.gridx = 0;
		c.gridy = 3;
		add(pass, c);
		
		c.gridx = 1;
		c.gridy = 3;
		add(surr, c);
		
		
		//kogo tura
		//ilosc jencow
		//przycisk pass
		//przycisk poddaj sie
	}
	
	
	public void actionPerformed(ActionEvent e) {
		//sprawdzaj ktory gracz gra, dodawaj do dwoch roznych list
		Object source = e.getSource();
		if(source == pass && gui.isActive()==true) {
			//wyslij klientem
			//if active czyli czy twoja tura
			gui.player.sendCommand("pass");
			gui.setActive(false);
			gui.getSPanel().turaPrzeciwnika();
		}
		else if(source == surr) {
			//wyslij klientem
			gui.player.sendCommand("exit");
			//cos serwer musi zwracac zeby potwierdzic
			//potem zamknac cala apke
			//init exit window
			gui.initExitWindow("surr");
		}
		
		
	}
	//zmienia jlabel
	public void turaPrzeciwnika() {
		kogotura.setVisible(true);
		kogotura2.setVisible(false);
	}
	
	public void turaGracza() {
		kogotura.setVisible(false);
		kogotura2.setVisible(true);
	}
	
	public void update_host(int new_hosts) {
		number_hosts += new_hosts;
		number_of_host.setText(Integer.toString(number_hosts));
	}
	
	
}
