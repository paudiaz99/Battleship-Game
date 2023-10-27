package persistence;

import java.util.ArrayList;

/**
 * This Interface is used to communicate with the database for the game
 */
public interface GameDAO {
    String getCurrentTime(String gameName);

    void insertGame(String gamename, String date, String state, String time, String jsonName, String username, String hits);

    ArrayList<String> getGamesNamesNotEnded(String name);

    String getFilePath(String selectedItem);
    void EndGame(String gameName);

    void updateGame(String name, String time, String path, String username, String date);

    ArrayList<String> getAllGamesNames();
    ArrayList<String> getGameNames(String name);

    void deleteGame(String gameName);

    void addHitToGame(String currentGameName);
    ArrayList<String> getAllHits(String username);

    void updateGameName(String oldName, String newName);
    int getHitsPerGame(String name);

    void updateHits(String currentGameName, int originalHits);
}
