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

import edu.westga.dsdm.project.azurepgsql.DBUserOps;
import edu.westga.dsdm.project.azurepgsql.DBEventOps;
import edu.westga.dsdm.project.model.AccountManager;
import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.Event;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.Project;
import edu.westga.dsdm.project.model.ProjectManager;
import edu.westga.dsdm.project.model.ProjectContext;

public class AccountTeacherProfilePageView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnCreateGroup;

    @FXML
    private Button btnEditGroup;

    @FXML
    private Button btnEditStudent;

    @FXML
    private Button btnMakeViewComments;

    @FXML
    private Button btnRegisterStudent;

    @FXML
    private Button deleteUser;

    @FXML
    private Button logout;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelRole;

    @FXML
    private ListView<Event> listEvents;

    @FXML
    private ListView<User> listStudents;

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

    @FXML
    void registerStudentToEvent(ActionEvent event) {
        try {
            DBEventOps.registerStudentToEvent(this.user,this.selectedEvent, this.seletedStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.selectedEvent = null;
        this.seletedStudent = null;
    }

    private User user;
    private User seletedStudent;
    private Event selectedEvent;

    @FXML
    void initialize() {
        this.initUserNames();
        this.selectionListener();
        this.loadStudents();
        this.loadEvents();
    }

    private void selectionListener() {
        this.listStudents.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.seletedStudent = newValue;
                    this.registerStudentToEvent();
                }
        );

        this.listEvents.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.selectedEvent = newValue;
                    this.registerStudentToEvent();
                }
        );
    }

    private void registerStudentToEvent() {
        if (this.seletedStudent != null && this.selectedEvent != null) {
            this.btnRegisterStudent.setDisable(this.seletedStudent == null && this.selectedEvent == null);
        }
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

    @FXML
    void handleLogoutButtonClick(ActionEvent event) {
        Session.getInstance().logout();
        GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    void registerStudent(MouseEvent event) {
    	//TODO: Loads the Student Registration page.
    }

    void loadStudents() {
        try {
            List<User> studentList = DBUserOps.getAllUsers();

            for (User user : studentList) {
                if (user.getRole().equalsIgnoreCase("student")) {
                    this.listStudents.getItems().add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadEvents() {
        try {
            List<Event> eventList = DBEventOps.findAllEvents();

            for (Event event : eventList) {
                //String eventName = event.getTitle();
                this.listEvents.getItems().add(event);
            }
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
