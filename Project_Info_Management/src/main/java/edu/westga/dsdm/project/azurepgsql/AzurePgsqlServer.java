package edu.westga.dsdm.project.azurepgsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Azure PostgreSQL Connection Manager.
 *
 * Loads database properties from application.properties and provides
 * a reusable JDBC connection for database access classes.
 *
 * @author Kate Anglin
 * @version Fall 2025
 */
public class AzurePgsqlServer {

    private static final Logger log = Logger.getLogger(AzurePgsqlServer.class.getName());

    private static AzurePgsqlServer instance;
    private Connection connection;
    private Properties properties;

    /**
     * Private constructor — initializes database configuration and opens the initial connection.
     *
     * @throws Exception if properties cannot be loaded or the database connection cannot be opened
     */
    private AzurePgsqlServer() throws Exception {
        loadProperties();
        openConnection();
    }

    /**
     * Returns the singleton instance of the database server manager.
     *
     * Uses double-checked locking to ensure thread safety while minimizing synchronization
     * overhead.
     *
     * @return the singleton {@code AzurePgsqlServer} instance
     * @throws Exception if initialization fails (e.g., properties or connection error)
     */
    public static AzurePgsqlServer getInstance() throws Exception {
        if (instance == null) {
            synchronized (AzurePgsqlServer.class) {
                if (instance == null) {
                    instance = new AzurePgsqlServer();
                }
            }
        }
        return instance;
    }

    /**
     * Loads database configuration from {@code application.properties} in the classpath.
     *
     * Required properties include (minimum):
     *
     *   {@code url} — full JDBC connection string
     *   {@code user}
     *   {@code password}
     *
     * @throws Exception if the properties file is missing or cannot be parsed
     */
    private void loadProperties() throws Exception {
        log.info("Loading application.properties...");
        properties = new Properties();
        var propsStream = AzurePgsqlServer.class
                .getClassLoader()
                .getResourceAsStream("application.properties");

        if (propsStream == null) {
            throw new RuntimeException("application.properties not found in classpath!");
        }

        properties.load(propsStream);
    }

    /**
     * Opens the JDBC connection to the configured Azure PostgreSQL instance.
     *
     * This method is invoked automatically during initialization, and again if a
     * connection is found closed.
     *
     @throws SQLException if the JDBC driver fails to authenticate or connect
     */
    private void openConnection() throws SQLException {
        log.info("Connecting to Azure PostgreSQL...");
        connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        log.info("Connection established. Database: " + connection.getCatalog());
    }

    /**
     * Returns the active database connection to the configured Azure PostgreSQL instance.
     * Reconnects if the connection has closed.
     *
     * @return JDBC connection
     * @throws SQLException if reconnection attempt fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            log.warning("Database connection was closed. Reopening...");
            openConnection();
        }
        return connection;
    }

    /**
     * Closes the active database connection if it is currently open.
     *
     * This method should be invoked during controlled shutdowns, such as application exit
     * or cleanup routines.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                log.info("Closing database connection...");
                connection.close();
            }
        } catch (SQLException ex) {
            log.warning("Error closing connection: " + ex.getMessage());
        }
    }
}