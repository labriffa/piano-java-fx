package view;

import javafx.scene.paint.Color;

/**
 * The BlackKey class provides the appearance settings needed to 
 * distinguish it as a sharp/flat piano key. 
 * 
 */
public class BlackKey extends PianoKey {

	public BlackKey(String keyAudioFile, String keyText, String background, String backgroundHover) {
			super(64, 170, keyAudioFile, keyText, background, backgroundHover);
	}
	
	/**
	 * The effect when a user hovers onto the key
	 */
	public void lightOn() {
		super.setFill(Color.WHITE);
	}

	/**
	 * The effect when a user hovers out of the key
	 */
	public void lightOff() {
		super.setFill(Color.BLACK);
	}	
}
