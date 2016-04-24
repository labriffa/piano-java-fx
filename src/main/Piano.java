package main;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import command.DelayCommand;
import command.LightOffCommand;
import command.LightOnCommand;
import command.PlayKeyCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import soundstyle.SoundStyleFactory;
import view.PianoKey;
import view.PianoView;

/**
 * The Piano class provides the functionality needed to interact with a piano. 
 * It enables users to make recordings of their actions and enables them to play 
 * a series of desired melodies.
 */
public class Piano extends BorderPane {

	private boolean recording;
	private long time, oldTime;
	private Melody currentMelodyRecording;
	Melody currentMelody;
	MelodyLibrary melodyLibrary;
	private Optional<String> melodyName;
	private static final PianoView pianoView = new PianoView();
	MelodyPlayer melodyPlayer;

	// Sets the sound style of the piano to the currently selected radio button
	ChangeListener<Toggle> toggleChangeListener = new ChangeListener<Toggle>(){

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			
			RadioButton pianoRadioButton = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
			String soundType = pianoRadioButton.getText();
			SoundStyleFactory soundStyleFactory = new SoundStyleFactory();

			for (String keyViewId : getPianoKeyMap().keySet()) {
				getPianoKeyMap().get(keyViewId).setSoundStyle(soundStyleFactory.create(soundType.toLowerCase()));
			}

		}
	};

	// Plays the selected item in the melodies list
	EventHandler<MouseEvent> listMouseHandler = new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent event) {

			String selectedMelody = pianoView.getSelectedMelody();
			currentMelody = melodyLibrary.getMelody(selectedMelody);
			
			// make sure a melody isn't already playing
			if (!melodyPlayer.isAlive() && currentMelody != null) {
				currentMelody.incrementTimesPlayed();
				melodyPlayer = new MelodyPlayer(currentMelody);
				pianoView.setStatusLabel("Now Playing: " + currentMelody.getMelodyName());
				melodyPlayer.start();	
			}
		}
	};

	// Adds the hover effect and allows a user to play a key
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			PianoKey keyView = (PianoKey) event.getSource();

			if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
				keyView.lightOn();
			} else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
				keyView.lightOff();
			}

			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

				keyView.incrementTimesPressed();
				pianoView.setStatusLabel("You Pressed: " + keyView.getKeyId());
				keyView.play();

				if (recording) {
					time = System.currentTimeMillis();
					long delay = time - oldTime;
					oldTime = time;

					currentMelodyRecording.addCommand(new PlayKeyCommand(keyView.getKeyId()));
					currentMelodyRecording.addCommand(new LightOnCommand(keyView.getKeyId()));
					currentMelodyRecording.addCommand(new DelayCommand(delay));
					currentMelodyRecording.addCommand(new LightOffCommand(keyView.getKeyId()));
				}
			} 
		}
	};

	// Handles the click of the recording button
	EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {

			Button selectedBtn = (Button) event.getSource();

			if (selectedBtn.getText().equals("Record")) {
				recordPlayingSession();
				currentMelodyRecording = new Melody();
				selectedBtn.getStyleClass().add("recording");

			} else if (selectedBtn.getText().equals("Stop Recording")) {
				stopPlayingSession();
				selectedBtn.getStyleClass().remove("recording");

				if (melodyName.isPresent()) {
					melodyLibrary.addMelody(getRecordedMelody());
				}
				
				
				try {
					MelodyReaderWriter.writeMelodies(melodyLibrary.getMelodies(), "melodies.txt");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				updateMelodyList();
			}
		}
	};

	public Piano(MelodyLibrary melodyLibrary) throws ClassNotFoundException, IOException {

		try {
			melodyLibrary.setMelodyLibrary((MelodyReaderWriter.readMelodies("melodies.txt")));	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		recording = false;
		currentMelodyRecording = new Melody();
		melodyPlayer = new MelodyPlayer();
		this.melodyLibrary = melodyLibrary;
		
		pianoView.setOnMouseHandler(mouseHandler);
		pianoView.setButtonHandler(buttonHandler);
		pianoView.setListMouseEventHandler(listMouseHandler);
		pianoView.setToggleGroupChangeListner(toggleChangeListener);
		getChildren().add(pianoView);
		
		this.getStyleClass().add("piano");
		
		updateMelodyList();
	}

	/**
	 * Creates a melody out of the current recording
	 * 
	 * @return Melody
	 */
	private Melody getRecordedMelody() {
		return new Melody(melodyName.get(), currentMelodyRecording.getMelodySequence());
	}

	/**
	 * Retrieves the keys that make up the piano 
	 * 
	 * @return HashMap<String, PianoKey>
	 */
	public static HashMap<String, PianoKey> getPianoKeyMap() {
		return pianoView.getPianoKeyMap();
	}

	/**
	 * Updates the melody list in the piano view
	 */
	public void updateMelodyList() {
		ObservableList<String> items = FXCollections.observableArrayList();

		for (Melody melody : melodyLibrary.getMelodies()) {
			items.add(melody.getMelodyName());
		}
		
		pianoView.setMelodyListItems(items);
	}

	/**
	 * Provides the steps required to start a recording
	 */
	private void recordPlayingSession() {
		recording = true;
		time = System.currentTimeMillis();
		oldTime = System.currentTimeMillis();
		pianoView.setRecordBtnText("Stop Recording");
	}

	/**
	 * Provides the steps required to stop a recording
	 */
	private void stopPlayingSession() {
		recording = false;
		pianoView.setRecordBtnText("Record");
		TextInputDialog dialog = new TextInputDialog("My Melody");
		melodyName = dialog.showAndWait();
	}
	
	/**
	 * Retrieves the melody library associated with this piano
	 * 
	 * @return MelodyLibrary
	 */
	public MelodyLibrary getMelodyLibrary() {
		return melodyLibrary;
	}
}
