package view;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * The WhiteKey class provides the appearance settings needed to 
 * distinguish it as a natural piano key. 
 * 
 */
public class WhiteKey extends PianoKey {
	
	public WhiteKey(String keyAudioFile, String keyText, String background, String backgroundHover) {
			super(114.2, 324, keyAudioFile, keyText, background, backgroundHover);
			setStroke(Color.BLACK);
			setFill(Color.WHITE);
			setFill(new ImagePattern(new Image(getBackground())));
	}	
	
	/**
	 * The effect when a user hovers onto the key
	 */
	public void lightOn() {
		setFill(new ImagePattern(new Image(getBackgroundHover())));
	}
	
	/**
	 * The effect when a user hovers out of the key
	 */
	public void lightOff() {
		setFill(new ImagePattern(new Image(getBackground())));
	}
}
