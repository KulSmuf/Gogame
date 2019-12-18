package GameOfGo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dialog.ModalityType;
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
import javax.swing.JDialog;
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
    TimerTask task = new coms_to_gui(this); 
    Date date = new Date();
	
	JDialog dialog;
	
	SetSizeFrame frame;
	
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
	public Client player;
	
	void getClient() {
		try {
			player = new Client();
		}
		catch(Exception e) {
			 JOptionPane.showMessageDialog(getFrame(),
	         	        "Brak odpowiedzi od serwera");
	         	    	System.exit(0);
		}
	}
	//firstframe
	private JFrame pierwsza;
	//waitingframe
	private JFrame waiting;
	//mainframe
	private JFrame main;
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
	
	public void setDialog(JDialog p) {
		this.dialog = p;
	}
	
	public JDialog getDialog() {
		return this.dialog;
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
	
	public void setMFrame(JFrame frame) {
		this.main = frame;
	}
	
	public JFrame getMFrame() {
		return this.main;
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
	
	public void setFrame(JFrame frame) {
		this.pierwsza = frame;
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
	
	
	
	
	
	public void initExitWindow(String store) {
	
        //wynik postaci XX:YY
        if(store.equals("exit")) {
        	JOptionPane.showMessageDialog(getMFrame(),
        	        "Przeciwnik się poddał");
        	    	System.exit(0);
        }
        else if(store.equals("surr")) {
        	JOptionPane.showMessageDialog(getMFrame(),
        	        "Poddałeś się");
        	    	System.exit(0);
        }
        else {
         String[] didyouwin = store.split(" ");
         String[] score = didyouwin[1].split(":");
         String points;
         String points2;
         if(isWhichplayer()==false) {
         points = score[0];
         points2 = score[1];
         }
         else {
        	points = score[1];
        	points2 = score[2];
         }
         if(Integer.parseInt(points)>Integer.parseInt(points2)) {
        	 JOptionPane.showMessageDialog(getMFrame(),
         	        "Wygrałeś z wynikiem "+points+" do "+points2);
         	    	System.exit(0);
         }
         else {
        	 JOptionPane.showMessageDialog(getMFrame(),
          	        "Przegrałeś z wynikiem "+points+" do "+points2);
          	    	System.exit(0);
         }
	    }
	}
	
	
	
	public void initFirstFrame() {
		//init okna wyboru
		JFrame one = new SetSizeFrame(this);
		frame = (SetSizeFrame) one;
		//fwidth = one.getWidth();
		//fheight = one.getHeight();
		one.setLayout(new GridLayout());
		one.add(new FirstPanel(this));
		one.pack();
		one.setLocationRelativeTo(null);
		one.setVisible(true);
		GUI.this.setFrame(one);
	}
	
		//metoda GUI po zrobieniu ruchu przez przeciwnika
		//przekazuje string z lokacja kamienia przeciwnika
		public void makeMove(String namiary) {
			if(namiary.equals("pass")) {
				getSPanel().turaGracza();
				setActive(true);
			}
			else {
				this.panel.addstone(namiary);
				getSPanel().turaGracza();
				setActive(true);
			}
		
		}
	
	//glowne okno
	public void initMainFrame() {
		//GUI.this.getDialog().dispose();
		GUI.this.getFrame().dispose();
		JFrame main = new MainFrame(this);
		//add frame
		
		//main.setSize(new Dimension(650,600));
		main.setLayout(new GridLayout(0,2));
		//main.setLayout(new GridBagLayout());
		MyPanel panel1 = new MyPanel(this.sizeofboard, this);
		main.add(panel1);
		SecondPanel panel2 = new SecondPanel(this.sizeofboard, this);
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
	
	public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
    		//normalnie bedzie z @Overrides
			public void run() {
				GUI gui = new GUI();
				gui.initFirstFrame();
			}
		});
    }

 }
