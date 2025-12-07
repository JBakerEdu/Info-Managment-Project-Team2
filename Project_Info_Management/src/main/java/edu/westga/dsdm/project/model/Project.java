package edu.westga.dsdm.project.model;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a project in the user's portfolio.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public class Project {
    private String name;
    private String description;
    private String hyperlink;
    private LocalDateTime lastEdited;
    private File rootDirectory;

    /**
     * Creates a new Project with the given name, description, and hyperlink.
     * Sets the lastEdited timestamp to now and the root directory to null.
     *
     * @param name the name of the project
     * @param description a description of the project
     * @param hyperlink a URL to an external project reference (e.g. GitHub)
     */
    public Project(String name, String description, String hyperlink) {
        this.name = name;
        this.description = description;
        this.hyperlink = hyperlink;
        this.lastEdited = LocalDateTime.now();
        this.rootDirectory = null;
    }

    /**
     * Gets the name of the project.
     *
     * @return the project name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the project and updates the lastEdited timestamp.
     *
     * @param name the new project name
     */
    public void setName(String name) {
        this.name = name;
        this.updateLastEdited();
    }

    /**
     * Gets the project's description.
     *
     * @return the project description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the project and updates the lastEdited timestamp.
     *
     * @param description the new project description
     */
    public void setDescription(String description) {
        this.description = description;
        this.updateLastEdited();
    }

    /**
     * Gets the external hyperlink of the project.
     *
     * @return the hyperlink URL
     */
    public String getHyperlink() {
        return this.hyperlink;
    }

    /**
     * Sets the external hyperlink and updates the lastEdited timestamp.
     *
     * @param hyperlink the new URL to set
     */
    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
        this.updateLastEdited();
    }

    /**
     * Gets the last date and time the project was modified.
     *
     * @return the lastEdited timestamp
     */
    public LocalDateTime getLastEdited() {
        return this.lastEdited;
    }

    /**
     * Gets the lastEdited timestamp formatted as a string (yyyy-MM-dd).
     *
     * @return formatted last edited date
     */
    public String getFormattedLastEdited() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.lastEdited.format(formatter);
    }

    /**
     * Gets the root file directory associated with this project.
     *
     * @return the root directory, or null if not set
     */
    public File getRootDirectory() {
        return this.rootDirectory;
    }

    /**
     * Sets the root directory and updates the lastEdited timestamp.
     *
     * @param rootDirectory the root directory to associate with the project
     */
    public void setRootDirectory(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.updateLastEdited();
    }

    /**
     * Updates the lastEdited timestamp to the current time.
     */
    private void updateLastEdited() {
        this.lastEdited = LocalDateTime.now();
    }
}
