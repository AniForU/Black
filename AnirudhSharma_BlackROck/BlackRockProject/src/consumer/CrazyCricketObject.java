package consumer;
import java.io.Serializable;

public class CrazyCricketObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CrazyCricketProtos.Player player;
	private CrazyCricketProtos.Game game;
	public CrazyCricketProtos.Player getPlayer() {
		return player;
	}
	public void setPlayer(CrazyCricketProtos.Player player) {
		this.player = player;
	}
	public CrazyCricketProtos.Game getGame() {
		return game;
	}
	public void setGame(CrazyCricketProtos.Game game) {
		this.game = game;
	}	
	
}
