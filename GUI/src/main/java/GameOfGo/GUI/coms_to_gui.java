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
			if(gui.gracz.hasServerSendCommand()) {
				odp = gui.gracz.getServerCommand();
				gui.zrobRuch(odp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block-
			e.printStackTrace();
		}
		//if na wiadomosc przy poddaniu i wygraniu i przegraniu
		//zrobRuch(odp);
		
	}
	
}