package edu.westga.dsdm.project.view.codebehind;

import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.AccountManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * CodeBehind To Handle Processing for the Login Page
 *
 * @author	Kate Anglin and Jacob
 * @version Fall 2025
 */
public class LoginPageView {

    @FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Label accountHeader;

    @FXML
    private Label errorNotCorrectPassword;

    @FXML
    private Label errorNotValidEmail;
	
	@FXML
	private Button loginSubmitButton;

    @FXML
    private PasswordField passwordTextFeild;

    @FXML
    private TextField emailTextFeild;

    @FXML
    void handleHomeClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
	void handleLoginButtonClick(ActionEvent event) {
		this.hideAllErrors();
		String email = this.emailTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		try {
			User user = AccountManager.validateLogin(email, password);

			if (user == null) {
				User exists = AccountManager.findUserByEmail(email);
				if (exists == null) {
					this.errorNotValidEmail.setVisible(true);
				} else {
					this.errorNotCorrectPassword.setVisible(true);
				}
				return;
			}
			Session.getInstance().login(user);
			AccountContext.getInstance().setUserToView(user);
			String role = user.getRole();

			if ("organizer".equalsIgnoreCase(role)) {
				GuiHelper.switchView(this.anchorPane, Views.ORGANIZER);
			} else {
				GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.errorNotValidEmail.setText("Server error — try again.");
			this.errorNotValidEmail.setVisible(true);
		}
	}

    @FXML
    void handleNoAccountClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.CREATE_ACCOUNT);
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
	void initialize() {
		if (Session.getInstance().getCurrentUser() != null) {
			String email = Session.getInstance().getCurrentUser().getEmail();
			this.accountHeader.setText(email);
		} else {
			this.accountHeader.setText("Account");
		}
		this.hideAllErrors();
		this.loginSubmitButton.setDisable(true);

		this.emailTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleLoginButton();
		});

		this.passwordTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleLoginButton();
		});
	}
	
	private void hideAllErrors() {
        this.errorNotCorrectPassword.setVisible(false);
        this.errorNotValidEmail.setVisible(false);
    }
	
	private void checkFieldsAndToggleLoginButton() {
		boolean fieldsFilled = !this.emailTextFeild.getText().trim().isEmpty() && !this.passwordTextFeild.getText().trim().isEmpty();
		this.loginSubmitButton.setDisable(!fieldsFilled);
	}
}