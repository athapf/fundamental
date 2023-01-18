package de.thaso.demo.payara.example.person.database.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertiesManager
 *
 * @author thaler
 * @since 13.09.16
 */
public class PropertiesManager {

    public static final Logger LOG = LogManager.getLogger();

    private static final String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");
    private static final char JAVA_FILE_SEPARATOR = '/';
    private static final String DEVELOP_PROPERTIES = "develop.properties";
    private static final String PROJECT_HOME = "PROJECT_HOME";
    private static final String USER_HOME = "user.home";
    private static final String DEVELOP_PROPERTIES_FILE = "develop.properties.file";

    public static Properties readDevelopProperties() {
        return readDevelopProperties(DEVELOP_PROPERTIES);
    }

    public static Properties readDevelopProperties(final String propertiesFileName) {
        final PropertiesManager propertiesManager = new PropertiesManager();
        return propertiesManager.readProperties(propertiesFileName);
    }

    private PropertiesManager() {}

    private Properties readProperties(final String propertiesFileName) {
        logBuildEnvironment();
        final Properties properties = new Properties();
        properties.putAll(loadPropertiesFromResource(StringUtils.join(JAVA_FILE_SEPARATOR, propertiesFileName)));

        final String project_home = System.getenv(PROJECT_HOME);
        if(StringUtils.isNoneEmpty(project_home)) {
            final String projectPropertiesFileName = StringUtils.join(project_home, SYSTEM_FILE_SEPARATOR, propertiesFileName);
            properties.putAll(loadPropertiesFromFile(projectPropertiesFileName));
        }

        final String userHomePropertiesFileName = StringUtils.join(System.getProperty(USER_HOME), SYSTEM_FILE_SEPARATOR, propertiesFileName);
        properties.putAll(loadPropertiesFromFile(userHomePropertiesFileName));

        final String developPropertiesFileName = System.getProperty(DEVELOP_PROPERTIES_FILE);
        if(StringUtils.isNotEmpty(developPropertiesFileName)) {
            properties.putAll(loadPropertiesFromFile(developPropertiesFileName));
        }

        return properties;
    }

    private void logBuildEnvironment() {
        final String target_env = System.getenv("DEVELOP_MODE");
        final String target_property = System.getProperty("develop.mode");
        LOG.info("==> develop mode: {} / {}", target_env, target_property);
    }

    private Properties loadPropertiesFromFile(final String fileName) {
        final Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileName));
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
        return properties;
    }

    private Properties loadPropertiesFromResource(final String resource) {
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(resource));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return properties;
    }
}
