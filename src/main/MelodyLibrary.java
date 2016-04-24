package main;

import java.util.ArrayList;

/**
 * The MelodyLibrary class represents an arbitrary collection of melodies.
 * It provides various methods such as those that allow the user to retrieve a 
 * melody by name, add a melody or remove a melody from the existing library.
 */
public class MelodyLibrary {

	private ArrayList<Melody> melodies;
	
	public MelodyLibrary() {
		this.melodies = new ArrayList<Melody>();
	}
	
	/**
	 * Sets the library to hold a series of melodies
	 * 
	 * @param melodies
	 */
	public void setMelodyLibrary(ArrayList<Melody> melodies) {
		this.melodies = melodies;
	}
	
	/**
	 * Retrieves all melodies currently stored in the melody library
	 * 
	 * @return
	 */
	public ArrayList<Melody> getMelodies() {
		return melodies;
	}
	
	/**
	 * Adds a melody to the melody library
	 * 
	 * @param melody
	 */
	public void addMelody(Melody melody) {
		melodies.add(melody);
	}
	
	/**
	 * Removes a melody from the melody library
	 * 
	 * @param index
	 */
	public void removeMelody(int index) {
		melodies.remove(index);
	}
	
	/**
	 * Searches the melody library for a melody matching the specified search string
	 * and returns the first occurrence.
	 * 
	 * @param melodyName
	 * @return String on success
	 * 		   null on failure
	 */
	public Melody getMelody(String melodyName) {
		
		Melody foundMelody = null;
		
		for (Melody melody : melodies) {
			if ( melody.getMelodyName().equals(melodyName) ) {
				foundMelody = melody;
			}
		}
		
		return foundMelody;
	}
}
