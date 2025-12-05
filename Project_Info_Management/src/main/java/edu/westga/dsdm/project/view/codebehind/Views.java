package edu.westga.dsdm.project.view.codebehind;

/**
 * Stores the different view locations
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public enum Views {
	HOMEPAGE("/edu/westga/dsdm/project/view/codebehind/HomePageView.fxml"),
	ACCOUNT("/edu/westga/dsdm/project/view/codebehind/AccountPortfolioPageView.fxml"),
	PROJECTS("/edu/westga/dsdm/project/view/codebehind/EventPageView.fxml"),
	LOGIN("/edu/westga/dsdm/project/view/codebehind/LoginPageView.fxml"),
	CREATE_ACCOUNT("/edu/westga/dsdm/project/view/codebehind/CreateAccountPageView.fxml"),
	TEACHER_PROFILE("/edu/westga/dsdm/project/view/codebehind/AccountTeacherProfilePageView.fxml"),
	STUDENT_PROFILE("/edu/westga/dsdm/project/view/codebehind/AccountStudentProfilePageView.fxml");
	
	private String fileLocation;
	
	Views(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	/**
	 * Get the file location of the enum
	 * @return the file location associated with the enum
	 */
	public String location() {
		return this.fileLocation;
	}
}