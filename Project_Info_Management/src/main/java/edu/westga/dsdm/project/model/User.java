package edu.westga.dsdm.project.model;

/**
 * Represents a user in the project portfolio system.
 * A user has a username, password, email, personal description, and a project manager.
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String description;
    private ProjectManager projectManager;

    /**
     * Creates a new User with the specified username, password, and email.
     * Initializes the description to an empty string and creates a new ProjectManager.
     *
     * @param username the username for the user
     * @param password the password for the user
     * @param email the email address of the user
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = "";
        this.projectManager = new ProjectManager();
    }

    /**
     * Gets the username of this user.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the password of this user.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the email of this user.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the personal description of this user.
     *
     * @return the user's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the personal description for this user.
     *
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project manager associated with this user.
     * This allows access to the user's projects.
     *
     * @return the ProjectManager
     */
    public ProjectManager getProjectManager() {
        return this.projectManager;
    }
}
