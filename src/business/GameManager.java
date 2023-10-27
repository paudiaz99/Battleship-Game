package business;

import persistence.GameDAO;
import persistence.GameJsonDAO;
import persistence.SQLConnector;
import persistence.SQLGameDAO;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the gameManager. It contains the methods to create, load and save a game.
 */
public class GameManager {
    public static final String NOT_ENDED = "Not Ended";
    public static final String ENDED = "Ended";
    public static final String NOT_DEFINED = "Not Defined";
    public static final String PATH_NOT_DEFINED = "Not Defined";
    public static final String ZERO_HITS = "0";

    private final GameDAO gameDAO;
    private final GameJsonDAO gameJsonDAO;

    /**
     * Constructor of the class. Initializes the gameDAO and the gameJson.
     */
    public GameManager() {
        this.gameDAO = new SQLGameDAO(new SQLConnector());
        this.gameJsonDAO = new GameJsonDAO();
    }

    /**
     * @return The names of the game NOT DEFINED + index
     */
    public String getNotDefinedName() {
        ArrayList<String> games = gameDAO.getAllGamesNames();
        System.out.println();
        int notDefinedGames = 0;
        for (String gameName: games) {
            if(gameName.contains(NOT_DEFINED)) {
                notDefinedGames++;
            }
        }
        return NOT_DEFINED + " " + (notDefinedGames + 1);
    }

    /**
     * @param gamename The name of the game
     * @param date The date of the game
     * @param state The state of the game
     * @param time The time of the game
     * @param jsonName The name of the json file
     * @param username The name of the player
     */
    public void createGame(String gamename, String date, String state, String time, String jsonName, String username, String hits) {
        this.gameDAO.insertGame(gamename, date, state, time, jsonName, username, hits);
    }

    /**
     * @param fileName The name of the file
     * @return The players of the game
     */
    public ArrayList<AIPlayer> loadGame(String fileName){
        return gameJsonDAO.loadGame(fileName);
    }

    /**
     * @param name The name of the game
     * @param players The players of the game
     *                Saves the game in a json file
     */
    public void saveGame(String name, ArrayList<Player> players) {
        gameJsonDAO.saveGame(players, name);
    }

    /**
     * @param name The name of the player
     * @return The names of the games that the player has played but not ended
     */
    public ArrayList<String> getGameNames(String name) {
        return gameDAO.getGamesNamesNotEnded(name);
    }

    /**
     * @param selectedItem The name of the game
     * @return The path of the game
     */
    public String getFilePath(String selectedItem) {
        return gameDAO.getFilePath(selectedItem);
    }

    /**
     * @param gameName The name of the game
     * @return The current state of the game.
     */
    public String getCurrentTime(String gameName) {
        return gameDAO.getCurrentTime(gameName);
    }


    /**
     * @param gameName The name of the game
     *                 Updates the state of the game to "Ended"
     */
    public void finishGame(String gameName) {
        String newName = gameName;
        if(!gameName.contains(NOT_DEFINED)) {
            newName = getNotDefinedName();
            gameDAO.updateGameName(gameName, newName);
        }
        gameDAO.EndGame(newName);
        if(!gameName.contains(NOT_DEFINED)) {
            gameJsonDAO.deleteGame(gameDAO.getFilePath(newName));
        }
    }

    /**
     * @param name The name of the game
     * @param time The time of the game
     * @param path The path of the game
     * @param gameName The name of the game to update
     */
    public void updateGame(String name, String time, String path, String gameName) {
        gameDAO.updateGame(name, time, path, gameName, new Date().toString());
    }

    /**
     * @param name The name of the game
     * @return True if the game is in the DB, false otherwise
     */
    public boolean checkGameName(String name) {
        ArrayList<String> games = gameDAO.getAllGamesNames();
        for (String gameName: games) {
            gameName = gameName.toLowerCase();
            name = name.toLowerCase();
            if(gameName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param currentUser The username of the player
     *                    Deletes all the games of the player
     */
    public void deleteAllGames(String currentUser) {
        ArrayList<String> games = gameDAO.getGameNames(currentUser);
        for (String gameName: games) {
            gameJsonDAO.deleteGame(gameDAO.getFilePath(gameName));
            gameDAO.deleteGame(gameName);
        }
    }

    /**
     * @param currentGameName The name of the game
     *                        Adds a hit to the game
     */
    public synchronized void addHitToGame(String currentGameName) {
        gameDAO.addHitToGame(currentGameName);
    }

    /**
     * @param username The username of the player
     * @return The number of hits of the player.
     */
    public ArrayList<String> getStatsHits(String username) {
        return gameDAO.getAllHits(username);
    }

    /**
     * @param currentGameName The name of the game
     * @return The number of hits of the game
     */
    public String getHits(String currentGameName) {
        return Integer.toString(gameDAO.getHitsPerGame(currentGameName));
    }

    /**
     * @param currentGameName The name of the game
     * @param originalHits The original number of hits of the game
     *                     Updates the number of hits of the game
     */
    public void updateHits(String currentGameName, int originalHits) {
        gameDAO.updateHits(currentGameName, originalHits);
    }
}
