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
        assert accountHeader != null : "fx:id=\"accountHeader\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert btnCreateGroup != null : "fx:id=\"btnCreateGroup\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert btnEditGroup != null : "fx:id=\"btnEditGroup\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert btnEditStudent != null : "fx:id=\"btnEditStudent\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert btnMakeViewComments != null : "fx:id=\"btnMakeViewComments\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert btnRegisterStudent != null : "fx:id=\"btnRegisterStudent\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert labelEmail != null : "fx:id=\"labelEmail\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert labelFullName != null : "fx:id=\"labelFullName\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert labelRole != null : "fx:id=\"labelRole\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";
        assert profilePicture != null : "fx:id=\"profilePicture\" was not injected: check your FXML file 'AccountTeacherProfilePageView.fxml'.";

    }
    
    void registerStudent(MouseEvent event) {
    	//TODO: Loads the Student Registration page.
    }
}
