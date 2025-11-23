package edu.westga.dsdm.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all registered user accounts within the system.
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public class AccountManager {
    private static List<User> accounts = new ArrayList<>();

    /**
     * Creates a new user account if the username and email are unique.
     *
     * @param username the desired username
     * @param password the desired password
     * @param email the email address for the account
     * @return true if the account was created, false if the username or email is already in use
     */
    public static boolean createAccount(String username, String password, String email) {
        if (findUserByUsernameOrEmail(username) != null) {
            return false;
        }
        if (findUserByUsernameOrEmail(email) != null) {
            return false;
        }
        accounts.add(new User(username, password, email));
        return true;
    }

    /**
     * Validates login credentials by matching both username and password.
     *
     * @param username the entered username
     * @param password the entered password
     * @return the matching User object if valid; null otherwise
     */
    public static User validateLogin(String username, String password) {
        for (User user : accounts) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by matching either username or email.
     *
     * @param input the username or email to look up
     * @return the matching User if found; null otherwise
     */
    public static User findUserByUsernameOrEmail(String input) {
        for (User user : accounts) {
            if (user.getUsername().equals(input) || user.getEmail().equals(input)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to look up
     * @return the matching User if found; null otherwise
     */
    public static User findUserByUsername(String username) {
        for (User user : accounts) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email to look up
     * @return the matching User if found; null otherwise
     */
    public static User findUserByEmail(String email) {
        for (User user : accounts) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of all registered user accounts.
     *
     * @return a list of all users
     */
    public static List<User> getAllAccounts() {
        return accounts;
    }
}
