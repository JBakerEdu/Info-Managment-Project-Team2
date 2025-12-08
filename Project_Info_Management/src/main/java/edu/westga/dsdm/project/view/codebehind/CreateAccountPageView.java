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
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * CodeBehind To Handle Processing for the Create Account Page
 *
 * @author	Kate Anglin and Jacob
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
    private Label errorNotValidFirstName;

	@FXML
	private Label errorNotValidLastName;

	@FXML
	private Label errorNotValidRole;
	
	@FXML
    private Label accountHeader;

    @FXML
    private PasswordField passwordTextFeild;

    @FXML
    private TextField firstNameTextFeild;

	@FXML
	private TextField lastNameTextFeild;

	@FXML
	private ComboBox<String> roleComboBox;
	
	@FXML
	private Button createAccountSubmitButton;

    @FXML
	void handleCreateAccountButtonClick(ActionEvent event) {
		this.hideAllErrors();

		if (!this.validateCreateAccountInputs()) {
			return;
		}

		String firstname = this.firstNameTextFeild.getText();
		String lastname = this.lastNameTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		String email = this.emailTextFeild.getText();
		String role = this.roleComboBox.getValue().toLowerCase();

		try {
			User user = AccountManager.createAccount(firstname, lastname, email, password, role);
			if (user != null) {
				User newUser = AccountManager.validateLogin(email, password);
				Session.getInstance().login(newUser);
				AccountContext.getInstance().setUserToView(newUser);
				//GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
				if ("organizer".equalsIgnoreCase(role)) {
					GuiHelper.switchView(this.anchorPane, Views.ORGANIZER);
				}else if ("Teacher".equalsIgnoreCase(role)) {
					GuiHelper.switchView(this.anchorPane, Views.TEACHER_PROFILE);
				} else if ("Student".equalsIgnoreCase(role)) {
					GuiHelper.switchView(this.anchorPane, Views.STUDENT_PROFILE);
				} else {
					GuiHelper.switchView(this.anchorPane, Views.ACCOUNT);
				}
			} else {
				this.errorNotValidEmail.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.showAlert("Error", "An unexpected error occurred: " + e.getMessage());
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

		String firstname = this.firstNameTextFeild.getText();
		String lastname = this.lastNameTextFeild.getText();
		String password = this.passwordTextFeild.getText();
		String confirmPassword = this.confirmPasswordTextFeild.getText();
		String email = this.emailTextFeild.getText();
		String role = this.roleComboBox.getValue().toLowerCase();

		if (firstname.isEmpty()) {
			this.errorNotValidFirstName.setVisible(true);
			isValid = false;
		}

		if (lastname.isEmpty()) {
			this.errorNotValidLastName.setVisible(true);
			isValid = false;
		}

		if (!password.equals(confirmPassword)) {
			this.errorNotConfirmedPassword.setVisible(true);
			isValid = false;
		}

		if (!email.contains("@") || !email.contains(".")) {
			this.errorNotValidEmail.setVisible(true);
			isValid = false;
		}

		if (role.isEmpty()) {
			this.errorNotValidRole.setVisible(true);
			isValid = false;
		}

		try {
			if (AccountManager.findUserByEmail(email) != null) {
				this.errorNotValidEmail.setVisible(true);
				isValid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.showAlert("Error", "An unexpected error occurred: " + e.getMessage());
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
		User currentUser = Session.getInstance().getCurrentUser();

		if (currentUser != null) {
			String role = currentUser.getRole();

			if ("organizer".equalsIgnoreCase(role)) {
				GuiHelper.switchView(this.anchorPane, Views.ORGANIZER);
			}else if ("Teacher".equalsIgnoreCase(role)) {
				GuiHelper.switchView(this.anchorPane, Views.TEACHER_PROFILE);
			} else if ("Student".equalsIgnoreCase(role)) {
				GuiHelper.switchView(this.anchorPane, Views.STUDENT_PROFILE);
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
			String firstName = Session.getInstance().getCurrentUser().getFirstName();
			this.accountHeader.setText(firstName);
		} else {
			this.accountHeader.setText("Account");
		}
		this.errorNotConfirmedPassword.setVisible(false);
		this.errorNotCorrectPassword.setVisible(false);
		this.errorNotValidEmail.setVisible(false);
		this.errorNotValidFirstName.setVisible(false);
		this.errorNotValidLastName.setVisible(false);
		this.errorNotValidRole.setVisible(false);
		this.createAccountSubmitButton.setDisable(true);
		
		this.firstNameTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
			this.checkFieldsAndToggleButton();
		});

		this.lastNameTextFeild.textProperty().addListener((observable, oldValue, newValue) -> {
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

		this.roleComboBox.getItems().addAll("Organizer", "Volunteer", "Judge", "Teacher", "Student");
		this.roleComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
			this.checkFieldsAndToggleButton();
		});
	}

	private void checkFieldsAndToggleButton() {
		boolean allFieldsFilled = !this.firstNameTextFeild.getText().trim().isEmpty()
				&& !this.lastNameTextFeild.getText().trim().isEmpty()
				&& !this.passwordTextFeild.getText().trim().isEmpty()
				&& !this.confirmPasswordTextFeild.getText().trim().isEmpty()
				&& !this.emailTextFeild.getText().trim().isEmpty()
				&& this.roleComboBox.getValue() != null;
		this.createAccountSubmitButton.setDisable(!allFieldsFilled);
	}

    private void hideAllErrors() {
        this.errorNotConfirmedPassword.setVisible(false);
        this.errorNotCorrectPassword.setVisible(false);
        this.errorNotValidEmail.setVisible(false);
        this.errorNotValidFirstName.setVisible(false);
		this.errorNotValidLastName.setVisible(false);
    }

	private void showAlert(String title, String message) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
