package persistence;

import business.AIPlayer;
import business.Player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This class is responsible for the persistence of the game in a json file
 */
public class GameJsonDAO implements GameJson{

    /**
     * @param fileName the name of the file to be created
     *                 Creates a new empty json file with the given name
     */
    public void createJsonFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode emptyJson = objectMapper.createObjectNode();
        String filePath = (Path.of("files", fileName + ".json")).toString();

        try {
            objectMapper.writeValue(new File(filePath), emptyJson);
        } catch (IOException e) {
            System.err.println("Error creating file");
        }
    }

    /**
     * @param filepath the path of the file to be loaded
     * @return the game loaded from the file
     */
    public ArrayList<AIPlayer> loadGame(String filepath) {
        FileReader reader = null;
        try {
            reader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            System.err.println("Error loading game");
        }
        ArrayList<AIPlayer> games = new Gson().fromJson(reader, new TypeToken<ArrayList<AIPlayer>>() {}.getType());

        if(games == null){
            games = new ArrayList<>();
        }
        return games;
    }

    /**
     * @param game the game to be saved
     * @param name the name of the file to be saved
     */
    public void saveGame(ArrayList<Player> game, String name) {
        createJsonFile(name);
        String filepath = Path.of("files", name + ".json").toString();
        FileWriter writer = null;
        try {
            writer = new FileWriter(filepath);
        } catch (IOException e) {
            System.err.println("Error saving game");
        }
        new Gson().toJson(game,writer);
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
    }

    /**
     * @param filePath the path of the file to be deleted
     *                 Deletes the file at the given path
     */
    public void deleteGame(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
