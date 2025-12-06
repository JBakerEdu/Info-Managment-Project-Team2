package edu.westga.dsdm.project.view.codebehind;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.Project;
import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.EventManager;
import edu.westga.dsdm.project.model.ProjectContext;
import edu.westga.dsdm.project.model.EventContext;
import edu.westga.dsdm.project.model.AccountManager;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.azurepgsql.DBEventOps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * CodeBehind To Handle Processing for the Home Page
 *
 * @author	Jacob Baker
 * @version Fall 2025
 */
public class HomePageView {
	
    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea eventDescription1;

    @FXML
    private TextArea eventDescription2;

    @FXML
    private TextArea eventDescription3;

    @FXML
    private TextArea eventDescription4;

    @FXML
    private TextField eventEdit1;

    @FXML
    private TextField eventEdit2;

    @FXML
    private TextField eventEdit3;

    @FXML
    private TextField eventEdit4;

    @FXML
    private Pane eventPane1;

    @FXML
    private Pane eventPane2;

    @FXML
    private Pane eventPane3;

    @FXML
    private Pane eventPane4;

    @FXML
    private Button eventView1;

    @FXML
    private Button eventView2;

    @FXML
    private Button eventView3;

    @FXML
    private Button eventView4;

    @FXML
    private Button login;


    @FXML
    void handleHomeClick(MouseEvent event) {
        GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handleLoginButtonClick(ActionEvent event) {
        if (Session.getInstance().getCurrentUser() == null) {
            GuiHelper.switchView(this.anchorPane, Views.LOGIN);
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
    void handleSearchBar(ActionEvent event) {
        // Not implemented yet
    }

    @FXML
    void handleSearchButtoneClick(MouseEvent event) {
        // Not implemented yet
    }

    @FXML
	void handleViewButtonClick(ActionEvent event) {
		Button source = (Button) event.getSource();

		int index = -1;
		if (source == this.eventView1) {
			index = 0;
		} else if (source == this.eventView2) {
			index = 1;
		} else if (source == this.eventView3) {
			index = 2;
		}

		if (index >= 0 && this.displayedEvents.size() > index) {
			EventContext.getInstance().setSelectedEvent(this.displayedEvents.get(index));
			GuiHelper.switchView(this.anchorPane, Views.EVENT_PAGE);
		}
	}


    public static List<Event> getAllEvents() throws Exception {
        return EventManager.getAllEvents();
    }

    @FXML
    void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            String username = Session.getInstance().getCurrentUser().getFirstName();
            this.accountHeader.setText(username);
            this.login.setDisable(true);
            this.login.setVisible(false);
        } else {
            this.accountHeader.setText("Account");
        }
        this.loadRandomEventsToHome();
    }

	private List<Event> displayedEvents = new ArrayList<>();

    private void loadRandomEventsToHome() {
		Pane[] panes = {this.eventPane1, this.eventPane2, this.eventPane3};
		TextField[] editors = {this.eventEdit1, this.eventEdit2, this.eventEdit3};
		TextArea[] descriptions = {this.eventDescription1, this.eventDescription2, this.eventDescription3};

		for (int i = 0; i < panes.length; i++) {
			this.setEventSlotVisible(panes[i], false);
			this.clearText(editors[i]);
			this.clearText(descriptions[i]);
		}
		List<Event> allEvents;
		try {
			allEvents = EventManager.getAllEvents();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		if (allEvents == null || allEvents.isEmpty()) {
			return;
		}

		Collections.shuffle(allEvents);
		this.displayedEvents = allEvents;
		int toShow = Math.min(3, allEvents.size());
		for (int i = 0; i < toShow; i++) {
			Event e = allEvents.get(i);
			this.setEventSlotVisible(panes[i], true);
			editors[i].setText(e.getTitle());
			descriptions[i].setText(this.getSafeEventSummary(e));
		}
		this.setEventSlotVisible(this.eventPane4, false);
	}




    private String getSafeEventSummary(Event event) {
        if (event == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        if (event.getDescription() != null && !event.getDescription().isBlank()) {
            sb.append(event.getDescription());
        } else {
            sb.append("(No description)");
        }

        if (event.getEventLocation() != null && !event.getEventLocation().isBlank()) {
            sb.append("\nLocation: ").append(event.getEventLocation());
        }

        if (event.getStartDateTime() != null) {
            sb.append("\nStarts: ").append(event.getStartDateTime());
        }
        if (event.getEndDateTime() != null) {
            sb.append("\nEnds: ").append(event.getEndDateTime());
        }

        return sb.toString();
    }

    private void setEventSlotVisible(Pane pane, boolean visible) {
        if (pane != null) {
            pane.setVisible(visible);
        }
    }

    private void clearText(javafx.scene.control.TextInputControl control) {
        if (control != null) {
            control.clear();
        }
    }
}