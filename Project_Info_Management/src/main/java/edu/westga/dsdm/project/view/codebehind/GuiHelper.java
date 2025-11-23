package edu.westga.dsdm.project.view.codebehind;

import java.io.IOException;

import edu.westga.dsdm.project.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Helper class for methods that all the codebehind classes will need
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public class GuiHelper {
	
	/**
	 * Switch the current view to a different one
	 * 
	 * @param anchorPane the anchor pane of the current view
	 * @param view the enum of the view you want to switch to
	 * @throws IOException if file is not found (be sure to use "view/" in front of the file name)
	 */
	public static void switchView(AnchorPane anchorPane, Views view) {
		try {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(view.location()));
			loader.load();
			Parent parent = loader.getRoot();
			Scene scene = new Scene(parent);
			stage.setTitle(Main.getWindowTitle());
			stage.setScene(scene);
		} catch (IOException error) {
			displayError("Display Switch Error", error.getMessage());
		}
	}
	
	/**
	 * Display an error modal window
	 *
	 * @param headerTxt the text you want for the header of the window
	 * @param contextTxt the text you want in the main window
	 */
	public static void displayError(String headerTxt, String contextTxt) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(headerTxt);
		alert.setContentText(contextTxt);
		alert.showAndWait();
	}

}