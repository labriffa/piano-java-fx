package view;

import javafx.scene.shape.Rectangle;
import soundstyle.PianoSoundStyle;
import soundstyle.Playable;
import soundstyle.SoundStyle;

/**
 * The PianoKey class is an abstract representation of a key on the piano
 */
public abstract class PianoKey extends Rectangle implements Playable {

	private String keyAudioSource;
	private String keyId;
	private String background;
	private String backgroundHover;
	private SoundStyle soundStyle;
	private int timesPressed;
	
	public PianoKey(double width, double height, String keyAudioSource, String keyText, 
			String background, String backgroundHover) {
		
		this.keyAudioSource = keyAudioSource;
		this.background = PianoKey.class.getResource("../resources/"+ background).toExternalForm();
		this.backgroundHover = PianoKey.class.getResource("../resources/"+ backgroundHover).toExternalForm();
		setKeyId(keyText);
		setWidth(width);
		setHeight(height);
		soundStyle = new PianoSoundStyle();
		
		this.getStyleClass().add("key");
	}
	
	/**
	 * Play AudioClip under the current sound style
	 */
	@Override
	public void play() {
		soundStyle.play(keyAudioSource);
	}
	
	/**
	 * Sets the current sound style
	 * 
	 * @param soundStyle
	 */
	public void setSoundStyle(SoundStyle soundStyle) {
		this.soundStyle = soundStyle;
	}
	
	public abstract void lightOn();
	public abstract void lightOff();	
	
	/**
	 * Sets the key id of this Piano key
	 * 
	 * @param keyId
	 */
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	/**
	 * Gets the key id of this Piano key
	 * 
	 * @return String
	 */
	public String getKeyId() {
		return keyId;
	}
	
	/**
	 * Gets the filename for the background
	 * 
	 * @return String
	 */
	public String getBackground() {
		return background;
	}
	
	/**
	 * Gets the filename for the background hover
	 * 
	 * @return String
	 */
	public String getBackgroundHover() {
		return backgroundHover;
	}
	
	/**
	 * Increments the number of times this Piano Key has been pressed
	 */
	public void incrementTimesPressed() {
		timesPressed++;
	}
	
	/**
	 * Gets the number of times this Piano Key has been pressed
	 * 
	 * @return int
	 */
	public int getTimesPressed() {
		return timesPressed;
	}
}
