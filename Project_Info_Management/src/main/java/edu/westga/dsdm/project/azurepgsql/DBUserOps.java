package edu.westga.dsdm.project.azurepgsql;
import edu.westga.dsdm.project.model.User;
import java.sql.*;
import java.util.logging.Logger;

public class DBUserOps {

    private static final Logger log = Logger.getLogger(DBUserOps.class.getName());

    /**
     * Create User using stored procedure create_user.
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
     *
     * @return true if email+hash match
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
     * Helper to fetch user info using SELECT.
     */
    public static User findByEmail(String email) throws Exception {
        log.info("Querying user by email");
        String sql = "SELECT user_id, first_name, last_name, email, password_hash, role_at_event " +
                "FROM users WHERE email = ? LIMIT 1";
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
}