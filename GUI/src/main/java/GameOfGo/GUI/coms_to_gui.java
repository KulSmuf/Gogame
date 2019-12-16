package GameOfGo.GUI;

import java.io.IOException;
import java.util.TimerTask;

public class coms_to_gui extends TimerTask
{
	GUI gui;
	String odp;
	
	public coms_to_gui(GUI gui) {
		this.gui = gui;
	}

	public void run() {
		try {
			if(gui.player.hasServerSendCommand()) {
				odp = gui.player.getServerCommand();
				if(odp.contains("exit")){
				 //konczy sie gra
					gui.initExitWindow(odp);
				}
				else {
				//to byl ruch
				 gui.zrobRuch(odp);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block-
			e.printStackTrace();
		}
		
	}
	
}