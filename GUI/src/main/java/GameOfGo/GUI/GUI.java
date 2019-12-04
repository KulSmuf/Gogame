package GameOfGo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import client.Client;

public class GUI {
	Timer timer;
    TimerTask task = new coms_to_gui(); 
    Date date = new Date();
	
	class coms_to_gui extends TimerTask
	{
		String odp;
		public void run() {
			try {
				if(gracz.hasServerSendCommand()) {
					odp = gracz.getServerCommand();
					zrobRuch(odp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block-
				e.printStackTrace();
			}
			//if na wiadomosc przy poddaniu i wygraniu i przegraniu
			//zrobRuch(odp);
			
		}
		
	}
	
	
	public Timer getTimer() {
		if(this.timer != null) {
		 return this.timer;
		}
		else {
			Timer tim = new Timer();
			setTimer(tim);
			return tim;
		}
	}
	
	public void setTimer(Timer tim) {
		this.timer = tim;
	}
	
	//to trzeba zmienic na true przy rozpoczeciu gry
	private boolean active;
	//instancja klienta
	public Client gracz;
	
	void getClient() {
		try {
			gracz = new Client();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	//firstframe
	private JFrame pierwsza;
	//waitingframe
	private JFrame waiting;
	//mypanel
	private MyPanel panel;
	//secondpanel
	private SecondPanel pi;
	//9,13,19
	private int sizeofboard;
	//false to czarny, true to bialy
	private boolean whichplayer;
	//czy bot
	private boolean bot;
	
	public void setMPanel(MyPanel p) {
		this.panel = p;
	}
	
	public MyPanel getMPanel() {
		return this.panel;
	}
	
	public void setWFrame(JFrame w) {
		this.waiting = w;
	}
	
	public JFrame getWframe() {
		return this.waiting;
	}
	
	public void setBot(boolean i) {
		this.bot = i;
	}
	
	public boolean getBot() {
		return this.bot;
	}
	
	public void setActive(boolean i) {
		this.active = i;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setFrame(JFrame frame) {
		this.pierwsza = frame;
	}
	
	public SecondPanel getSPanel() {
		return this.pi;
	}
	
	public void setSPanel(SecondPanel panel) {
		this.pi = panel;
	}
	
	public JFrame getFrame() {
		return this.pierwsza;
	}
	
	public int Getsizeoftheboard() {
		return this.sizeofboard;
	}

	public void setSizeoftheboard(int size) {
		this.sizeofboard = size;
	}
	
	public boolean isWhichplayer() {
		return this.whichplayer;
	}




	public void setWhichplayer(boolean player) {
		this.whichplayer = player;
	}
	
	
	//przyciski
	private boolean wgracz = false;
	private boolean wbot = false;
	private boolean w9 = false;
	private boolean w13 = false;
	private boolean w19 = false;


	public boolean isWgracz() {
		return wgracz;
	}




	public boolean isWbot() {
		return wbot;
	}




	public boolean isW9() {
		return w9;
	}




	public boolean isW13() {
		return w13;
	}




	public boolean isW19() {
		return w19;
	}




	public void setWgracz(boolean wgracz) {
		this.wgracz = wgracz;
	}




	public void setWbot(boolean wbot) {
		this.wbot = wbot;
	}




	public void setW9(boolean w9) {
		this.w9 = w9;
	}




	public void setW13(boolean w13) {
		this.w13 = w13;
	}




	public void setW19(boolean w19) {
		this.w19 = w19;
	}
	
	//initfirstframe
	private static int fwidth;
	private static int fheight;
	public void initFirstFrame() {
		//init okna wyboru
		JFrame one = new SetSizeFrame();
		fwidth = one.getWidth();
		fheight = one.getHeight();
		one.setLayout(new GridLayout());
		one.add(new FirstPanel());
		one.pack();
		one.setLocationRelativeTo(null);
		one.setVisible(true);
		GUI.this.setFrame(one);
	}
	
		//metoda GUI po zrobieniu ruchu przez przeciwnika
		//przekazuje string z lokacja kamienia przeciwnika
		public void zrobRuch(String namiary) {
			this.panel.addkamien(namiary);
			getSPanel().turaGracza();
			setActive(true);
			
		}
	
	public class SecondPanel extends JPanel implements ActionListener{
		JButton pass;
		JButton surr;
		JLabel kogotura2;
		JLabel kogotura;
		int width;
		int height;
		public SecondPanel(int size) {
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
			
			pass = new JButton("Spasuj");
			//pass.setPreferredSize(new Dimension((int)0.5*width, (int)0.25*height));
			
			surr = new JButton("Poddaj się");
			//surr.setPreferredSize(new Dimension((int)0.5*width, (int)0.25*height));
			
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
			if(source == pass) {
				//wyslij klientem
				//if active czyli czy twoja tura
				GUI.this.gracz.sendCommand("pass");
				active=false;
				GUI.this.getSPanel().turaPrzeciwnika();
			}
			else if(source == surr) {
				//wyslij klientem
				GUI.this.gracz.sendCommand("surr");
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
		
		
	}
	
	
	
	
	
	public class MyPanel extends JPanel implements ActionListener{
		private int dim = 0;
		private int height = 0;
		private int width = 0;
		private ArrayList<Line2D> linie = new ArrayList<Line2D>();
		//tablica przyciskow
		ArrayList<JButton> przyciski = new ArrayList<JButton>();
		//kamienie
		private ArrayList<JButton> kamienie = new ArrayList<JButton>();
		//kamienie przeciwnika
		private ArrayList<JButton> kamieniep = new ArrayList<JButton>();
		
		private Color background = new Color(249, 235, 106);
		
		//bedzie czytal string od serwera, sam go przetwarzal i dodawal w tym miejscu kamien przeciwnika
		public void addkamien(String pozycja) {
			//parse string
			//nie uwzgledniamy tych co przeciwnik zbil
			String[] tokenss = pozycja.split(" ");
			String[] tokens = tokenss[0].split(",");
			if(tokens == null) {
				throw new IllegalArgumentException("zły format");
			}
			int x = Integer.parseInt(tokens[0]);
			int y = Integer.parseInt(tokens[1]);
			if(x>GUI.this.sizeofboard || y>GUI.this.sizeofboard || x<0 || y<0) {
				throw new IllegalArgumentException("złe dane");
			}
			
			//znalezc odpowiedni przycisk
			 for(JButton b:przyciski) {
				if(x == b.getBounds().x/40-1 && y == b.getBounds().y/40-1) {
					b.setEnabled(false);
					b.setVisible(false);
					kamieniep.add(b);
					repaint();
				}
			 
			}
			if(tokenss[1] != "0") {
				int ile = Integer.parseInt(tokenss[1]);
				for(int u=0;u<ile;u++) {
					String[] namiar = tokenss[2+u].split(",");
					int s = Integer.parseInt(namiar[0]);
					int r = Integer.parseInt(namiar[1]);
					for(JButton b:przyciski) {
						if(s == b.getBounds().x/40-1 && r == b.getBounds().y/40-1) {
							//usunac moje kamyki i zwolnic miejsce, wlaczyc przyciski
							b.setEnabled(true);
							b.setVisible(true);
							kamienie.remove(b);
							repaint();
						}
					 }
				}
			}
		}
		
		public void actionPerformed(ActionEvent f) {
			//sprawdza czy nie tura przeciwnika
			if(isActive()) {
			Object source = f.getSource();
			//wysyla serwerowi ruch gracza w postaci XY zaczynajac od 1x1
			String str = Integer.toString((int)((AbstractButton)source).getBounds().getX()/40-1);
			String str2 = Integer.toString((int)((AbstractButton)source).getBounds().getY()/40-1);
		
				getTimer().cancel();
			
			GUI.this.gracz.sendCommand(str+" "+str2);
			//if SendCommand=1 przeprowadz ruch, jesli nie to nic nie rob
			try {
				while(GUI.this.gracz.hasServerSendCommand() == false) {
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String response = GUI.this.gracz.getServerCommand();
			JOptionPane.showMessageDialog(getFrame(), response);
			if(response.equals("1")) {
				((AbstractButton) source).setEnabled(false);
				((AbstractButton) source).setVisible(false);
				this.kamienie.add((JButton) source);
				repaint();
				//wywoluj metode tura przeciwnika
				//czekaj na ruch przeciwnika, jesli null to znaczy ze wyszedl
				//tura Przeciwnika wiec nic nie mozesz zrobic
				active=false;
				GUI.this.getSPanel().turaPrzeciwnika();
				Timer timer = new Timer();
				TimerTask task = new coms_to_gui(); 
				timer.scheduleAtFixedRate(task, date, 2000);
				setTimer(timer);
			}
			else if(!response.equals("0") && !response.equals("1") && response.isEmpty()==false) {
				String[] tokens = response.split(" ");
				for(int u=1;u<=Integer.parseInt(tokens[0]);u++) {
					String[] cordinates = tokens[u].split(",");
					int s = Integer.parseInt(cordinates[0]);
					int r = Integer.parseInt(cordinates[1]);
					for(JButton b:przyciski) {
						if(s == b.getBounds().x/40-1 && r == b.getBounds().y/40-1) {
							//usunac kamienie przeciwnika i zwolnic miejsce, wlaczyc przyciski
							b.setEnabled(true);
							b.setVisible(true);
							kamieniep.remove(b);
							//repaint();
						}
					 }
				}
			((AbstractButton) source).setEnabled(false);
			((AbstractButton) source).setVisible(false);
			this.kamienie.add((JButton) source);
			repaint();
			//wywoluj metode tura przeciwnika
			//czekaj na ruch przeciwnika, jesli null to znaczy ze wyszedl
			//tura Przeciwnika wiec nic nie mozesz zrobic
			active=false;
			GUI.this.getSPanel().turaPrzeciwnika();
			Timer timer = new Timer();
			TimerTask task = new coms_to_gui(); 
			timer.scheduleAtFixedRate(task, date, 2000);
			setTimer(timer);
			}
			}
		}
		
		
		public MyPanel(int size) {
			this.setLayout(null);
			this.setBackground(background);
			this.dim = size;
			switch(this.dim) {
				case 9:
					this.setPreferredSize(new Dimension(400, 400));
					break;
				case 13:
					this.setPreferredSize(new Dimension(560, 560));
					break;
				case 19:
					this.setPreferredSize(new Dimension(800, 800));
			}
			
			
			for(int r=1;r<=dim;r++) {
				for(int k=1;k<=dim;k++) {
					JButton ada = new JButton();
					ada.setBounds(r*40, k*40, 5, 5);
					ada.setBackground(background);
					ada.addActionListener(this);
					przyciski.add(ada);
					this.add(ada);
				}
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for(int i=1;i<=dim;i++) {
				Line2D newline = new Line2D.Double(40, i*40, dim*40, i*40);
				linie.add(newline);
				g2d.draw(newline);
				Line2D newline2 = new Line2D.Double(i*40, 40, i*40, dim*40);
				linie.add(newline2);
				g2d.draw(newline2);
			}
			//skalowanie ikon kamieni
			ImageIcon iconb = new ImageIcon("black.png"); // load the image to a imageIcon
			Image imageb = iconb.getImage(); // transform it 
			Image newimgb = imageb.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iconb = new ImageIcon(newimgb);
			ImageIcon iconw = new ImageIcon("white.png"); // load the image to a imageIcon
			Image imagew = iconw.getImage(); // transform it 
			Image newimgw = imagew.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			iconw = new ImageIcon(newimgw);
			//
			for(JButton s:kamienie) {
				int x = s.getLocation().x - 15;
				int y = s.getLocation().y - 15;
				if(isWhichplayer()==true) {
				    iconw.paintIcon(this, g, x, y);
				}
				else {
					iconb.paintIcon(this, g, x, y);
				}
			}
				
			for(JButton p:kamieniep) {
				int xp = p.getLocation().x - 15;
				int yp = p.getLocation().y - 15;
				if(isWhichplayer()==true) {
				    iconb.paintIcon(this, g, xp, yp);
				}
				else {
					iconw.paintIcon(this, g, xp, yp);
				}
			}
			
			

			

			
		}
		
	}
	
	public class MainFrame extends JFrame
	{
		//private FlowLayout layout;
		//GridBagConstraints gbc = new GridBagConstraints();
		private int size = 0;
		public MainFrame() {
			super("Hello World");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			switch(GUI.this.sizeofboard) {
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

	
	//glowne okno
	public void initMainFrame() {
		GUI.this.getFrame().dispose();
		JFrame main = new MainFrame();
		
		//main.setSize(new Dimension(650,600));
		main.setLayout(new GridLayout(0,2));
		//main.setLayout(new GridBagLayout());
		MyPanel panel1 = new MyPanel(this.sizeofboard);
		main.add(panel1);
		SecondPanel panel2 = new SecondPanel(this.sizeofboard);
		main.add(panel2);
		setMPanel(panel1);
		setSPanel(panel2);
		if(isWhichplayer()==false) {
			panel2.turaGracza();
		}
		else {
			panel2.turaPrzeciwnika();
		}
		//main.setComponentOrientation(
        //       ComponentOrientation.LEFT_TO_RIGHT);
		//main.add(new JPanel());
		main.pack();
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
	}
	public class WaitingPanel extends JPanel{
		JButton zrezygnuj;
		JLabel oczekiwanie;
		public WaitingPanel() {
			setLayout(new GridLayout(2,1));
			
			oczekiwanie = new JLabel("Oczekiwanie na serwer");
			add(oczekiwanie);
			
			zrezygnuj = new JButton("Zrezygnuj");
			add(zrezygnuj);
			zrezygnuj.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                GUI.this.gracz.sendCommand("quit");
		                //wyjscie z aplikacji
		                GUI.this.getWframe().dispose();
		                System.exit(0);
		            }
		        });
		}
		
	}
	
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
	public class FirstPanel extends JPanel implements ActionListener{
		
		private JFrame okno;
		JButton size9;
		JButton size13;
		JButton size19;
		JButton gracz;
		JButton bot;
		JButton zagraj;
		
		
		public FirstPanel() {
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
					 GUI.this.setSizeoftheboard(9);
					 s++;
				}
				if(size13.isEnabled() == false) {
					GUI.this.setSizeoftheboard(13);
					s++;
				}
				if(size19.isEnabled() == false) {
					GUI.this.setSizeoftheboard(19);
					s++;
				}
				if(gracz.isEnabled() == false) {
					GUI.this.setBot(false);
					p++;
				}
				if(bot.isEnabled() == false) {
					GUI.this.setBot(true);
					p++;
				}
				
				if(s!=0 && p!=0) {
				//wyslij start size w/b
				//jakis concat stringow
				String rozmiar = Integer.toString(GUI.this.Getsizeoftheboard());
				String zkimgram;
				if(getBot() == true) {
					zkimgram = "b";
				}
				else {
					zkimgram = "p";
				}
				getClient();
				GUI.this.gracz.sendCommand(rozmiar+" "+zkimgram);
				WaitingFrame okno = new WaitingFrame();
				GUI.this.setWFrame(okno);
				//okno.add(new WaitingPanel());
				//okno.setSize(new Dimension(300,300));
				//okno.pack();
				//okno.setLocationRelativeTo(null);
				//okno.setVisible(true);
				//zaleznie od tego co przyjdzie, czy mozesz przyjsc cos innego niz tak?
				try {
					while(GUI.this.gracz.hasServerSendCommand() == false) {
						//
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					if(GUI.this.gracz.getServerCommand().equals("B")) {
						setWhichplayer(true);
						setActive(false);
						//odpal Timera
						Timer timer = new Timer();
						TimerTask task = new coms_to_gui(); 
						timer.scheduleAtFixedRate(task,date,2000);
						setTimer(timer);
					}
					else {
						setWhichplayer(false);
						setActive(true);
					}
					okno.setVisible(false);
					initMainFrame();
				}
				
				
			}
	}
	
	}	
	

	
	
	
	
	
	
	
	
	public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
    		//normalnie bedzie z @Overrides
			public void run() {
				new GUI().initFirstFrame();
			}
		});
    }

 }
