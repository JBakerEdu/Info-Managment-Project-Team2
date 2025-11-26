package edu.westga.dsdm.project.view.codebehind;

import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.dsdm.project.viewmodel.StudentProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StudentRegistrationPageView {

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
    private Label errorNotValidFirstName;

    @FXML
    private Label errorNotValidLastName;

    @FXML
    private TextField firstNameTextFeild;

    @FXML
    private TextField lastNameTextFeild;

    @FXML
    private PasswordField passwordTextFeild;
    
    private StudentProfile viewModel;

    @FXML
    void handleCreateAccountButtonClick(ActionEvent event) {
    	if (!this.validateFields()) {
    		this.viewModel.printInformation();
    	}
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
    	this.viewModel = new StudentProfile();
    	this.bindFields();
    	
//        assert accountHeader != null : "fx:id=\"accountHeader\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert confirmPasswordTextFeild != null : "fx:id=\"confirmPasswordTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert createAccountSubmitButton != null : "fx:id=\"createAccountSubmitButton\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert emailTextFeild != null : "fx:id=\"emailTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert errorNotConfirmedPassword != null : "fx:id=\"errorNotConfirmedPassword\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert errorNotCorrectPassword != null : "fx:id=\"errorNotCorrectPassword\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert errorNotValidEmail != null : "fx:id=\"errorNotValidEmail\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert errorNotValidUsername != null : "fx:id=\"errorNotValidUsername\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert errorNotValidUsername1 != null : "fx:id=\"errorNotValidUsername1\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert firstNameTextFeild != null : "fx:id=\"firstNameTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert lastNameTextFeild != null : "fx:id=\"lastNameTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
//        assert passwordTextFeild != null : "fx:id=\"passwordTextFeild\" was not injected: check your FXML file 'TeacherRegistrationtPageView.fxml'.";
    }

    private void bindFields() {
    	this.firstNameTextFeild.textProperty().bindBidirectional(this.viewModel.firstNameProperty());
    	this.lastNameTextFeild.textProperty().bindBidirectional(this.viewModel.lastNameProperty());
    	this.emailTextFeild.textProperty().bindBidirectional(this.viewModel.emailProperty());
    	this.passwordTextFeild.textProperty().bindBidirectional(this.viewModel.passwordProperty());
    	this.confirmPasswordTextFeild.textProperty().bindBidirectional(this.viewModel.confirmPasswordProperty());
    }
    
    private boolean validateFields() {
    	if (this.firstNameTextFeild.textProperty().getValue().isEmpty()) {
    		this.errorNotValidFirstName.setVisible(true);
    		return true;
    	} else {
    		this.errorNotValidFirstName.setVisible(false);
    	}
    	
    	if (this.lastNameTextFeild.textProperty().getValue().isEmpty()) {
    		this.errorNotValidLastName.setVisible(true);
    		return true;
    	} else {
    		this.errorNotValidLastName.setVisible(false);
    	}
    	
    	if (this.emailTextFeild.textProperty().getValue().isEmpty()) {
    		this.errorNotValidEmail.setVisible(true);
    		return true;
    	} else {
    		this.errorNotValidEmail.setVisible(false);
    	}
    	
    	if (this.passwordTextFeild.textProperty().getValue().isEmpty() ||
    			this.confirmPasswordTextFeild.textProperty().getValue().isEmpty() ||
    			this.passwordTextFeild.textProperty().getValue() == 
    			this.confirmPasswordTextFeild.textProperty().getValue()) {
    		this.errorNotCorrectPassword.setVisible(true);
    		this.errorNotConfirmedPassword.setVisible(true);
    		return true;
    	} else {
    		this.errorNotCorrectPassword.setVisible(false);
    		this.errorNotConfirmedPassword.setVisible(false);
    	}
    	
    	return false;
    }
    
}
