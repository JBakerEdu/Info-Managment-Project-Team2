package edu.westga.dsdm.project.view.codebehind;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TeacherRegistrationPageView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label accountHeader;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField confirmPasswordTextFeild;

    @FXML
    private Button createAccountSubmitButton;

    @FXML
    private TextField emailTextFeild;

    @FXML
    private Label errorNotConfirmedPassword;

    @FXML
    private Label errorNotCorrectPassword;

    @FXML
    private Label errorNotValidEmail;

    @FXML
    private Label errorNotValidUsername;

    @FXML
    private Label errorNotValidUsername1;

    @FXML
    private PasswordField passwordTextFeild;

    @FXML
    private TextField userNameTextFeild;

    @FXML
    private TextField userNameTextFeild1;

    @FXML
    void handleCreateAccountButtonClick(ActionEvent event) {

    }

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
        assert accountHeader != null : "fx:id=\"accountHeader\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert confirmPasswordTextFeild != null : "fx:id=\"confirmPasswordTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert createAccountSubmitButton != null : "fx:id=\"createAccountSubmitButton\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert emailTextFeild != null : "fx:id=\"emailTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert errorNotConfirmedPassword != null : "fx:id=\"errorNotConfirmedPassword\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert errorNotCorrectPassword != null : "fx:id=\"errorNotCorrectPassword\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert errorNotValidEmail != null : "fx:id=\"errorNotValidEmail\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert errorNotValidUsername != null : "fx:id=\"errorNotValidUsername\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert errorNotValidUsername1 != null : "fx:id=\"errorNotValidUsername1\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert passwordTextFeild != null : "fx:id=\"passwordTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert userNameTextFeild != null : "fx:id=\"userNameTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
        assert userNameTextFeild1 != null : "fx:id=\"userNameTextFeild1\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";

    }

}
