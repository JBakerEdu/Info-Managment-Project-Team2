package edu.westga.dsdm.project.model;

/**
 * Singleton class used to store and manage the currently selected project.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public final class ProjectContext {
    private static ProjectContext instance = null;
    private Project selectedProject;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private ProjectContext() {
    }

    /**
     * Retrieves the singleton instance of ProjectContext.
     *
     * @return the singleton instance
     */
    public static ProjectContext getInstance() {
        if (instance == null) {
            instance = new ProjectContext();
        }
        return instance;
    }

    /**
     * Sets the currently selected project.
     *
     * @param project the project to be selected
     */
    public void setSelectedProject(Project project) {
        this.selectedProject = project;
    }

    /**
     * Gets the currently selected project.
     *
     * @return the selected Project, or null if none is set
     */
    public Project getSelectedProject() {
        return this.selectedProject;
    }

    /**
     * Clears the selected project.
     */
    public void clear() {
        this.selectedProject = null;
    }

    /**
     * Checks if a project is currently selected.
     *
     * @return true if a project is selected; false otherwise
     */
    public boolean hasSelectedProject() {
        return this.selectedProject != null;
    }
}
