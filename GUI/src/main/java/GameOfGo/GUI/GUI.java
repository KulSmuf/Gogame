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
    TimerTask task = new coms_to_gui(this); 
    Date date = new Date();
	
	
	
	
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
		public void zrobRuch(String namiary) {
			this.panel.addkamien(namiary);
			getSPanel().turaGracza();
			setActive(true);
			
		}
	
	//glowne okno
	public void initMainFrame() {
		GUI.this.getFrame().dispose();
		JFrame main = new MainFrame(this);
		
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
