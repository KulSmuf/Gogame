package GameOfGo.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class MyPanel extends JPanel implements ActionListener{
	GUI gui;
	
	private int dim = 0;
	private int height = 0;
	private int width = 0;
	private ArrayList<Line2D> lines = new ArrayList<Line2D>();
	//tablica przyciskow
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	//kamienie
	private ArrayList<JButton> stones = new ArrayList<JButton>();
	//kamienie przeciwnika
	ArrayList<JButton> opponent_stones = new ArrayList<JButton>();
	
	private Color background = new Color(249, 235, 106);
	
	//bedzie czytal string od serwera, sam go przetwarzal i dodawal w tym miejscu kamien przeciwnika
	public void addstone(String position) {
		//parse string
		//nie uwzgledniamy tych co przeciwnik zbil
		String[] tokenss = position.split(" ");
		String[] tokens = tokenss[0].split(",");
		if(tokens == null) {
			throw new IllegalArgumentException("zły format");
		}
		int x = Integer.parseInt(tokens[0]);
		int y = Integer.parseInt(tokens[1]);
		if(x>gui.Getsizeoftheboard() || y>gui.Getsizeoftheboard() || x<0 || y<0) {
			throw new IllegalArgumentException("złe dane");
		}
		
		//znalezc odpowiedni przycisk
		 for(JButton b:buttons) {
			if(x == b.getBounds().x/40-1 && y == b.getBounds().y/40-1) {
				b.setEnabled(false);
				b.setVisible(false);
				opponent_stones.add(b);
				repaint();
			}
		 
		}
		if(tokenss[1] != "0") {
			int ile = Integer.parseInt(tokenss[1]);
			for(int u=0;u<ile;u++) {
				String[] namiar = tokenss[2+u].split(",");
				int s = Integer.parseInt(namiar[0]);
				int r = Integer.parseInt(namiar[1]);
				for(JButton b:buttons) {
					if(s == b.getBounds().x/40-1 && r == b.getBounds().y/40-1) {
						//usunac moje kamyki i zwolnic miejsce, wlaczyc przyciski
						b.setEnabled(true);
						b.setVisible(true);
						stones.remove(b);
						repaint();
					}
				 }
			}
		}
	}
	
	public void actionPerformed(ActionEvent f) {
		//sprawdza czy nie tura przeciwnika
		if(gui.isActive()) {
		Object source = f.getSource();
		//wysyla serwerowi ruch gracza w postaci XY zaczynajac od 1x1
		String str = Integer.toString((int)((AbstractButton)source).getBounds().getX()/40-1);
		String str2 = Integer.toString((int)((AbstractButton)source).getBounds().getY()/40-1);
	
			gui.getTimer().cancel();
		
		gui.player.sendCommand(str+" "+str2);
		//if SendCommand=1 przeprowadz ruch, jesli nie to nic nie rob
		try {
			while(gui.player.hasServerSendCommand() == false) {
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String response = gui.player.getServerCommand();
		//JOptionPane.showMessageDialog(getFrame(), response);
		if(response.equals("1")) {
			((AbstractButton) source).setEnabled(false);
			((AbstractButton) source).setVisible(false);
			this.stones.add((JButton) source);
			repaint();
			//wywoluj metode tura przeciwnika
			//czekaj na ruch przeciwnika, jesli null to znaczy ze wyszedl
			//tura Przeciwnika wiec nic nie mozesz zrobic
			gui.setActive(false);
			gui.getSPanel().turaPrzeciwnika();
			Timer timer = new Timer();
			TimerTask task = new coms_to_gui(this.gui);
			Date date = new Date();
			timer.scheduleAtFixedRate(task, date, 2000);
			gui.setTimer(timer);
		}
		else if(!response.equals("0") && !response.equals("1") && response.isEmpty()==false) {
			String[] tokens = response.split(" ");
			for(int u=1;u<=Integer.parseInt(tokens[0]);u++) {
				String[] cordinates = tokens[u].split(",");
				int s = Integer.parseInt(cordinates[0]);
				int r = Integer.parseInt(cordinates[1]);
				for(JButton b:buttons) {
					if(s == b.getBounds().x/40-1 && r == b.getBounds().y/40-1) {
						//usunac kamienie przeciwnika i zwolnic miejsce, wlaczyc przyciski
						b.setEnabled(true);
						b.setVisible(true);
						opponent_stones.remove(b);
						//repaint();
					}
				 }
			}
		((AbstractButton) source).setEnabled(false);
		((AbstractButton) source).setVisible(false);
		this.stones.add((JButton) source);
		repaint();
		//wywoluj metode tura przeciwnika
		//czekaj na ruch przeciwnika, jesli null to znaczy ze wyszedl
		//tura Przeciwnika wiec nic nie mozesz zrobic
		gui.setActive(false);
		gui.getSPanel().turaPrzeciwnika();
		Timer timer = new Timer();
		Date date = new Date();
		TimerTask task = new coms_to_gui(this.gui); 
		timer.scheduleAtFixedRate(task, date, 2000);
		gui.setTimer(timer);
		}
		}
	}
	
	
	public MyPanel(int size, GUI gui) {
		this.gui = gui;
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
				//co lepiej?
				//Border emptyBorder = BorderFactory.createEmptyBorder();
				//ada.setBorder(emptyBorder);
				buttons.add(ada);
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
			lines.add(newline);
			g2d.draw(newline);
			Line2D newline2 = new Line2D.Double(i*40, 40, i*40, dim*40);
			lines.add(newline2);
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
		for(JButton s:stones) {
			int x = s.getLocation().x - 15;
			int y = s.getLocation().y - 15;
			if(gui.isWhichplayer()==true) {
			    iconw.paintIcon(this, g, x, y);
			}
			else {
				iconb.paintIcon(this, g, x, y);
			}
		}
			
		for(JButton p:opponent_stones) {
			int xp = p.getLocation().x - 15;
			int yp = p.getLocation().y - 15;
			if(gui.isWhichplayer()==true) {
			    iconb.paintIcon(this, g, xp, yp);
			}
			else {
				iconw.paintIcon(this, g, xp, yp);
			}
		}
		
		

		

		
	}
	
}
