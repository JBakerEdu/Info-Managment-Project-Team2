package edu.westga.dsdm.project.view.codebehind;

import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.EventContext;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.azurepgsql.DBUserOps;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * EventPageView class handles the UI logic for displaying event details.
 * It populates the event information such as description, rubric, start and end dates, 
 * and the organizer's name.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class EventPageView {

    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField endDateTextFeild;

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
    private TextField startDateTextFeild;

    private Event selectedEvent;

    /**
     * Initializes the event page view by fetching the selected event from the EventContext.
     * If an event is selected, its details are populated in the UI.
     * Otherwise, an error message is displayed.
     */
    @FXML
    void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            String username = Session.getInstance().getCurrentUser().getFirstName();
            this.accountHeader.setText(username);
        } else {
            this.accountHeader.setText("Account");
        }

        this.selectedEvent = EventContext.getInstance().getSelectedEvent();

        if (selectedEvent != null) {
            populateEventDetails();
        } else {
            showError("Error", "No event selected.");
        }
    }

    /**
     * Populates the event details in the UI.
     * It sets the event title, description, rubric, location, start and end dates, and organizer.
     * 
     * @param selectedEvent The event to be displayed in the UI.
     */
    private void populateEventDetails() {
        this.projectTitleLabel.setText(selectedEvent.getTitle());
        this.descriptionTextArea.setText(selectedEvent.getDescription() != null ? selectedEvent.getDescription() : "No description available");
        this.rubricTextArea.setText(selectedEvent.getRubric() != null ? selectedEvent.getRubric() : "No rubric provided");
        this.locationTextField.setText(selectedEvent.getEventLocation() != null ? selectedEvent.getEventLocation() : "Location not provided");
        
       if (selectedEvent.getStartDateTime() != null) {
            String startDate = "Starts: " + formatDateTime(selectedEvent.getStartDateTime());
            this.startDateTextFeild.setText(startDate);
        } else {
            this.startDateTextFeild.setText("Start date has not been added.");
        }

        if (selectedEvent.getEndDateTime() != null) {
            String endDate = "Ends: " + formatDateTime(selectedEvent.getEndDateTime());
            this.endDateTextFeild.setText(endDate);
        } else {
            this.endDateTextFeild.setText("End date has not been added.");
        }

        try {
            User organizer = DBUserOps.findUserByIdIterative(selectedEvent.getOrganizerUserId());

            if (organizer != null) {
                organizersName.setText(
                    "Organizer: " + organizer.getFirstName() + " " + organizer.getLastName()
                );
            } else {
                organizersName.setText("Organizer: Unknown");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            organizersName.setText("Organizer: Error loading");
        }
    }

     /**
     * Formats a given LocalDateTime object into a readable string.
     * The format is "MM/dd/yyyy HH:mm".
     * 
     * @param dateTime The LocalDateTime object to format.
     * @return A formatted string representing the date-time.
     */
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    /**
     * Displays an error message in the console.
     * 
     * @param title The title of the error.
     * @param message The error message to display.
     */
    private void showError(String title, String message) {
        System.out.println(title + ": " + message);
    }

    /**
     * Handles the click event on the "Home" button.
     * Switches the view to the homepage.
     * 
     * @param event The MouseEvent triggered when the "Home" button is clicked.
     */
    @FXML
    void handleHomeClick(MouseEvent event) {
        GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    /**
     * Handles the click event on the "Personal Account" button.
     * Switches the view to the account page.
     * 
     * @param event The MouseEvent triggered when the "Personal Account" button is clicked.
     */
    @FXML
    void handlePersonalAccountClick(MouseEvent event) {
        User currentUser = Session.getInstance().getCurrentUser();

        if (currentUser != null) {
            String role = currentUser.getRole();
            if ("organizer".equalsIgnoreCase(role)) {
                GuiHelper.switchView(this.anchorPane, Views.ORGANIZER);
            } else {
                GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
            }
        } else {
            GuiHelper.switchView(this.anchorPane, Views.LOGIN);
        }
    }

    /**
     * Handles the search bar action. Not implemented yet.
     * 
     * @param event The ActionEvent triggered by the search bar.
     */
    @FXML
    void handleSearchBar(ActionEvent event) {
        // Not implemented yet
    }

    /**
     * Handles the search button click. Not implemented yet.
     * 
     * @param event The MouseEvent triggered when the search button is clicked.
     */
    @FXML
    void handleSearchButtoneClick(MouseEvent event) {
        // Not implemented yet
    }
}
