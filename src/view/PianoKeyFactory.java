package view;

import main.FactoryIF;

/**
 * The PianoKeyFactory creates PianoKey objects on demand when given a string to check
 * against.
 */
public class PianoKeyFactory implements FactoryIF {

	private static PianoKey keyView = null;
	
	/**
	 * Checks to see if the String passed is either a natural or a sharp and creates the relevant object
	 * 
	 * @param keyType
	 * @return PianoKey on success
	 * 		   null on failure
	 */
	public PianoKey create(String keyType) {
	
		if (keyType.endsWith("#")) {
			
			keyView = new BlackKey(keyType +"_key", keyType, keyType + "_key_no_hover.jpg", keyType + "_key_on_hover.jpg");
		
		} else {
			keyView = new WhiteKey(keyType +"_key", keyType, keyType + "_key_no_hover.jpg", keyType + "_key_on_hover.jpg");
		}
		
		return keyView;
		
	}
}