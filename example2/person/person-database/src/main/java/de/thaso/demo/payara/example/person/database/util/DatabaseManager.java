package de.thaso.demo.payara.example.person.database.util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseManager
 *
 * @author thaler
 * @since 13.09.16
 */
public class DatabaseManager {

    public static final Logger LOG = LogManager.getLogger(DatabaseManager.class);

    private static final String FLYWAY_SCRIPT_PATH = "db/local";
    private static final String JDBC_URL = "javax.persistence.jdbc.url";
    private static final String JDBC_USER = "javax.persistence.jdbc.user";
    private static final String JDBC_PASSWORD = "javax.persistence.jdbc.password";

    private Connection connection;

    public void createDatabase(final Properties properties) throws SQLException, LiquibaseException {
        LOG.info("createDatabase {}", properties.getProperty(JDBC_URL));

        connection = DriverManager.getConnection(
                properties.getProperty(JDBC_URL),
                properties.getProperty(JDBC_USER),
                properties.getProperty(JDBC_USER));

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("liquibase/changelog.xml",
                new ClassLoaderResourceAccessor(this.getClass().getClassLoader()), database);
        liquibase.update("all");
        database.commit();

        connection.commit();
        connection.close();
    }

    public void migrateDatabase(final Properties properties) {
        LOG.info("migrateDatabase {}", properties.getProperty(JDBC_URL));
    }
}
