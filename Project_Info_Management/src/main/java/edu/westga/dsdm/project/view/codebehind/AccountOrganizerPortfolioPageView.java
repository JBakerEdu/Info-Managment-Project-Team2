package edu.westga.dsdm.project.view.codebehind;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.EventManager;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;

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
    @FXML private TextArea projectDescription1;
    @FXML private TextArea projectDescription2;
    @FXML private TextArea projectDescription3;
    @FXML private TextField projectName1;
    @FXML private TextField projectName2;
    @FXML private TextField projectName3;
    @FXML private Pane projectPane1;
    @FXML private Pane projectPane2;
    @FXML private Pane projectPane3;
    @FXML private Button projectView1;
    @FXML private Button projectView2;
    @FXML private Button projectView3;
    @FXML private Label role;
    @FXML private Label userName;

    private User organizerUser;
    private List<Event> events;
    private int eventsPage = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadUser();
        this.loadEvents();
        this.updateUI();
    }

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

    private void loadEvents() {
        try {
            this.events = EventManager.getEventsForOrganizer(this.organizerUser.getUserId());
        } catch (Exception ex) {
            this.showError("Error loading events", ex.getMessage());
        }
    }

    private void updateUI() {
        clearPanes();

        if (this.events == null || this.events.isEmpty()) {
            this.projectName1.setText("No events found.");
            this.projectDescription1.setText("");
            return;
        }

        int start = eventsPage * 3;

        setEventIntoPane(0, projectPane1, projectName1, projectDescription1);
        setEventIntoPane(1, projectPane2, projectName2, projectDescription2);
        setEventIntoPane(2, projectPane3, projectName3, projectDescription3);
    }

    private void clearPanes() {
        projectPane1.setVisible(false);
        projectPane2.setVisible(false);
        projectPane3.setVisible(false);
    }

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

    @FXML
    void handleHomeClick(MouseEvent event) { 
		GuiHelper.switchView(anchorPane, Views.HOMEPAGE);
	}

    @FXML
    void handleLastEventsButtonClick(ActionEvent event) {
        if (eventsPage > 0) {
            eventsPage--;
            updateUI();
        }
    }

    @FXML
    void handleLogoutButtonClick(ActionEvent event) {
        Session.getInstance().logout();
        GuiHelper.switchView(anchorPane, Views.LOGIN);
    }

    @FXML
    void handleNextEventsButtonClick(ActionEvent event) {
        if (events != null && (eventsPage + 1) * 3 < events.size()) {
            eventsPage++;
            updateUI();
        }
    }

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

    @FXML
    void handleSearchBar(ActionEvent event) { }

    @FXML
    void handleSearchButtoneClick(MouseEvent event) { }

    @FXML
    void handleViewButtonClick(ActionEvent event) { }

    private void showError(String title, String msg) {
        Alert a = new Alert(AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(title);
        a.showAndWait();
    }
}
