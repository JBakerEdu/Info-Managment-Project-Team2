package edu.westga.dsdm.project.view.codebehind;

import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.EventContext;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.time.format.DateTimeFormatter;

public class EventPageView {

    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField endDateTextField;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private Label organizersName;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label projectTitleLabel;

    @FXML
    private TextArea rubricTextArea;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField startTimeTextField;

    private Event selectedEvent;

    @FXML
    void initialize() {
        this.selectedEvent = EventContext.getInstance().getSelectedEvent();

        if (selectedEvent != null) {
            populateEventDetails();
        } else {
            showError("Error", "No event selected.");
        }
    }

    private void populateEventDetails() {
        projectTitleLabel.setText(selectedEvent.getTitle());
        descriptionTextArea.setText(selectedEvent.getDescription() != null ? selectedEvent.getDescription() : "No description available");
        rubricTextArea.setText(selectedEvent.getRubric() != null ? selectedEvent.getRubric() : "No rubric provided");
        locationTextField.setText(selectedEvent.getEventLocation() != null ? selectedEvent.getEventLocation() : "Location not provided");


		//TODO I need to make time work and be able to get the user name not just ID number
		
        organizersName.setText("Organizer: " + (selectedEvent.getOrganizerUserId() != 0 ? selectedEvent.getUserId() : "Not available"));
    }

    private void showError(String title, String message) {
        System.out.println(title + ": " + message);
    }

    @FXML
    void handleHomeClick(MouseEvent event) {
        GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handlePersonalAccountClick(MouseEvent event) {
        GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
    }

    @FXML
    void handleSearchBar(ActionEvent event) {
        // Not implemented yet
    }

    @FXML
    void handleSearchButtoneClick(MouseEvent event) {
        // Not implemented yet
    }
}
