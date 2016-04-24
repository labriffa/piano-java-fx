package soundstyle;

import javafx.scene.media.AudioClip;
import view.PianoKey;

/**
 * The SoundStyle class provides an abstract representation of a particular instrumental sound
 */
public abstract class SoundStyle implements Playable {
	
	private String soundType;
	private AudioClip keyAudio;
	
	/**
	 * Plays the given audio source
	 * 
	 * @param keyAudioSource
	 */
	public void play(String keyAudioSource) {
		keyAudio = new AudioClip(PianoKey.class.getResource("../resources/" + keyAudioSource + 
				soundType + ".wav").toExternalForm());
		
		play();
	}	
	
	@Override
	public void play() {
		keyAudio.play();
	}
	
	/**
	 * Allows the sound type to be changed
	 * 
	 * @param soundType
	 */
	public void setSoundType(String soundType) {
		this.soundType = soundType;
	}
}
