package edu.westga.dsdm.project.view.codebehind;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import edu.westga.dsdm.project.azurepgsql.DBEventOps;
import edu.westga.dsdm.project.model.AccountManager;
import edu.westga.dsdm.project.model.EventManager;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.Project;

public class AccountStudentProfilePageView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnViewProject;

    @FXML
    private Button logout;

    @FXML
    private Button deleteUser;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelRole;

    @FXML
    private ListView<Event> listStudentProjects;

    @FXML
    private ImageView profileImage;

    @FXML
    private ImageView profilePicture;

    @FXML
    void handleHomeClick(MouseEvent event) {

    }

    @FXML
    void handlePersonalAccountClick(MouseEvent event) {

    }

    @FXML
    void handleSearchBar(ActionEvent event) {

    }

    @FXML
    void handleSearchButtoneClick(MouseEvent event) {

    }

    private User user;

    @FXML
    void initialize() {
        this.initUserNames();
        this.loadStudentProjects();
    }

    private void initUserNames() {
        if (Session.getInstance().getCurrentUser() != null) {
            String firstName = Session.getInstance().getCurrentUser().getFirstName();
            this.accountHeader.setText(firstName);

            if (AccountContext.getInstance().hasUserToView()) {
                user = AccountContext.getInstance().getUserToView();
                this.labelFullName.setText(user.getFirstName() + " " + user.getLastName());
                this.labelEmail.setText(user.getEmail());
                this.labelRole.setText(user.getRole());
            } else {
                this.labelFullName.setText("Error: Select Another User");
            }
        } else {
            GuiHelper.switchView(this.anchorPane, Views.LOGIN);
            this.accountHeader.setText("Account");
        }
    }

    void loadStudentProjects() {
        try {
            List<Integer> allRegisteredEvents = DBEventOps.getRegisteredEvents(user.getUserId());
            List<Event> allEvents = DBEventOps.findAllEvents();

            for (Event event : allEvents) {
                for (int eventId : allRegisteredEvents) {
                    if (eventId == event.getEventId()) {
                        this.listStudentProjects.getItems().add(event);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutButtonClick(ActionEvent event) {
        try {
            Session.getInstance().logout();
            GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteUserButtonClick(ActionEvent event) {
        AccountManager.deleteUser(user.getUserId());
        Session.getInstance().logout();
        GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }
}
