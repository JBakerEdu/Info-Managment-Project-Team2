package edu.westga.dsdm.project.view.codebehind;

import java.util.List;
import java.util.Collections;
import edu.westga.dsdm.project.model.User;
import edu.westga.dsdm.project.model.Project;
import edu.westga.dsdm.project.model.ProjectContext;
import edu.westga.dsdm.project.model.AccountManager;
import edu.westga.dsdm.project.model.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * CodeBehind To Handle Processing for the Home Page
 *
 * @author	Jacob Baker
 * @version Spring 2025
 */
public class HomePageView {
	private Project[] displayedProjects = new Project[4];
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Label accountHeader;

    @FXML
    private Button login;

    @FXML
    private TextArea projectDescription1;

    @FXML
    private TextArea projectDescription2;

    @FXML
    private TextArea projectDescription3;

    @FXML
    private TextArea projectDescription4;

    @FXML
    private TextField projectEdit1;

    @FXML
    private TextField projectEdit2;

    @FXML
    private TextField projectEdit3;

    @FXML
    private TextField projectEdit4;

    @FXML
    private ImageView projectImage1;

    @FXML
    private ImageView projectImage2;

    @FXML
    private ImageView projectImage3;

    @FXML
    private ImageView projectImage4;

    @FXML
    private Pane projectPane1;

    @FXML
    private Pane projectPane2;

    @FXML
    private Pane projectPane3;

    @FXML
    private Pane projectPane4;

    @FXML
    private Button projectView1;

    @FXML
    private Button projectView2;

    @FXML
    private Button projectView3;

    @FXML
    private Button projectView4;

    @FXML
    void handleHomeClick(MouseEvent event) {
		GuiHelper.switchView(this.anchorPane, Views.HOMEPAGE);
    }

    @FXML
    void handleLoginButtonClick(ActionEvent event) {
		if (Session.getInstance().getCurrentUser() == null) {
			GuiHelper.switchView(this.anchorPane, Views.LOGIN);
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
		Button source = (Button) event.getSource();

		int index = -1;
		if (source == this.projectView1) {
			index = 0;
		} else if (source == this.projectView2) {
			index = 1;
		} else if (source == this.projectView3) {
			index = 2;
		} else if (source == this.projectView4) {
			index = 3;
		}

		if (index >= 0 && this.displayedProjects[index] != null) {
			ProjectContext.getInstance().setSelectedProject(this.displayedProjects[index]);
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
		if (Session.getInstance().getCurrentUser() != null) {
			String username = Session.getInstance().getCurrentUser().getFirstName();
			this.accountHeader.setText(username);
			this.login.setDisable(true);
			this.login.setVisible(false);
		} else {
			this.accountHeader.setText("Account");
		}
		this.loadRandomProjectsToHome();
	}
	
	private void loadRandomProjectsToHome() {
		Pane[] panes = { this.projectPane1, this.projectPane2, this.projectPane3, this.projectPane4 };
		TextField[] editors = { this.projectEdit1, this.projectEdit2, this.projectEdit3, this.projectEdit4 };
		TextArea[] descriptions = { this.projectDescription1, this.projectDescription2, this.projectDescription3, this.projectDescription4 };
		for (int i = 0; i < panes.length; i++) {
			this.setProjectSlotVisible(panes[i], false);
			this.clearText(editors[i]);
			this.clearText(descriptions[i]);
		}

//		List<User> users = AccountManager.getAllAccounts();
//		if (users == null || users.isEmpty()) {
//			return;
//		}
//		Collections.shuffle(users);
//		int filled = 0;
//		int attempts = 0;
//		while (filled < 4 && attempts < users.size()) {
//			User user = users.get(attempts++);
//			Project validProject = this.getRandomProjectFromUser(user);
//			if (validProject != null) {
//				this.setProjectSlotVisible(panes[filled], true);
//				editors[filled].setText(this.getSafeDate(validProject));
//				descriptions[filled].setText(this.getSafeDescription(validProject));
//				this.displayedProjects[filled] = validProject;
//				filled++;
//			}
//		}
	}
	
	private Project getRandomProjectFromUser(User user) {
		if (user == null) {
			return null;
		}

//		List<Project> projects = user.getProjectManager().getProjects();
//		if (projects == null || projects.isEmpty()) {
//			return null;
//		}
//
//		Collections.shuffle(projects);
//		for (Project project : projects) {
//			if (project != null) {
//				return project;
//			}
//		}
		return null;
	}

	private void setProjectSlotVisible(Pane pane, boolean visible) {
		if (pane != null) {
			pane.setVisible(visible);
		}
	}

	private void clearText(javafx.scene.control.TextInputControl control) {
		if (control != null) {
			control.clear();
		}
	}

	private String getSafeDate(Project project) {
		return (project.getFormattedLastEdited() != null) ? project.getFormattedLastEdited() : "Unknown Date";
	}

	private String getSafeDescription(Project project) {
		return (project.getDescription() != null) ? project.getDescription() : "(No description)";
	}
}
