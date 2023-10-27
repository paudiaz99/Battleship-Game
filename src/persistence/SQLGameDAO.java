package persistence;

import business.GameManager;

import java.util.ArrayList;


/**
 * This class is used to communicate with the database for the game
 */
public class SQLGameDAO implements GameDAO {
    private final SQLConnector connector;

    /**
     * @param connector the connector to the database
     *                  Creates a new SQLGameDAO
     */
    public SQLGameDAO(SQLConnector connector) {
        this.connector = connector;
        this.connector.connect();
    }

    /**
     * @param gamename the name of the game
     * @param date the date of the game
     * @param state the state of the game
     * @param time the time of the game
     * @param jsonName the name of the json file
     * @param username the username of the player
     * @param hits the hits of the game
     */
    @Override
    public void insertGame(String gamename, String date, String state, String time, String jsonName, String username, String hits) {
        String query = "INSERT INTO game(GameName, Username, CreationDate, CurrentTime, JsonFile, GameState, hits) VALUES ('"+gamename+"','"+username+"','"+date+"','"+time+"','"+jsonName+"','"+state+"', '"+hits+"')";
        connector.insertQuery(query);
    }

    /**
     * @param name the name of the player
     * @return the names of the games of the player that are not ended
     */
    @Override
    public ArrayList<String> getGamesNamesNotEnded(String name) {
        String query = "SELECT GameName, CreationDate FROM game WHERE Username = '"+name+"' AND GameState = '"+ GameManager.NOT_ENDED +"'";
        return connector.getMultipleStringsMultipleCol(query, "GameName", "CreationDate");
    }

    /**
     * @param gameName the name of the game
     * @return file path of the game
     */
    @Override
    public String getFilePath(String gameName) {
        String query = "SELECT JsonFile FROM game WHERE GameName = '"+gameName+"'";
        return connector.getQuery(query, "JsonFile");
    }

    /**
     * @param gameName the name of the game
     * @return current time of the game
     */
    @Override
    public String getCurrentTime(String gameName) {
        String query = "SELECT CurrentTime FROM game WHERE GameName = '"+gameName+"'";
        return connector.getQuery(query, "CurrentTime");
    }

    /**
     * @param gameName the name of the game
     *                 Ends the game by changing the state of the game to ENDED
     */
    public void EndGame(String gameName){
        String query = "UPDATE game SET GameState = '"+ GameManager.ENDED +"' WHERE GameName = '"+gameName+"'";
        connector.insertQuery(query);
    }

    /**
     * @param name the name of the player
     * @param time the time of the game
     * @param path the path of the json file
     * @param gameName the name of the game
     */
    @Override
    public void updateGame(String name, String time, String path, String gameName, String date) {
        String query = "UPDATE game SET CurrentTime = '"+time+"', JsonFile = '"+path+"',GameName = '"+name+"',CreationDate = '"+date+"' WHERE GameName = '"+gameName+"'";
        connector.insertQuery(query);
    }

    /**
     * @return the names of all the games
     */
    @Override
    public ArrayList<String> getAllGamesNames() {
        String query = "SELECT GameName FROM game";
        return connector.getMultipleStrings(query, "GameName");
    }

    /**
     * @param name the name of the player
     * @return the names of the games of the player
     */
    @Override
    public ArrayList<String> getGameNames(String name) {
        String query = "SELECT GameName FROM game WHERE Username = '"+name+"'";
        return connector.getMultipleStrings(query, "GameName");
    }

    /**
     * @param gameName the name of the game
     *                 Deletes the game from the database
     */
    @Override
    public void deleteGame(String gameName) {
        String query = "DELETE FROM game WHERE GameName = '"+gameName+"'";
        connector.deleteQuery(query);
    }

    /**
     * @param currentGameName the name of the game
     *                        Adds a hit to the game
     */
    @Override
    public synchronized void addHitToGame(String currentGameName) {
        int hits = getHitsPerGame(currentGameName);
        String hitsString = Integer.toString(hits+1);
        String query = "UPDATE game SET Hits = "+hitsString+" WHERE GameName = '"+currentGameName+"'";
        connector.insertQuery(query);
    }

    /**
     * @param gameName the name of the game
     * @return the hits of the game
     */
    @Override
    public int getHitsPerGame(String gameName) {
        String query = "SELECT hits FROM game WHERE GameName = '"+gameName+"'";
        return Integer.parseInt(connector.getQuery(query, "hits"));
    }

    /**
     * @param currentGameName the name of the game
     * @param originalHits the original hits of the game
     *                     Updates the hits of the game
     */
    @Override
    public void updateHits(String currentGameName, int originalHits) {
        String hitsString = Integer.toString(originalHits);
        String query = "UPDATE game SET Hits = "+hitsString+" WHERE GameName = '"+currentGameName+"'";
        connector.insertQuery(query);
    }

    /**
     * @param username the username of the player
     * @return the hits of the games of the player
     */
    @Override
    public ArrayList<String> getAllHits(String username) {
        String query = "SELECT hits FROM game WHERE Username = '"+username+"' AND GameState = '"+GameManager.ENDED+"'";
        return connector.getMultipleStrings(query, "hits");
    }

    /**
     * @param gameName the name of the game
     * @param newName the new name of the game
     *                Updates the name of the game
     */
    @Override
    public void updateGameName(String gameName, String newName) {
        String query = "UPDATE game SET GameName = '"+newName+"' WHERE GameName = '"+gameName+"'";
        connector.insertQuery(query);
    }

}
