package persistence;


import java.util.ArrayList;

/**
 * This class is used to manage the user in the database. It implements the UserDAO interface.
 */
public class SQLUserDAO implements UserDAO{
    /**
     * The connector to use to connect to the database.
     */
    private final SQLConnector connector;

    /**
     * @param connector The connector to use to connect to the database.
     *                  Constructor of the class SQLUserDAO that initialize the connector.
     */
    public SQLUserDAO(SQLConnector connector) {
        this.connector = connector;
        this.connector.connect();
    }


    /**
     * @param username The username to insert
     * @param email The email to insert
     * @param password The password to insert
     *                 Insert a new user in the database
     */
    public void insertUser(String username, String email, String password){
        String query = "INSERT INTO user(Username, Email, Password, wins, gamesPlayed) VALUES ('"+username+"','"+email+"','"+password+"',0,0)";
        connector.insertQuery(query);
    }

    /**
     * @param username The username to delete
     * @return true if the user has been deleted, false otherwise
     */
    public boolean deleteUser(String username) {
        String query = "DELETE FROM user WHERE Username = '"+username+"'";
        return connector.deleteQuery(query) > 0;
    }

    /**
     * @param username The username to check
     * @param password The password to check
     * @return true if the username and the password are correct, false otherwise
     */
    public boolean checkUser(String username, String password) {
        String query = "SELECT * FROM user WHERE (Username = '"+username+"' OR Email = '"+username+"') AND BINARY Password = BINARY '"+password+"'";
        return connector.selectQuery(query);
    }

    /**
     * @param username The username to check
     * @param email The email to check
     * @return true if the username or the email are already in use, false otherwise
     */
    @Override
    public boolean checkUserName(String username, String email) {
        String query = "SELECT * FROM user WHERE Username = '"+username+"' OR Email = '"+email+"'";
        return connector.selectQuery(query);
    }

    /**
     * @param username The username to get the wins
     * @return The number of wins of the user
     */
    public int getWins(String username){
        String query = "SELECT wins FROM user WHERE Username = '"+username+"'";
        return Integer.parseInt(connector.getQuery(query, "wins"));
    }

    /**
     * @param username The username to get the games played
     * @return The number of games played by the user
     */
    public int getGamesPlayed(String username){
        String query = "SELECT gamesPlayed FROM user WHERE Username = '"+username+"'";
        return Integer.parseInt(connector.getQuery(query, "gamesPlayed"));
    }

    /**
     * @param username The username to add the win to
     *                 Add a win to the user
     */
    public void addWin(String username){
        int wins = getWins(username);
        String winsString = Integer.toString(wins + 1);
        String query = "UPDATE user SET wins = '"+winsString+"' WHERE Username = '"+username+"'";
        connector.insertQuery(query);
    }

    /**
     * @param name The name of the user to add the game to
     *             Add a game to the history of the user
     */
    public void addGameToHistory(String name){
        int gamesPlayed = getGamesPlayed(name);
        String gamesPlayedString = Integer.toString(gamesPlayed + 1);
        String query = "UPDATE user SET gamesPlayed = '"+gamesPlayedString+"' WHERE Username = '"+name+"'";
        connector.insertQuery(query);
    }

    /**
     * @param username The username or mail to get the name
     * @return The name of the user
     */
    public String getPlayerName(String username){
        String query = "SELECT Username FROM user WHERE Username = '"+username+"' OR Email = '"+username+"'";
        return connector.getQuery(query, "Username");
    }

    /**
     * @return An ArrayList of all the usernames in the database
     */
    @Override
    public ArrayList<String> getAllUsernames() {
        String query = "SELECT Username FROM user";
        return connector.getMultipleStrings(query, "Username");
    }

}
