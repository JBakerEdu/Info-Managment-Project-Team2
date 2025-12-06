package edu.westga.dsdm.project.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of projects for a specific user.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class ProjectManager {
    private List<Project> projects;

    /**
     * Constructs a new ProjectManager with an empty list of projects.
     */
    public ProjectManager() {
        this.projects = new ArrayList<>();
    }

    /**
     * Adds a new project to the manager.
     *
     * @param name the name of the project (must not be null or blank)
     * @param description the project description
     * @param hyperlink an external link to the project (e.g. GitHub)
     * @param rootDirectory the file directory associated with the project
     * @throws IllegalArgumentException if the name is null or blank
     */
    public void addProject(String name, String description, String hyperlink, File rootDirectory) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }

        Project project = new Project(name, description, hyperlink);
        project.setRootDirectory(rootDirectory);

        this.projects.add(project);
    }

    /**
     * Removes a project from the manager.
     *
     * @param project the project to remove
     */
    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    /**
     * Retrieves the list of all projects managed by this ProjectManager.
     *
     * @return a list of Project objects
     */
    public List<Project> getProjects() {
        return this.projects;
    }

    /**
     * Retrieves a project by its name, ignoring case.
     *
     * @param name the name of the project to search for
     * @return the matching Project, or null if not found or if the name is null/blank
     */
    public Project getProjectByName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        for (Project project : this.projects) {
            if (project.getName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }
}
