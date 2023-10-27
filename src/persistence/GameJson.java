package persistence;

import business.AIPlayer;
import business.Player;

import java.util.ArrayList;

/**
 * This Interface is used to communicate with the json files of the games
 */
public interface GameJson {
    void createJsonFile(String fileName);
    ArrayList<AIPlayer> loadGame(String filepath);
    void saveGame(ArrayList<Player> game, String name);
    void deleteGame(String filepath);

}
