package edu.westga.dsdm.project.model;

import edu.westga.dsdm.project.azurepgsql.DBUserOps;
import edu.westga.dsdm.project.util.EncryptionService;

/**
 * Manages all registered user accounts within the database.
 * 
 * @author Kate Anglin
 * @version Fall 2025
 */
public class AccountManager {

    /**
     * Creates a new user account using DB stored procedures.
     *
     * @param firstName user's given name
     * @param lastName  user's family name
     * @param email     unique email
     * @param password  plaintext password
     * @param role      system role (e.g. teacher, volunteer)
     * @return Created User or null if failed
     */
    public static User createAccount(String firstName, String lastName,
                                     String email, String password, String role) throws Exception {
        User existing = DBUserOps.findByEmail(email);
        if (existing != null) {
            return null;
        }
        String passwordHash = EncryptionService.hashPassword(password);
        return DBUserOps.createUser(firstName, lastName, email, passwordHash, role);
    }

    /**
     * Attempts login by email and password.
     *
     * @param email    user email
     * @param password plaintext password
     * @return User if valid login, null if invalid
     */
    public static User validateLogin(String email, String password) throws Exception {
        User found = DBUserOps.findByEmail(email);
        if (found == null) {
            return null;
        }
        boolean ok = EncryptionService.verifyPassword(password, found.getPasswordHash());
        return ok ? found : null;
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email to look up
     * @return the matching User if found; null otherwise
     */
    public static User findUserByEmail(String email) throws Exception {
        return DBUserOps.findByEmail(email);
    }
}
