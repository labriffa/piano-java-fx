package soundstyle;

import main.FactoryIF;

/**
 * The SoundStyleFactory creates SoundStyle objects on demand when given a string to check
 * against.
 */
public class SoundStyleFactory implements FactoryIF {

	private static SoundStyle soundStyle = null;
	
	/**
	 * Takes the passed String and chooses the relevant SoundStyle object to create
	 * and return
	 *
	 * @param soundStyleType
	 * @return SoundStyle on success
	 * 		   null on failure
	 */
	public SoundStyle create(String soundStyleType) {
		
		if (soundStyleType.equals("piano")) {
			soundStyle = new PianoSoundStyle();
		} else if (soundStyleType.equals("guitar")) {
			soundStyle = new GuitarSoundStyle();
		} else if (soundStyleType.equals("glockenspiel")) {
			soundStyle = new GlockenspielSoundStyle();
		} else if (soundStyleType.equals("violin")) {
			soundStyle = new ViolinSoundStyle();
		} 
		
		return soundStyle;
		
	}
	
}
