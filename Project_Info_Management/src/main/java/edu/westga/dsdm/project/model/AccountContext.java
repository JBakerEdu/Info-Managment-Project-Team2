package edu.westga.dsdm.project.model;

/**
 * Singleton class used to store and manage the currently viewed user account.
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public final class AccountContext {
    private static AccountContext instance = null;
    private User viewedUser;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private AccountContext() {
    }

    /**
     * Retrieves the singleton instance of AccountContext.
     *
     * @return the singleton instance
     */
    public static AccountContext getInstance() {
        if (instance == null) {
            instance = new AccountContext();
        }
        return instance;
    }

    /**
     * Sets the user that is currently being viewed.
     *
     * @param user the user to be viewed
     */
    public void setUserToView(User user) {
        this.viewedUser = user;
    }

    /**
     * Retrieves the user currently being viewed.
     *
     * @return the viewed user, or null if none is set
     */
    public User getUserToView() {
        return this.viewedUser;
    }

    /**
     * Clears the currently viewed user.
     */
    public void clearUserToView() {
        this.viewedUser = null;
    }

    /**
     * Checks whether a user is currently being viewed.
     *
     * @return true if a user is set for viewing, false otherwise
     */
    public boolean hasUserToView() {
        return this.viewedUser != null;
    }
}
