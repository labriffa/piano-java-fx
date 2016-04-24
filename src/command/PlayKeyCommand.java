package command;


import java.io.Serializable;

import main.Piano;

/**
 * The PlayKeyCommand class provides an implementation for the CommandIF execute method.
 * It's responsibility is to invoke the receivers play method.
 */
public class PlayKeyCommand implements CommandIF, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyId;
	
	public PlayKeyCommand(String keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public void execute() {
		Piano.getPianoKeyMap().get(keyId).play();
	}
}
