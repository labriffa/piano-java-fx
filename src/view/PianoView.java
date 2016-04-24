package view;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The PianoView provides the graphical representation of the piano
 */
public class PianoView extends Pane {

	private BorderPane borderPane;
	private HBox whiteKeyHbox, blackKeyHbox;
	private VBox soundStyleVbox, melodiesVbox, statusVbox;
	private HashMap<String, PianoKey> pianoKeyMap;
	private Button playRecordingBtn, recordBtn;
	RadioButton normalPianoRadioButton, guitarPianoRadioButton, violinPianoRadioButton, glockenspielPianoRadioButton;
	private ToggleGroup pianoSoundToggleGroup;
	private ListView<String> melodyListView;
	private String[] whiteKeys = {"C","D","E","F","G","A","B"};
	private String[] blackKeys = {"C#","D#","F#","G#","A#"};
	private Label statusLabel;
	private ImageView conductorImageView;
	private PianoKeyFactory pianoKeyFactory;
	
	public PianoView() {
		
		pianoKeyMap = new HashMap<String, PianoKey>();
		borderPane = new BorderPane();
		
		whiteKeyHbox = new HBox();
		blackKeyHbox = new HBox();
		blackKeyHbox.setPadding(new Insets(0,0,0,76));
		blackKeyHbox.setPickOnBounds(false);
		
		playRecordingBtn = new Button("Play Recording");
		recordBtn = new Button("Record");
		
		melodyListView = new ListView<String>();
		melodyListView.setPadding(new Insets(10,10,10,10));
		melodyListView.setPrefHeight(130);
		melodyListView.setStyle("-fx-background-color: black");
		
		pianoKeyFactory = new PianoKeyFactory();
		
		// create the white piano keys
		for (String whitekey : whiteKeys) {
			PianoKey keyView = pianoKeyFactory.create(whitekey);
			whiteKeyHbox.getChildren().add(keyView);
			pianoKeyMap.put(whitekey, keyView);
		}
		
		// create the black piano keys
		for (int i = 0; i < blackKeys.length; i++) {
			PianoKey keyView = pianoKeyFactory.create(blackKeys[i]);
			blackKeyHbox.setSpacing(58);
			blackKeyHbox.getChildren().add(keyView);
			pianoKeyMap.put(blackKeys[i], keyView);

			// push the second grouping of black keys to the right
			if (i >= 2) {
				keyView.setTranslateX(106);
			} 
			
			getStyleClass().add("piano");
		}
		
		pianoSoundToggleGroup = new ToggleGroup();
		
		normalPianoRadioButton = new RadioButton("Piano");
		normalPianoRadioButton.setToggleGroup(pianoSoundToggleGroup);
			
		guitarPianoRadioButton = new RadioButton("Guitar");
		guitarPianoRadioButton.setToggleGroup(pianoSoundToggleGroup);
			
		violinPianoRadioButton = new RadioButton("Violin");
		violinPianoRadioButton.setToggleGroup(pianoSoundToggleGroup);
			
		glockenspielPianoRadioButton = new RadioButton("Glockenspiel");
		glockenspielPianoRadioButton.setToggleGroup(pianoSoundToggleGroup);
			
		normalPianoRadioButton.setSelected(true);
		
		soundStyleVbox = new VBox();
		soundStyleVbox.setPadding(new Insets(10,10,10,10));
		soundStyleVbox.getChildren().addAll(new Label("Sound Types"), normalPianoRadioButton, 
				guitarPianoRadioButton, violinPianoRadioButton, glockenspielPianoRadioButton);
		
		statusLabel = new Label("Press A Piano Key To Begin");
		statusLabel.setId("status");
		conductorImageView = new ImageView();
		conductorImageView.setImage(new Image(PianoKey.class.getResource("../resources/conductor.gif").toExternalForm()));
		statusVbox = new VBox(conductorImageView, statusLabel);
		statusVbox.setTranslateX(50);
		statusVbox.setTranslateY(-10);
		
		melodiesVbox = new VBox();
		melodiesVbox.getChildren().addAll(new Label("Melodies"), melodyListView);
		melodiesVbox.setTranslateY(-70);
		
		borderPane.setTop(recordBtn);
		borderPane.setLeft(soundStyleVbox);
		borderPane.setRight(melodiesVbox);
		borderPane.setCenter(statusVbox);
		borderPane.setBottom(new StackPane(whiteKeyHbox, blackKeyHbox));
		getChildren().add(borderPane);
	}
	
	/**
	 * Sets the passed event handler to the piano keys
	 * 
	 * @param mouseHandler
	 */
	public void setOnMouseHandler(EventHandler<MouseEvent> mouseHandler) {
		
		for (PianoKey pianoKey : pianoKeyMap.values()) {
			pianoKey.setOnMouseEntered(mouseHandler);
			pianoKey.setOnMouseExited(mouseHandler);
			pianoKey.setOnMousePressed(mouseHandler);
		}
	}
	
	/**
	 * Sets the passed event handler to the melody list
	 * 
	 * @param listMouseHandler
	 */
	public void setListMouseEventHandler(EventHandler<MouseEvent> listMouseHandler) {
		melodyListView.setOnMousePressed(listMouseHandler);
	}
	
	/**
	 * Sets the passed event handler to the record button
	 * 
	 * @param buttonHandler
	 */
	public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
		playRecordingBtn.setOnAction(buttonHandler);
		recordBtn.setOnAction(buttonHandler);
	}
	
	/**
	 * Allows the record button's text to be changed
	 * 
	 * @param text
	 */
	public void setRecordBtnText(String text) {
		recordBtn.setText(text);
	}
	
	/**
	 * Retrieves the selection made in the melody list
	 * 
	 * @return String
	 */
	public String getSelectedMelody() {
		return melodyListView.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Allows the melody list to be populated
	 * 
	 * @param melodyItems
	 */
	public void setMelodyListItems(ObservableList<String> melodyItems) {
		melodyListView.setItems(melodyItems);
	}
	
	/**
	 * Accepts and sets the event handler to the piano sound toggle group
	 * 
	 * @param toggleChangeListener
	 */
	public void setToggleGroupChangeListner(ChangeListener<Toggle> toggleChangeListener) {
		pianoSoundToggleGroup.selectedToggleProperty().addListener(toggleChangeListener);
	}
	
	/**
	 * Retrieves the piano keys
	 * 
	 * @return
	 */
	public HashMap<String,PianoKey> getPianoKeyMap() {
		return pianoKeyMap;
	}
	
	/**
	 * Sets the text of the status label
	 * 
	 * @param statusLabel
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel.setText(statusLabel);
	}
}
