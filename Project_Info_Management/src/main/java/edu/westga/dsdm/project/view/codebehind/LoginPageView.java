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
 * @author	Jacob Baker
 * @version Spring 2025
 */
public class LoginPageView {

    @FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Label accountHeader;

    @FXML
    private Label errorNotCorrectPassword;

    @FXML
    private Label errorNotValidUsername;
	
	@FXML
	private Button loginSubmitButton;

    @FXML
    private PasswordField passwordTextFeild;

    @FXML
    private TextField userNameTextFeild;

    @FXML
    void handleHomeClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
	void handleLoginButtonClick(ActionEvent event) {
		this.hideAllErrors();
		String enteredValue = this.userNameTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		User user = AccountManager.findUserByUsernameOrEmail(enteredValue);
		if (user == null) {
			this.errorNotValidUsername.setVisible(true);
			return;
		}

		if (!user.getPassword().equals(password)) {
			this.errorNotCorrectPassword.setVisible(true);
			return;
		}
		Session.getInstance().login(user);
		AccountContext.getInstance().setUserToView(user);
		GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
	}

    @FXML
    void handleNoAccountClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.CREATE_ACCOUNT);
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
		this.hideAllErrors();
		this.loginSubmitButton.setDisable(true);

		this.userNameTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleLoginButton();
		});

		this.passwordTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleLoginButton();
		});
	}
	
	private void hideAllErrors() {
        this.errorNotCorrectPassword.setVisible(false);
        this.errorNotValidUsername.setVisible(false);
    }
	
	private void checkFieldsAndToggleLoginButton() {
		boolean fieldsFilled = !this.userNameTextFeild.getText().trim().isEmpty() && !this.passwordTextFeild.getText().trim().isEmpty();
		this.loginSubmitButton.setDisable(!fieldsFilled);
	}
}
