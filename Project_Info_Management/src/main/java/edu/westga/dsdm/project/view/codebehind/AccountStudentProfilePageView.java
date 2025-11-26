package edu.westga.dsdm.project.view.codebehind;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private Label labelEmail;

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelRole;

    @FXML
    private ListView<?> listStudentProjects;

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
        assert accountHeader != null : "fx:id=\"accountHeader\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert btnViewProject != null : "fx:id=\"btnViewProject\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert labelEmail != null : "fx:id=\"labelEmail\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert labelFullName != null : "fx:id=\"labelFullName\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert labelRole != null : "fx:id=\"labelRole\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert listStudentProjects != null : "fx:id=\"listStudentProjects\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        assert profilePicture != null : "fx:id=\"profilePicture\" was not injected: check your FXML file 'AccountStudentProfilePageView.fxml'.";
        
        this.loadStudentProjects();
    }
    
    void loadStudentProjects() {
    	//TODO: Load student projects they may be associated with. No need to implement the view button.
    }
}
