package edu.westga.dsdm.project.view.codebehind;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.EventManager;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.EventContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialog;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Controller for handling the organizer's event portfolio page.
 * This page allows the organizer to view, add, delete, and navigate events.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class AccountOrganizerPortfolioPageView implements Initializable {

    @FXML private Label accountHeader;
    @FXML private Button addEventsButton;
    @FXML private AnchorPane anchorPane;
    @FXML private Button delete1;
    @FXML private Button delete2;
    @FXML private Button delete3;
    @FXML private Button lastEventsButton;
    @FXML private Button logout;
    @FXML private Button nextEventsButton;
    @FXML private ImageView profileImage;
	@FXML private ImageView searchImage;
    @FXML private TextArea eventDescription1;
    @FXML private TextArea eventDescription2;
    @FXML private TextArea eventDescription3;
    @FXML private TextField eventName1;
    @FXML private TextField eventName2;
    @FXML private TextField eventName3;
    @FXML private Pane eventPane1;
    @FXML private Pane eventPane2;
    @FXML private Pane eventPane3;
    @FXML private Button eventView1;
    @FXML private Button eventView2;
    @FXML private Button eventView3;
    @FXML private Label role;
    @FXML private Label userName;

    private User organizerUser;
    private List<Event> events;
    private int eventsPage = 0;

	/**
     * Initializes the view by loading the user and event data.
     * 
     * This method is automatically called when the view is loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUser();
        this.loadEvents();
        this.updateUI();
    }

	/**
     * Loads the current user (organizer) details from the session or context.
     * If no user is found, redirects to the login page.
     */
    private void loadUser() {
        AccountContext ctx = AccountContext.getInstance();

        if (ctx != null && ctx.hasUserToView()) {
            this.organizerUser = ctx.getUserToView();
        } else {
            this.organizerUser = Session.getInstance().getCurrentUser();
        }

        if (this.organizerUser == null) {
            GuiHelper.switchView(this.anchorPane, Views.LOGIN);
            return;
        }

        this.userName.setText(this.organizerUser.getFirstName() + "'s Events");
        this.accountHeader.setText(this.organizerUser.getFirstName());
    }

	/**
     * Loads the events associated with the current organizer.
     * If an error occurs while fetching events, it displays an error message.
     */
    private void loadEvents() {
        try {
            this.events = EventManager.getEventsForOrganizer(this.organizerUser.getUserId());
        } catch (Exception ex) {
            this.showError("Error loading events", ex.getMessage());
        }
    }

	/**
     * Updates the UI with the events. 
     * This includes populating the UI with event titles and descriptions.
     * It also handles pagination for multiple pages of events.
     */
    private void updateUI() {
        clearPanes();

        if (this.events == null || this.events.isEmpty()) {
            this.eventName1.setText("No events found.");
            this.eventDescription1.setText("");
            return;
        }

        int start = eventsPage * 3;

        setEventIntoPane(0, eventPane1, eventName1, eventDescription1);
        setEventIntoPane(1, eventPane2, eventName2, eventDescription2);
        setEventIntoPane(2, eventPane3, eventName3, eventDescription3);
    }

	/**
     * Clears the event panes by hiding them.
     */
    private void clearPanes() {
        eventPane1.setVisible(false);
        eventPane2.setVisible(false);
        eventPane3.setVisible(false);
    }

	/**
     * Populates the event details into a specific pane.
     * 
     * @param offset the index offset of the event
     * @param pane the pane to display the event in
     * @param nameField the TextField to display the event's title
     * @param descField the TextArea to display the event's description
     */
    private void setEventIntoPane(int offset, Pane pane, TextField nameField, TextArea descField) {
        int index = eventsPage * 3 + offset;

        if (events == null || index >= events.size()) {
            pane.setVisible(false);
            return;
        }

        Event e = events.get(index);

        pane.setVisible(true);
        nameField.setText(e.getTitle());
        descField.setText(e.getDescription() == null ? "" : e.getDescription());
    }

	/**
     * Handles the click event for adding a new event.
     * 
     * Displays a dialog to collect event details from the user and create a new event.
     */
    @FXML
	void handleAddEventsButtonClick(ActionEvent event) {
		javafx.scene.control.Dialog<Event> dialog = new javafx.scene.control.Dialog<>();
		dialog.setTitle("Create New Event");

		ButtonType createButtonType = new ButtonType("Create", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

		TextField titleField = new TextField();
		titleField.setPromptText("Event Title");

		TextField locationField = new TextField();
		locationField.setPromptText("Event Location");

		TextArea descriptionArea = new TextArea();
		descriptionArea.setPromptText("Event Description (optional)");
		descriptionArea.setPrefRowCount(3);

		TextArea rubricArea = new TextArea();
		rubricArea.setPromptText("Rubric (optional)");
		rubricArea.setPrefRowCount(2);

		DatePicker startDate = new DatePicker();
		TextField startTime = new TextField("09:00");

		DatePicker endDate = new DatePicker();
		TextField endTime = new TextField("17:00");

		VBox content = new VBox(10,
				new Label("Title:"), titleField,
				new Label("Location:"), locationField,
				new Label("Description:"), descriptionArea,
				new Label("Rubric:"), rubricArea,
				new Label("Start Date:"), startDate,
				new Label("Start Time (HH:MM):"), startTime,
				new Label("End Date:"), endDate,
				new Label("End Time (HH:MM):"), endTime
		);
		content.setPadding(new Insets(10));

		dialog.getDialogPane().setContent(content);

		dialog.setResultConverter(button -> {

			if (button != createButtonType) {
				return null;
			}

			if (titleField.getText().isBlank() ||
				locationField.getText().isBlank() ||
				startDate.getValue() == null ||
				endDate.getValue() == null) {

				showError("Missing Information", "Title, Location, Start Date, and End Date are required.");
				return null;
			}

			LocalTime st, et;
			try {
				st = LocalTime.parse(startTime.getText());
				et = LocalTime.parse(endTime.getText());
			} catch (Exception ex) {
				showError("Invalid Time", "Times must be in HH:MM format.");
				return null;
			}

			Event newEvent = new Event();
			newEvent.setTitle(titleField.getText());
			newEvent.setEventLocation(locationField.getText());
			newEvent.setDescription(descriptionArea.getText());
			newEvent.setRubric(rubricArea.getText());
			newEvent.setStartDateTime(LocalDateTime.of(startDate.getValue(), st));
			newEvent.setEndDateTime(LocalDateTime.of(endDate.getValue(), et));
			newEvent.setOrganizerUserId(this.organizerUser.getUserId());
			newEvent.setStatus("Scheduled");

			return newEvent;
		});

		Optional<Event> result = dialog.showAndWait();
		if (result.isEmpty()) {
			return;
		}
		Event created = result.get();
		try {
			Event saved = EventManager.createEventForOrganizer(
				created.getOrganizerUserId(),
				created.getTitle(),
				created.getEventLocation(),
				created.getDescription(),
				created.getRubric(),
				created.getStartDateTime(),
				created.getEndDateTime()
			);
			this.events.add(saved);
			this.updateUI();

		} catch (Exception ex) {
			showError("Database Error", ex.getMessage());
		}
	}

	/**
     * Handles the click event to delete an event.
     * 
     * Displays a confirmation dialog before deleting the event.
     */
    @FXML
	void handleDeleteEventsButtonClick(ActionEvent event) {

		Button clicked = (Button) event.getSource();
		int deleteIndex = -1;
		if (clicked == delete1) {
			deleteIndex = eventsPage * 3;
		} else if (clicked == delete2) {
			deleteIndex = eventsPage * 3 + 1;
		} else if (clicked == delete3) {
			deleteIndex = eventsPage * 3 + 2;
		}

		if (deleteIndex < 0 || deleteIndex >= events.size()) {
			showError("Delete Error", "Unable to determine which event to delete.");
			return;
		}

		Event toDelete = events.get(deleteIndex);
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
			"Are you sure you want to delete the event:\n\n" + toDelete.getTitle(),
			ButtonType.YES, ButtonType.NO);

		confirm.setHeaderText("Delete Event?");
		confirm.setTitle("Confirm Delete");

		Optional<ButtonType> result = confirm.showAndWait();
		if (result.isEmpty() || result.get() != ButtonType.YES) {
			return;
		}

		try {
			EventManager.deleteEvent(toDelete.getEventId());
			events.remove(deleteIndex);
			if (eventsPage * 3 >= events.size() && eventsPage > 0) {
				eventsPage--;
			}
			updateUI();
		} catch (Exception ex) {
			showError("Database Error", ex.getMessage());
		}
	}

	/**
     * Navigates the user back to the homepage.
     * This method is triggered when the home button is clicked.
     */
    @FXML
    void handleHomeClick(MouseEvent event) { 
		GuiHelper.switchView(anchorPane, Views.HOMEPAGE);
	}

	/**
     * Handles the "Previous Events" button click.
     * This method decreases the events page counter (if not already on the first page) 
     * and reloads the updated event list for the previous page.
     */
    @FXML
    void handleLastEventsButtonClick(ActionEvent event) {
        if (eventsPage > 0) {
            eventsPage--;
            updateUI();
        }
    }

	/**
     * Logs the current user out and redirects them to the login page.
     * This method is triggered when the logout button is clicked.
     */
    @FXML
    void handleLogoutButtonClick(ActionEvent event) {
        Session.getInstance().logout();
        GuiHelper.switchView(anchorPane, Views.LOGIN);
    }

	/**
     * Handles the "Next Events" button click.
     * This method increases the events page counter if there are more events to show.
     * It ensures that there are enough events remaining to display on the next page.
     */
    @FXML
    void handleNextEventsButtonClick(ActionEvent event) {
        if (events != null && (eventsPage + 1) * 3 < events.size()) {
            eventsPage++;
            updateUI();
        }
    }

	/**
     * Navigates the user to their personal account page based on their role.
     * If the user is an organizer, they are directed to the organizer's page.
     * If the user is not an organizer, they are directed to their general account page.
     * If the user is not logged in, they are redirected to the login page.
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
     * Placeholder method for the search bar functionality.
     * This method is intended to handle user input from a search bar, but the implementation is not provided yet.
     */
    @FXML
    void handleSearchBar(ActionEvent event) { }

	/**
     * Placeholder method for the search button click functionality.
     * This method is meant to handle the event when the search button is clicked, but the implementation is not provided yet.
     */
    @FXML
    void handleSearchButtoneClick(MouseEvent event) { }

	/**
	 * Handles the click event on the "View" buttons for events.
	 * Navigates to the event page displaying the selected event details.
	 * 
	 * @param event The ActionEvent triggered by clicking the "View" button.
	 */
    @FXML
    void handleViewButtonClick(ActionEvent event) { 
		if (Session.getInstance().getCurrentUser() == null) {
			GuiHelper.switchView(this.anchorPane, Views.LOGIN);
		}

		Button source = (Button) event.getSource();
		int index = -1;
		
		if (source == this.eventView1) {
			index = eventsPage * 3;
		} else if (source == this.eventView2) {
			index = eventsPage * 3 + 1;
		} else if (source == this.eventView3) {
			index = eventsPage * 3 + 2;
		}

		if (index >= 0 && index < this.events.size()) {
			EventContext.getInstance().setSelectedEvent(this.events.get(index));
			GuiHelper.switchView(this.anchorPane, Views.EVENT_PAGE);
		} else {
			showError("Error", "Event not found.");
		}
		
	}

	/**
	 * Utility method to display an error dialog with the given title and message.
	 *
	 * @param title the title of the error dialog
	 * @param msg the error message to display
	 */
    private void showError(String title, String msg) {
        Alert a = new Alert(AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(title);
        a.showAndWait();
    }
}
