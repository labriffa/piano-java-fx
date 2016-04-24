package soundstyle;

/**
 * The GlockenspielSoundStyle class is responsible for providing the sounds
 * of a glockenspiel.
 */
public class GlockenspielSoundStyle extends SoundStyle {

	@Override
	public void play(String keyAudioSource) {
		super.setSoundType("_glockenspiel");
		super.play(keyAudioSource);
	}
}
