package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * The MelodyReaderWriter is responsible for writing out and reading in
 * serialized objects from a file source.
 */
public class MelodyReaderWriter {
	
	/**
	 * Writes serialized objects to a specified file
	 * 
	 * @param melodyList
	 * @param fileName
	 * @serialData ArrayList<Melody>
	 * @throws IOException
	 */
	public static void writeMelodies(ArrayList<Melody> melodyList, String fileName) throws IOException {
		OutputStream file = null;
		OutputStream buffer = null;
		OutputStream output = null;

		try {
			file = new FileOutputStream(fileName);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			((ObjectOutputStream) output).writeObject(melodyList);
		} finally {
			if (output != null) {
				output.close();
			}
			if (buffer != null) {
				buffer.close();
			}
			if (file != null) {
				file.close();
			}
		}
	}

	/**
	 * 
	 * Reads serialized objects from a specified file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Melody> readMelodies(String fileName) throws IOException, ClassNotFoundException {
		InputStream file = null;
		InputStream buffer = null;
		ObjectInput input = null;

		try {
			file = new FileInputStream(fileName);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);

			@SuppressWarnings("unchecked")
			ArrayList<Melody> readInventory = (ArrayList<Melody>) input.readObject();
			
			return readInventory;
		} finally {
			if (input != null) {
				input.close();
			}
			if (buffer != null) {
				buffer.close();
			}
			if (file != null) {
				file.close();
			}
		}
	}
}
