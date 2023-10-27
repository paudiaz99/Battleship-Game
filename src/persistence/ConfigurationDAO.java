package persistence;

/**
 * This Interface is used to communicate with the database for the configuration
 */
public interface ConfigurationDAO {
    String readConfig();
    int getTime();
}
