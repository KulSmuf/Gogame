package GameOfGo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FirstPanel extends JPanel implements ActionListener{
	
	GUI gui;
	
	private JFrame okno;
	JButton size9;
	JButton size13;
	JButton size19;
	JButton gracz;
	JButton bot;
	JButton zagraj;
	
	
	public FirstPanel(GUI gui) {
		this.gui = gui;
		//this.okno = frame;
		setPreferredSize(new Dimension(500, 300));
		this.setLayout(new GridLayout(3,2));
		
		size9 = new JButton("9x9");
		size9.addActionListener(this);
		size9.setFont(new Font("Arial", Font.PLAIN, 40));
		
		size13 = new JButton("13x13");
		size13.addActionListener(this);
		size13.setFont(new Font("Arial", Font.PLAIN, 40));
		
		size19 = new JButton("19x19");
		size19.addActionListener(this);
		size19.setFont(new Font("Arial", Font.PLAIN, 40));
		
		//z kim gra
		bot = new JButton("Zagraj z botem");
		bot.addActionListener(this);
		bot.setFont(new Font("Arial", Font.PLAIN, 20));
		
		gracz = new JButton("Zagraj z graczem");
		gracz.addActionListener(this);
		gracz.setFont(new Font("Arial", Font.PLAIN, 20));
		
		zagraj = new JButton("Graj");
		zagraj.addActionListener(this);
		zagraj.setFont(new Font("Arial", Font.PLAIN, 40));
		
		this.add(size9);
		this.add(gracz);
		this.add(size13);
		this.add(bot);
		this.add(size19);
		this.add(zagraj);
	}
	

	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		//default color
		Color myWhite = new Color(255, 255, 255); // Color white
		//kiedy wcisniety
		Color notWhite = new Color(0, 0, 0); // Color non-white
		//jeszcze if do zagraj
		//TODO metoda robiaca plansze input int?
		
		
		if(source == gracz) {
			gracz.setEnabled(false);
			gracz.setBackground(myWhite);
			bot.setEnabled(true);
			bot.setBackground(null);
		}
		else if(source == bot) {
			gracz.setEnabled(true);
			gracz.setBackground(null);
			bot.setEnabled(false);
			bot.setBackground(myWhite);
			
		}
		else if(source == size9) {
			
			size13.setEnabled(true);
			size13.setBackground(null);
			
			size19.setEnabled(true);
			size19.setBackground(null);
			
			size9.setEnabled(false);
			size9.setBackground(myWhite);
		}
		else if(source == size13) {
			
			size9.setEnabled(true);
			size9.setBackground(null);
			
			size19.setEnabled(true);
			size19.setBackground(null);
			
			size13.setEnabled(false);
			size13.setBackground(myWhite);
		}
		else if(source == size19) {
			
			size9.setEnabled(true);
			size9.setBackground(null);
			
			size13.setEnabled(true);
			size13.setBackground(null);
			
			size19.setEnabled(false);
			size19.setBackground(myWhite);
		}
		else if(source == zagraj) {
			//czy zainicjowales size i player
			int s = 0;
			int p = 0;
			
			if(size9.isEnabled() == false) {
				 gui.setSizeoftheboard(9);
				 s++;
			}
			if(size13.isEnabled() == false) {
				gui.setSizeoftheboard(13);
				s++;
			}
			if(size19.isEnabled() == false) {
				gui.setSizeoftheboard(19);
				s++;
			}
			if(gracz.isEnabled() == false) {
				gui.setBot(false);
				p++;
			}
			if(bot.isEnabled() == false) {
				gui.setBot(true);
				p++;
			}
			
			if(s!=0 && p!=0) {
			//wyslij start size w/b
			//jakis concat stringow
			String rozmiar = Integer.toString(gui.Getsizeoftheboard());
			String zkimgram;
			if(gui.getBot() == true) {
				zkimgram = "b";
			}
			else {
				zkimgram = "p";
			}
			gui.getClient();
			gui.player.sendCommand(rozmiar+" "+zkimgram);
			
			/*
			JFrame okno = new JFrame();
			JPanel panel = new JPanel(new BorderLayout());
			panel.setPreferredSize(new Dimension(400,400));
			JLabel oczekiwanie = new JLabel("Oczekiwanie na serwer");
			oczekiwanie.setSize(new Dimension(50,50));
			panel.add(oczekiwanie, BorderLayout.NORTH);
			
			JButton zrezygnuj = new JButton("Zrezygnuj");
			zrezygnuj.setSize(new Dimension(50,50));
			panel.add(zrezygnuj, BorderLayout.SOUTH);
			
			zrezygnuj.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                gui.gracz.sendCommand("quit");
	                //wyjscie z aplikacji
	                gui.getWframe().dispose();
	                System.exit(0);
	            }
	        });
			
			
			okno.add(panel);
			okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//okno.setLocationRelativeTo(null);
			okno.pack();
			okno.setVisible(true);
			//okno.setVisible(true);
			//gui.setWFrame(okno);
			//okno.add(new WaitingPanel());
			//okno.setSize(new Dimension(300,300));
			//okno.pack();
			//okno.setLocationRelativeTo(null);
			//okno.setVisible(true);
			 *
			*/
			gui.getFrame().setTitle("Oczekiwanie na serwer...");
			//zaleznie od tego co przyjdzie, czy mozesz przyjsc cos innego niz tak?
			try {
				while(gui.player.hasServerSendCommand() == false) {
					//
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				if(gui.player.getServerCommand().equals("B")) {
					gui.setWhichplayer(true);
					gui.setActive(false);
					//odpal Timera
					Timer timer = new Timer();
					Date date = new Date();
					TimerTask task = new coms_to_gui(this.gui); 
					timer.scheduleAtFixedRate(task,date,2000);
					gui.setTimer(timer);
				}
				else {
					gui.setWhichplayer(false);
					gui.setActive(true);
				}
				//okno.setVisible(false);
				gui.initMainFrame();
			}
			
			
		}
}

}	