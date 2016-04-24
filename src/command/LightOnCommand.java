package command;
import java.io.Serializable;

import main.Piano;

/**
 * The LightOnCommand class provides an implementation for the CommandIF execute method.
 * It's responsibility is to invoke the receivers light on method.
 *
 */
public class LightOnCommand implements CommandIF, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyId;
	
	public LightOnCommand(String keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public void execute() {
		Piano.getPianoKeyMap().get(keyId).lightOn();
	}
}
