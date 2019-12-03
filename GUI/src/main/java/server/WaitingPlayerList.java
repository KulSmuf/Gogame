package server;

import java.util.ArrayList;

class WaitingPlayerList {
	ArrayList<WaitingPlayer> playerList;
	
	public WaitingPlayerList() {
		playerList = new ArrayList<WaitingPlayer>();
	}
	
	public Player getWaitingPlayer(int boardSize){
		for( WaitingPlayer player: playerList ) {
			if( player.getBoardSize() == boardSize ) return player.getWaitingPlayer();
		}
		return null;
	}
	
	public void removeWaitingPlayer( int boardSize ) {
		for( int i=0;i<playerList.size();i++ ) {
			if( playerList.get(i).getBoardSize() == boardSize ) {
				playerList.remove(playerList.get(i));
			}
		}
	}
	
	public void addWaitingPlayer( Player player, int boardSize ) {
		playerList.add(new WaitingPlayer(player, boardSize));
	}
	
	class WaitingPlayer{
		
		private Player waitingPlayer;
		private int boardSize;
		
		public WaitingPlayer(Player waitingPlayer, int boardSize) {
			this.waitingPlayer = waitingPlayer;
			this.boardSize = boardSize;
		}

		public Player getWaitingPlayer() {
			return waitingPlayer;
		}

		public int getBoardSize() {
			return boardSize;
		}
	}
}
