package edu.westga.dsdm.project.azurepgsql;
import java.util.List;
import java.util.ArrayList;
import edu.westga.dsdm.project.model.User;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Utility class providing database operations for User objects.
 *
 * This class interacts with PostgreSQL through stored procedures and
 * prepared queries. All methods obtain a connection through
 * {@link AzurePgsqlServer#getInstance()} and either modify or retrieve
 * user data in the database
 * 
 * @author Kate and Jacob
 */
public class DBUserOps {

    private static final Logger log = Logger.getLogger(DBUserOps.class.getName());

    /**
     * Create User using stored procedure create_user.
     * The procedure inserts a user record based on the supplied fields.
     * After execution, this method performs a lookup by email and returns
     * the newly created user.
     *
     * @param first first name
     * @param last last name
     * @param email email
     * @param hash hashed password
     * @param role role at event
     * @return user or null if not found
     * @throws Exception if pgSqlServer not found
     */
    public static User createUser(String first, String last, String email, String hash, String role) throws Exception {
        log.info("Calling stored procedure: create_user");
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        CallableStatement stmt =
                conn.prepareCall("CALL create_user(?, ?, ?, ?, ?)");

        stmt.setString(1, first);
        stmt.setString(2, last);
        stmt.setString(3, email);
        stmt.setString(4, hash);
        stmt.setString(5, role);

        stmt.execute();
        return findByEmail(email);
    }

    /**
     * Deletes a user using stored procedure delete_user.
     *
     * @param userId the ID of the user to delete
     * @throws Exception if a database connection error occurs
     */
    public static void deleteUser(int userId) throws Exception {
        log.info("Calling stored procedure: delete_user");
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        CallableStatement stmt =
                conn.prepareCall("CALL delete_user(?)");

        stmt.setInt(1, userId);
        stmt.execute();
    }

    /**
     * Updates a user's role using change_user_role procedure.
     *
     * @param userId the ID of the user whose role should be updated
     * @param newRole the new role to assign to the user
     * @throws Exception if a database connection error occurs
     */
    public static void changeUserRole(int userId, String newRole) throws Exception {
        log.info("Calling stored procedure: change_user_role");
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        CallableStatement stmt =
                conn.prepareCall("CALL change_user_role(?, ?)");

        stmt.setInt(1, userId);
        stmt.setString(2, newRole);

        stmt.execute();
    }

    /**
     * Uses verify_user_credentials function.
     * @param email the email address to check
     * @param passwordHash the hashed password to verify
     * @return {@code true} if the credentials match a user record,
     *         {@code false} otherwise
     * @throws Exception if a database connection error occurs
     */
    public static boolean verifyCredentials(String email, String passwordHash) throws Exception {
        log.info("Calling stored function: verify_user_credentials");
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt =
                conn.prepareStatement("SELECT verify_user_credentials(?, ?) AS valid");

        stmt.setString(1, email);
        stmt.setString(2, passwordHash);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return false;
        }

        return rs.getBoolean("valid");
    }

    /**
     * Fetches user info by email without exposing the password hash.
     *
     * @param email the email to search for
     * @return user if the user (w/o passhash) exists, null otherwise
     * @throws Exception if a database connection error occurs
     */
    public static User findByEmail(String email) throws Exception {
        log.info("Checking if user exists by email");
        String sql = "SELECT * FROM find_user_by_email(?)";
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }

        return mapUser(rs);
    }

    /**
     * Utility mapper — converts a ResultSet row to your User class
     *
     * @param rs the ResultSet positioned on the desired row
     * @return a fully populated {@link User} object
     * @throws SQLException if any column access fails
     */
    private static User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("role_at_event")
        );
    }

    /**
     * Retrieves all users from the database.
     * 
     * This method queries the database for all users and returns them as a list. 
     * It fetches the user details from the `users` table and maps them to `User` objects.
     * 
     * @return A list of all users from the database.
     * @throws Exception If an error occurs while querying the database or processing the result set.
     */
    public static List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        Connection conn = AzurePgsqlServer.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            users.add(mapUser(rs));
        }

        return users;
    }

    /**
     * Finds a user by their user_id by fetching all users and iterating.
     *
     * @param userId the ID of the user to find
     * @return matching User or null if not found
     * @throws Exception if database access fails
     */
    public static User findUserByIdIterative(int userId) throws Exception {
        List<User> users = getAllUsers();  // You already have this method

        if (users == null || users.isEmpty()) {
            return null;
        }

        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }

        return null;
    }


}