package main;

import java.io.Serializable;
import java.util.ArrayList;

import command.CommandIF;

/**
 * The Melody class represents a melody made up of a series of commands.
 * It provides methods that allow the user to retrieve the name of the Melody as well as methods
 * that allow the user to retrieve the sequence of commands it is comprised of.
 *
 */
public class Melody implements Serializable {


	private static final long serialVersionUID = 1L;
	private String melodyName;
	private ArrayList<CommandIF> melody;
	private int timesPlayed;
	
	public Melody(String melodyName, ArrayList<CommandIF> melody) {
		this.melodyName = melodyName;
		this.melody = melody;
	}
	
	public Melody() {
		melody = new ArrayList<CommandIF>();
	}
	
	/**
	 * Allows commands to be added to the melody
	 * 
	 * @param command
	 */
	public void addCommand(CommandIF command) {
		melody.add(command);
	}
	
	/**
	 * Gets the commands that make up the melody
	 * 
	 * @return ArrayList<CommandIF>
	 */
	public ArrayList<CommandIF> getMelodySequence() {
		return melody; 
	}
	
	/**
	 * Gets the name of the melody
	 * 
	 * @return String
	 */
	public String getMelodyName() {
		return melodyName;
	}	
	
	/**
	 * Increments the number of times this melody has played by one
	 */
	public void incrementTimesPlayed() {
		timesPlayed++;
	}
	
	/**
	 * Gets the number of times this melody has been played
	 * 
	 * @return int
	 */
	public int getTimesPlayed() {
		return timesPlayed;
	}
}
