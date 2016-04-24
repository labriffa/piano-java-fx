package command;
import java.io.Serializable;

import main.Piano;

/**
 * The LightOffCommand class provides an implementation for the CommandIF execute method.
 * It's responsibility is to invoke the receivers light off method.
 *
 */
public class LightOffCommand implements CommandIF, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyId;
	
	public LightOffCommand(String keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public void execute() {
		Piano.getPianoKeyMap().get(keyId).lightOff();
	}
}
