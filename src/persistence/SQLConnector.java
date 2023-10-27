package persistence;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class is used to connect to the database.
 */
public class SQLConnector {

    /**
     * The user of the database.
     */
    private final String DB_User;
    /**
     * The password of the database.
     */
    private final String DB_Password;
    /**
     * The url of the database.
     */
    private final String DB_Url;
    /**
     * The connection to the database.
     */
    private Connection connection;

    /**
     * Constructor of the class SQLConnector that initialize the connector.
     */
    public SQLConnector() {
        ConfigurationDAO configurationJsonDAO = new ConfigurationJsonDAO();
        String[] configurationStrings = configurationJsonDAO.readConfig().split("-");
        this.DB_User = configurationStrings[0];
        this.DB_Password = configurationStrings[1];
        this.DB_Url = "jdbc:mysql://"+configurationStrings[2]+":"+configurationStrings[3]+"/"+configurationStrings[4];
    }

    /**
     * Connect to the database
     */
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(DB_Url, DB_User, DB_Password);
        } catch (SQLException e) {
            System.err.println("Couldn't connect to --> " + DB_Url + " (" + e.getMessage() + ")");
        }
    }

    /**
     * @param query The query to execute
     *              Execute the query
     */
    void insertQuery(String query){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when executing query --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    /**
     * @param query The query to execute
     * @return The number of rows affected
     */
    public int deleteQuery(String query) {
        int rowsAffected = 0;
        try {
            Statement s = connection.createStatement();
            rowsAffected = s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when deleting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
        return rowsAffected;
    }

    /**
     * @param query The query to execute
     * @return true if the query has a result, false otherwise
     */
    public boolean selectQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return statement.getResultSet().next();

        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when executing query --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
        return false;
    }

    /**
     * @param query The query to execute
     * @param column The column to get
     * @return The column of the query
     */
    public String getQuery(String query, String column){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString(column);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when executing query --> " + e.getSQLState() + " (" + e.getMessage() + ")");
            return null;
        }
    }

    /**
     * @param query The query to execute
     * @param column The column to get
     * @return ArrayList of the column
     */
    public ArrayList<String> getMultipleStrings(String query, String column) {
        ArrayList<String> gamesNames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                gamesNames.add(resultSet.getString(column));
            }
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when executing query --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
        return gamesNames;
    }

    /**
     * @param query The query to execute
     * @param name The name of the column to get
     * @param name2 The name of the second column to get
     * @return ArrayList of the two columns
     */
    public ArrayList<String> getMultipleStringsMultipleCol(String query, String name, String name2) {
        ArrayList<String> strings = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                strings.add(resultSet.getString(name) + " - " + resultSet.getString(name2));
            }
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when executing query --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
        return strings;
    }
}
