package de.thaso.demo.payara.example.person.database;

import de.thaso.demo.payara.example.person.database.util.DatabaseManager;
import de.thaso.demo.payara.example.person.database.util.PropertiesManager;
import liquibase.exception.LiquibaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDatabaseIT {
    public static final Logger LOG = LogManager.getLogger(ConnectDatabaseIT.class);

    private static final Properties properties = PropertiesManager.readDevelopProperties();

    @BeforeAll
    public static void initDatabase() throws Exception {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createDatabase(properties);
    }

    @Test
    public void tableDepartmentExists() throws SQLException, LiquibaseException {
        Connection connection = openConnection();

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(1) FROM T_DEPARTMENT");
        resultSet.next();
        LOG.info("SQL: " + resultSet.getInt(1));

        connection.close();
    }

    @Test
    public void tableDemoExists() throws SQLException, LiquibaseException {
        Connection connection = openConnection();

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(1) FROM T_DEMO");
        resultSet.next();
        LOG.info("SQL: " + resultSet.getInt(1));

        connection.close();
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("javax.persistence.jdbc.url"),
                properties.getProperty("javax.persistence.jdbc.user"),
                properties.getProperty("javax.persistence.jdbc.password"));
    }
}
