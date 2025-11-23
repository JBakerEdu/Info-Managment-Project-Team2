package edu.westga.dsdm.project.view.codebehind;

import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.AccountManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * CodeBehind To Handle Processing for the Create Account Page
 *
 * @author	Jacob Baker
 * @version Spring 2025
 */
public class CreateAccountPageView {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField confirmPasswordTextFeild;

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
    private Label accountHeader;

    @FXML
    private PasswordField passwordTextFeild;

    @FXML
    private TextField userNameTextFeild;
	
	@FXML
	private Button createAccountSubmitButton;

    @FXML
	void handleCreateAccountButtonClick(ActionEvent event) {
		this.hideAllErrors();

		if (!this.validateCreateAccountInputs()) {
			return;
		}

		String username = this.userNameTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		String email = this.emailTextFeild.getText();

		boolean success = AccountManager.createAccount(username, password, email);
		if (success) {
			User newUser = AccountManager.validateLogin(username, password);
			Session.getInstance().login(newUser);
			AccountContext.getInstance().setUserToView(newUser);
			GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
		} else {
			this.errorNotValidUsername.setVisible(true);
		}
	}

	/**
	* Validates user inputs for creating an account.
	* Shows all appropriate error messages.
	*
	* @return true if inputs are valid, false if there are errors
	*/
	private boolean validateCreateAccountInputs() {
		boolean isValid = true;

		String username = this.userNameTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		String confirmPassword = this.confirmPasswordTextFeild.getText();
		String email = this.emailTextFeild.getText();

		if (!password.equals(confirmPassword)) {
			this.errorNotConfirmedPassword.setVisible(true);
			isValid = false;
		}

		if (!email.contains("@") || !email.contains(".")) {
			this.errorNotValidEmail.setVisible(true);
			isValid = false;
		}

		if (AccountManager.findUserByUsername(username) != null) {
			this.errorNotValidUsername.setVisible(true);
			isValid = false;
		}

		if (AccountManager.findUserByEmail(email) != null) {
			this.errorNotValidEmail.setVisible(true);
			isValid = false;
		}
		
		return isValid;
	}

    @FXML
    void handleHasAccountClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.LOGIN);
    }

    @FXML
    void handleHomeClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handlePersonalAccountClick(MouseEvent event) {
		if (Session.getInstance().getCurrentUser() != null) {
			GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
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
	void initialize() {
		if (Session.getInstance().getCurrentUser() != null) {
			String username = Session.getInstance().getCurrentUser().getUsername();
			this.accountHeader.setText(username);
		} else {
			this.accountHeader.setText("Account");
		}
		this.errorNotConfirmedPassword.setVisible(false);
		this.errorNotCorrectPassword.setVisible(false);
		this.errorNotValidEmail.setVisible(false);
		this.errorNotValidUsername.setVisible(false);
		this.createAccountSubmitButton.setDisable(true);
		
		this.userNameTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleButton();
		});

		this.passwordTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleButton();
		});

		this.confirmPasswordTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleButton();
		});

		this.emailTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleButton();
		});
	}

	private void checkFieldsAndToggleButton() {
		boolean allFieldsFilled = !this.userNameTextFeild.getText().trim().isEmpty()
			&& !this.passwordTextFeild.getText().trim().isEmpty()
			&& !this.confirmPasswordTextFeild.getText().trim().isEmpty()
			&& !this.emailTextFeild.getText().trim().isEmpty();
		this.createAccountSubmitButton.setDisable(!allFieldsFilled);
	}

    private void hideAllErrors() {
        this.errorNotConfirmedPassword.setVisible(false);
        this.errorNotCorrectPassword.setVisible(false);
        this.errorNotValidEmail.setVisible(false);
        this.errorNotValidUsername.setVisible(false);
    }
}
