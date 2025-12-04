package edu.westga.dsdm.project.view.codebehind;

import edu.westga.dsdm.project.model.Session;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.AccountContext;
import edu.westga.dsdm.project.model.Project;
import edu.westga.dsdm.project.model.ProjectManager;
import edu.westga.dsdm.project.model.ProjectContext;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * CodeBehind To Handle Processing for the Accounts Portfolio Page
 *
 * @author	Jacob Baker
 * @version Spring 2025
 */
public class AccountPortfolioPageView {
	private List<Project> currentProjects;
	private int currentPage;
	private final int projectsPerPage = 3;
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Label accountHeader;

    @FXML
    private Button addProjectsButton;
	
	@FXML
    private Button save;

    @FXML
    private Button delete1;

    @FXML
    private Button delete2;

    @FXML
    private Button delete3;

    @FXML
    private Button edit;
	
	@FXML
    private Button cancel;
	
	@FXML
    private Button logout;

    @FXML
    private Button lastProjectsButton;

    @FXML
    private Button nextProjectsButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private ImageView profilePicture;
	
	@FXML
    private TextArea userDescription;

    @FXML
    private TextArea projectDescription1;

    @FXML
    private TextArea projectDescription2;

    @FXML
    private TextArea projectDescription3;

    @FXML
    private TextField projectEdit1;

    @FXML
    private TextField projectEdit2;

    @FXML
    private TextField projectEdit3;

    @FXML
    private ImageView projectImage1;

    @FXML
    private ImageView projectImage2;

    @FXML
    private ImageView projectImage3;

    @FXML
    private Pane projectPane1;

    @FXML
    private Pane projectPane2;

    @FXML
    private Pane projectPane3;

    @FXML
    private Button projectView1;

    @FXML
    private Button projectView2;

    @FXML
    private Button projectView3;

    @FXML
    private Label userName;

    @FXML
    void handleAddProjectButtonClick(ActionEvent event) {
		if (Session.getInstance().getCurrentUser() == null) {
			GuiHelper.switchView(this.anchorPane, Views.LOGIN);
			return;
		}

		String defaultName = "New Project";
		String defaultDescription = "Project Description Here";
		String defaultLink = "";

		this.loadUserProjects(Session.getInstance().getCurrentUser());
    }

    @FXML
    void handleDeleteProjectsButtonClick(ActionEvent event) {
		Object source = event.getSource();
		int indexOffset = 0;
		if (source == this.delete1) {
			indexOffset = 0;
		} else if (source == this.delete2) {
			indexOffset = 1;
		} else if (source == this.delete3) {
			indexOffset = 2;
		} else {
			return;
		}
		int projectIndex = (this.currentPage * this.projectsPerPage) + indexOffset;

		if (projectIndex < this.currentProjects.size()) {
			boolean confirmed = this.confirmDeletion();
			if (confirmed) {
				Project projectToDelete = this.currentProjects.get(projectIndex);
				//Session.getInstance().getCurrentUser().getProjectManager().removeProject(projectToDelete);
				this.loadUserProjects(Session.getInstance().getCurrentUser());
				this.showAlert("Deleted", "The project has been deleted successfully.");
			}
		}
    }
	
	private void showAlert(String title, String message) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean confirmDeletion() {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Are you sure you want to delete this project?");
		alert.setContentText("This action cannot be undone.");
		return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
	}

    @FXML
    void handleEditButtonClick(ActionEvent event) {
		this.switichEditMode(true);
		
    }
	
	@FXML
    void handleSaveButtonClick(ActionEvent event) {
		//Session.getInstance().getCurrentUser().setDescription(this.userDescription.getText());
		this.switichEditMode(false);
		
    }
	
	@FXML
    void handleCancelButtonClick(ActionEvent event) {
		this.switichEditMode(false);
    }
	
	private void switichEditMode(boolean isEditing) {
		this.edit.setVisible(!isEditing);
		this.logout.setVisible(!isEditing);
		this.save.setVisible(isEditing);
		this.cancel.setVisible(isEditing);
		this.userDescription.setEditable(isEditing);
	}
	
	@FXML
    void handleLogoutButtonClick(ActionEvent event) {
		Session.getInstance().logout();
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handleHomeClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handleLastProejctsButtonClick(ActionEvent event) {
		if (this.currentPage > 0) {
			this.currentPage--;
			this.updateProjectDisplay();
		}
    }

    @FXML
    void handleNextProjectsButtonClick(ActionEvent event) {
		int totalPages = (int) Math.ceil((double) this.currentProjects.size() / this.projectsPerPage);

		if (this.currentPage < totalPages - 1) {
			this.currentPage++;
			this.updateProjectDisplay();
		}
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
    void handleViewButtonClick(ActionEvent event) {
		Object source = event.getSource();

		int indexOffset = 0;

		if (source == this.projectView1) {
			indexOffset = 0;
		} else if (source == this.projectView2) {
			indexOffset = 1;
		} else if (source == this.projectView3) {
			indexOffset = 2;
		} else {
			return;
		}

		int projectIndex = (this.currentPage * this.projectsPerPage) + indexOffset;

		if (projectIndex < this.currentProjects.size()) {
			Project selectedProject = this.currentProjects.get(projectIndex);
			ProjectContext.getInstance().setSelectedProject(selectedProject);
			GuiHelper.switchView(this.anchorPane, Views.PROJECTS);
		}
    }
	
	/**
	* this returns the anchor pane that will be changed 
	*
	* @return anchorPane that is in use to help change views
	*/
	public AnchorPane getAnchorPane() {
		return this.anchorPane;
	}

	/**
	*this sets the anchorPane that will be used as the view
	*
	* @param tempAnchorPane which it the anchor pane being set to
	*/
	public void setAnchorPane(AnchorPane tempAnchorPane) {
		this.anchorPane = tempAnchorPane;
		
	}
	
	@FXML
	void initialize() {
		this.currentPage = 0;
		this.initUserNames();
	}

	private void initUserNames() {
		if (Session.getInstance().getCurrentUser() != null) {
			String firstName = Session.getInstance().getCurrentUser().getFirstName();
			this.accountHeader.setText(firstName);

			if (AccountContext.getInstance().hasUserToView()) {
				User viewed = AccountContext.getInstance().getUserToView();
				this.userName.setText(viewed.getFirstName());
				this.loadUserProjects(viewed);
			} else {
				this.userName.setText("Error: Select Another User");
			}
		} else {
			GuiHelper.switchView(this.anchorPane, Views.LOGIN);
			this.accountHeader.setText("Account");
		}
	}

	private void loadUserProjects(User user) {
		this.currentProjects = new ProjectManager().getProjects();
		this.currentPage = 0;
		this.updateProjectDisplay();
	}

	private void updateProjectDisplay() {
		this.projectEdit1.clear();
		this.projectEdit2.clear();
		this.projectEdit3.clear();
		this.projectDescription1.clear();
		this.projectDescription2.clear();
		this.projectDescription3.clear();
		this.projectPane1.setVisible(false);
		this.projectPane2.setVisible(false);
		this.projectPane3.setVisible(false);
		int startIndex = this.currentPage * this.projectsPerPage;

		if (startIndex < this.currentProjects.size()) {
			this.projectPane1.setVisible(true);
			this.projectEdit1.setText(this.currentProjects.get(startIndex).getFormattedLastEdited());
			this.projectDescription1.setText(this.currentProjects.get(startIndex).getDescription());
		}
		if (startIndex + 1 < this.currentProjects.size()) {
			this.projectPane2.setVisible(true);
			this.projectEdit2.setText(this.currentProjects.get(startIndex + 1).getFormattedLastEdited());
			this.projectDescription2.setText(this.currentProjects.get(startIndex + 1).getDescription());
		}
		if (startIndex + 2 < this.currentProjects.size()) {
			this.projectPane3.setVisible(true);
			this.projectEdit3.setText(this.currentProjects.get(startIndex + 2).getFormattedLastEdited());
			this.projectDescription3.setText(this.currentProjects.get(startIndex + 2).getDescription());
		}
		this.updateNavigationButtons();
	}
	
	private void updateNavigationButtons() {
		int totalPages = (int) Math.ceil((double) this.currentProjects.size() / this.projectsPerPage);
		this.lastProjectsButton.setDisable(this.currentPage == 0);
		this.nextProjectsButton.setDisable(this.currentPage >= totalPages - 1);
	}
}
