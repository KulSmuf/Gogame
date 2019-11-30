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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

	
	//firstframe
	private JFrame pierwsza;
	//9,13,19
	private int sizeofboard;
	//false to bot, true to gracz
	private boolean whichplayer;
	
	public void setFrame(JFrame frame) {
		this.pierwsza = frame;
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
	
	/*public class SecondPanel extends JPanel implements ActionListener{
		
		public SecondPanel() {
			setLayout(new GridBagLayout());
			//kogo tura
			//ilosc jencow
			//przycisk pass
			//przycisk poddaj sie
		}
		
		
	}*/
	
	
	
	
	
	public class MyPanel extends JPanel implements ActionListener{
		private int dim = 0;
		private int height = 0;
		private int width = 0;
		private ArrayList<Line2D> linie = new ArrayList<Line2D>();
		//tablica przyciskow
		private ArrayList<JButton> przyciski = new ArrayList<JButton>();
		//kamienie
		private ArrayList<JButton> kamienie = new ArrayList<JButton>();
		
		private Color background = new Color(249, 235, 106);
		
		public void actionPerformed(ActionEvent f) {
			//sprawdzaj ktory gracz gra, dodawaj do dwoch roznych list
			Object source = f.getSource();
			((AbstractButton) source).setEnabled(false);
			((AbstractButton) source).setVisible(false);
			this.kamienie.add((JButton) source);
			repaint();
			
		}
		
		
		public MyPanel(int size) {
			this.setLayout(null);
			this.setBackground(background);
			this.dim = size;
			switch(this.dim) {
				case 9:
					this.setPreferredSize(new Dimension(400, 400));
					//this.height = 550;
					//this.width = 550;
					break;
				case 13:
					this.setPreferredSize(new Dimension(560, 560));
					//this.height = 750;
					//this.width = 750;
					break;
				case 19:
					this.setPreferredSize(new Dimension(800, 800));
					//this.height = 1050;
					//this.width = 1050;
			}
			
			
			for(int r=1;r<=dim;r++) {
				for(int k=1;k<=dim;k++) {
					JButton ada = new JButton();
					ada.setBounds(r*40, k*40, 5, 5);
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
				
				//gracz
				if(isWhichplayer()==true) {
				    iconw.paintIcon(this, g, x, y);
				}
				//
				else {
					iconb.paintIcon(this, g, x, y);
				}
			}
			
			

			

			
		}
	}
	
	
	//glowne okno
	public void initMainFrame() {
		GUI.this.getFrame().dispose();
		JFrame main = new MainFrame();
		switch(GUI.this.sizeofboard) {
			case 9:
				main.setPreferredSize(new Dimension(650, 440));
				break;
			case 13:
				main.setPreferredSize(new Dimension(850, 560));
				break;
			case 19:
				main.setPreferredSize(new Dimension(1250, 800));
				break;
		}
		//main.setSize(new Dimension(650,600));
		main.setLayout(new FlowLayout(0,0,0));
		main.add(new MyPanel(this.sizeofboard));
		//main.setComponentOrientation(
        //       ComponentOrientation.LEFT_TO_RIGHT);
		//main.add(new JPanel());
		main.pack();
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
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
				
				if(size9.isEnabled() == false) {
					 GUI.this.setSizeoftheboard(9);
				}
				if(size13.isEnabled() == false) {
					GUI.this.setSizeoftheboard(13);
				}
				if(size19.isEnabled() == false) {
					GUI.this.setSizeoftheboard(19);
				}
				if(gracz.isEnabled() == false) {
					GUI.this.setWhichplayer(false);
				}
				if(bot.isEnabled() == false) {
					GUI.this.setWhichplayer(true);
				}
				
				initMainFrame();
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
