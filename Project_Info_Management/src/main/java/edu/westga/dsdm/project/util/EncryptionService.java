package edu.westga.dsdm.project.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The Encryption Service that encrypts passwords
 *
 * @author Kate Anglin
 * @version Fall 2025
 */
public class EncryptionService {

    /**
     * Hashes the password
     *
     * @param plain password text
     * @return hashed password
     */
    public static String hashPassword(String plain) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plain, salt);
    }

    /**
     * Verifys the password
     *
     * @param plain password text
     * @param hashed password
     * @return true if valid password; false otherwise
     */
    public static boolean verifyPassword(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}