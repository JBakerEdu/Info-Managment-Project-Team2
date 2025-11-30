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

    private AzurePgsqlServer() throws Exception {
        loadProperties();
        openConnection();
    }

    /**
     * Singleton accessor — ensures 1 DB connection manager.
     *
     * @return the server instance
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
     * Loads database configuration from src/main/resources/application.properties.
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
     * Opens the JDBC connection.
     */
    private void openConnection() throws SQLException {
        log.info("Connecting to Azure PostgreSQL...");
        connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        log.info("Connection established. Database: " + connection.getCatalog());
    }

    /**
     * Returns the active database connection.
     * Reconnects if the connection has closed.
     *
     * @return JDBC connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            log.warning("Database connection was closed. Reopening...");
            openConnection();
        }
        return connection;
    }

    /**
     * Gracefully closes DB connection.
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