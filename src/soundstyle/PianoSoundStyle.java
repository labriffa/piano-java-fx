package soundstyle;

/**
 * The PianoSoundStyle class is responsible for providing the sounds
 * of a piano.
 */
public class PianoSoundStyle extends SoundStyle {
	
	@Override
	public void play(String keyAudioSource) {
		super.setSoundType("_piano");
		super.play(keyAudioSource);
	}
}
