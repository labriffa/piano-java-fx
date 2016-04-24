package soundstyle;

/**
 * The ViolinSoundStyle class is responsible for providing the sounds
 * of a violin.
 */
public class ViolinSoundStyle extends SoundStyle {

	@Override
	public void play(String keyAudioSource) {
		super.setSoundType("_violin");
		super.play(keyAudioSource);
	}
	
}
