package main;
import command.CommandIF;

/**
 * The MelodyPlayer class is responsible for playing a given Melody.
 */
public class MelodyPlayer extends Thread implements Runnable {

	private Melody melody;
	
	public MelodyPlayer(Melody melody) {
		this.melody = melody;
	}
	
	public MelodyPlayer() {}
	
	/**
	 * Iterates through the series of commands that make up the melody
	 * and invokes their execute methods. 
	 */
	@Override
	public void run() {
		
		for (CommandIF command : melody.getMelodySequence() ) {
			
			command.execute();
    	}
	}
}
