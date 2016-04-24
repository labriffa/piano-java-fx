package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Melody;
import main.MelodyLibrary;
import main.MelodyReaderWriter;
import main.Piano;

/**
 * The TeacherView class provides the statistical information for a given Piano by 
 * showcasing a series of graphs on a number of topics.
 */
public class TeacherView extends Pane {

	private Button refreshBtn, deleteMelodyBtn;
	private Piano piano;
	private MelodyLibrary melodyLibrary;
	private BarChart<String, Number> popularPianoKeyBarChart;
	private PieChart melodyPlayedPieChart;
	private ObservableList<PieChart.Data> melodyPlayedPieChartData;
	private ComboBox<String> melodyComboBox;
	private ObservableList<String> melodyList;

	// Handles the events tied to refreshing and melody deleting
	EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {

			if (e.getSource() == refreshBtn) {
				updateBarChart();
				updatePieChart();
				updateMelodyComboBox();
			} else if (e.getSource() == deleteMelodyBtn) {
				deleteMelody();
			}
		}

	};


	public TeacherView(Piano piano) {
		this.piano = piano;
		melodyLibrary = piano.getMelodyLibrary();

		refreshBtn = new Button("Refresh");
		refreshBtn.setOnAction(buttonHandler);
		deleteMelodyBtn = new Button("Delete");
		deleteMelodyBtn.setOnAction(buttonHandler);
		deleteMelodyBtn.setId("deleteMelodyBtn");

		popularPianoKeyBarChart =  new BarChart<String,Number>(new CategoryAxis(), new NumberAxis());
		popularPianoKeyBarChart.setTitle("Most Popular Piano Keys");
		popularPianoKeyBarChart.setMaxSize(400, 500);

		melodyPlayedPieChartData = FXCollections.observableArrayList();
		melodyPlayedPieChart = new PieChart(melodyPlayedPieChartData);
		melodyPlayedPieChart.setTitle("Most Popular Played Melodies");
		melodyPlayedPieChart.setMaxSize(400, 600);

		BorderPane actionTeacherBorderPane = new BorderPane();
		actionTeacherBorderPane.setLeft(refreshBtn);
		actionTeacherBorderPane.setId("actionTeacherBorderPane");

		melodyComboBox = new ComboBox<String>();
		melodyList = FXCollections.observableArrayList();
		melodyComboBox.setItems(melodyList);

		Label deleteMelodiesLbl = new Label("Delete Melodies");
		deleteMelodiesLbl.setTranslateY(20);
		deleteMelodiesLbl.setTranslateX(-20);

		actionTeacherBorderPane.setRight(new HBox(deleteMelodiesLbl, melodyComboBox, deleteMelodyBtn));
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(popularPianoKeyBarChart);
		borderPane.setRight(new VBox(melodyPlayedPieChart));
		borderPane.setTop(actionTeacherBorderPane);

		updateBarChart();
		updatePieChart();
		updateMelodyComboBox();

		getChildren().add(borderPane);
		getStyleClass().add("teacher-pane");
	}

	/**
	 * Repopulates the bar chart
	 */
	public void updateBarChart() {
		popularPianoKeyBarChart.getData().clear();

		for (PianoKey keyView : Piano.getPianoKeyMap().values()) {
			XYChart.Series<String, Number> pianoKeyPopularitySeries = new XYChart.Series<String, Number>();
			pianoKeyPopularitySeries.getData().add(new XYChart.Data<String, Number>(keyView.getKeyId(), keyView.getTimesPressed()));
			popularPianoKeyBarChart.getData().add(pianoKeyPopularitySeries);
		}
	}
	
	/**
	 * Repopulates the pie chart
	 */
	public void updatePieChart() {
		melodyPlayedPieChartData.clear();

		for (Melody melody : melodyLibrary.getMelodies()) {
			melodyPlayedPieChartData.add(new PieChart.Data(melody.getMelodyName(), melody.getTimesPlayed()));
		}
	}
	
	/**
	 * Removes a selected melody from the melody list
	 */
	public void deleteMelody() {

		int index = melodyComboBox.getSelectionModel().getSelectedIndex();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Delete Melody");

		if (index >= 0 && melodyComboBox.getItems().get(index) != null) {
			alert.setContentText("Are you sure you wish to delete " + melodyComboBox.getItems().get(index) + " ?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				melodyLibrary.removeMelody(index);
			}
		}


		try {
			MelodyReaderWriter.writeMelodies(melodyLibrary.getMelodies(), "melodies.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateMelodyComboBox();
		piano.updateMelodyList();
	}


	/**
	 * Updates the melody combo box to replicate the currently stored melodies in
	 * the melody list
	 */
	public void updateMelodyComboBox() {

		melodyList.clear();

		for (Melody melody : melodyLibrary.getMelodies()) {
			melodyList.add(melody.getMelodyName());
		}

		if (melodyList.size() > 0) {
			melodyComboBox.getSelectionModel().select(melodyList.get(0));
		}

	}
}
