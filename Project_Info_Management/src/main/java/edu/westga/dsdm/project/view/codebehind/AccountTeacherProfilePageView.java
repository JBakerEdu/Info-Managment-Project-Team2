package edu.westga.dsdm.project.view.codebehind;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
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
    private Button logout;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelRole;

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
    void initialize() {
        this.initUserNames();
    }

    private void initUserNames() {
        if (Session.getInstance().getCurrentUser() != null) {
            String firstName = Session.getInstance().getCurrentUser().getFirstName();
            this.accountHeader.setText(firstName);

            if (AccountContext.getInstance().hasUserToView()) {
                User viewed = AccountContext.getInstance().getUserToView();
                this.labelFullName.setText(viewed.getFirstName() + " " + viewed.getLastName());
                this.labelEmail.setText(viewed.getEmail());
                this.labelRole.setText(viewed.getRole());
                //this.loadUserProjects(viewed);
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
}
