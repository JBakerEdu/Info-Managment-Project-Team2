package edu.westga.dsdm.project.model;

/**
 * Singleton class that manages the current logged-in user session.
 * 
 * @author Jacob Baker
 * @version Fall 2025
 */
public final class Session {
    private static Session instance = null;
    private User currentUser;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private Session() {
        // private constructor (singleton)
    }

    /**
     * Retrieves the singleton instance of the Session.
     *
     * @return the Session instance
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Logs in the given user and stores it as the current session user.
     *
     * @param user the user to log in
     */
    public void login(User user) {
        this.currentUser = user;
    }

    /**
     * Logs out the current user, ending the session.
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Gets the user currently logged into the session.
     *
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }
}
