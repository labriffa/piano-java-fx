package soundstyle;

/**
 * The GuitarSoundStyle class is responsible for providing the sounds
 * of a guitar.
 */
public class GuitarSoundStyle extends SoundStyle {

	@Override
	public void play(String keyAudioSource) {
		super.setSoundType("_guitar");
		super.play(keyAudioSource);
	}
}
