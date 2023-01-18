package de.thaso.demo.payara.example.person.database.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class PersistenceStoreHelper {

    public static final Logger LOG = LogManager.getLogger(PersistenceStoreHelper.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManagerAud;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("demodb", getConnectionProperties());
            entityManagerAud = entityManagerFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManagerFactory = null;
            entityManagerAud = null;
        }
    }

    public static Properties getConnectionProperties() {
        Properties properties = PropertiesManager.readDevelopProperties();
        properties.setProperty("javax.persistence.transactiontype","RESOURCE_LOCAL");
        return properties;
  }

    public static EntityManager getEntityManager() {
    return entityManagerAud;
  }
}
