package edu.westga.dsdm.project.model;

/**
 * Represents a user stored in the database.
 * This model contains only database fields.
 *
 * @author Kate Anglin
 * @version Fall 2025
 */
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String role;

    /**
     * Constructs a User with the given database values.
     *
     * @param userId the user's unique database ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param passwordHash the hashed password from the database
     * @param role the role at events
     */
    public User(int userId, String firstName, String lastName, String email, String passwordHash, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /**
     * Gets the UserId
     *
     * @return the user id
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Gets the first name
     *
     * @return the user first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the last name
     *
     * @return the user last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the email
     *
     * @return the users email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the hashed password
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return this.passwordHash;
    }

    /**
     * Gets the role of the user
     *
     * @return the role
     */
    public String getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return this.getFirstName () + " " + this.getLastName();
    }
}