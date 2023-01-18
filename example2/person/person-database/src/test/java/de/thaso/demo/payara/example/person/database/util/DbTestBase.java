package de.thaso.demo.payara.example.person.database.util;

import liquibase.exception.LiquibaseException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Spy;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbTestBase {

    @Spy
    protected EntityManager entityManager = PersistenceStoreHelper.getEntityManager();

    private Connection connection;
    private IDatabaseConnection databaseConnection;

    @BeforeAll
    public static void prepareDatabase() throws SQLException, LiquibaseException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createDatabase(PropertiesManager.readDevelopProperties());
    }

    @BeforeEach
    public void setUpConnection() {
        entityManager.getTransaction().begin();
        connection = entityManager.unwrap(Connection.class);

        initializeDatabase();
    }

    @AfterEach
    public void tearDownConnection() {
        entityManager.getTransaction().commit();
    }

    public Connection getConnection() {
        return connection;
    }

    private void initializeDatabase() {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        try {
            databaseConnection = new DatabaseConnection(connection);

            final DatabaseConfig config = databaseConnection.getConfig();
            final H2DataTypeFactory dataTypeFactory = new H2DataTypeFactory();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dataTypeFactory);

            IDataSet dataSet = builder.build(this.getClass().getResourceAsStream("/dbunit/base-setup.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }
    }

    protected void updateDatabase(final String setupFile) {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        try {
            IDataSet dataSet = builder.build(this.getClass().getResourceAsStream("/dbunit/" + setupFile));
            DatabaseOperation.REFRESH.execute(databaseConnection, dataSet);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }
    }
}
