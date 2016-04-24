package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.TeacherView;

/**
 * The PianoApp class represents the main starting point of the Piano Application
 *
 */
public class PianoApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	Scene scene;
	TabPane root;
	Tab tab1, tab2;
	BorderPane pane;
	MelodyLibrary melodyLibrary;
	Piano piano;

	public void start(Stage stage) throws Exception {
		stage.setTitle("Software Architectures – Lewis Briffa");
		root = new TabPane();
		scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		
		melodyLibrary = new MelodyLibrary();
		piano = new Piano(melodyLibrary);
		
		tab1 = new Tab();
		tab1.setText("Piano");
		tab1.setClosable(false);
		tab1.setContent(piano);
		root.getTabs().add(tab1);

		tab2 = new Tab();
		tab2.setText("Teacher");
		tab2.setContent(new TeacherView(piano));
		tab2.setClosable(false);
		root.getTabs().add(tab2);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
			
		});
		
		scene.getStylesheets().add(PianoApp.class.getResource("../resources/style.css").toExternalForm());
		stage.show();
	}
}
