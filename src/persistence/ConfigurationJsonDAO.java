package persistence;

import business.Config;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

/**
 * Class that implements the ConfigurationDAO interface. It reads the configuration from a json file.
 */
public class  ConfigurationJsonDAO implements ConfigurationDAO{
    private Path filePath;

    public ConfigurationJsonDAO() {
        String fileName = "config.json";
        String folder = "files";
        filePath = Path.of(folder, fileName);
    }

    /**
     * @return the configuration in a String
     */
    @Override
    public String readConfig() {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath.toString());
            Config config = new Gson().fromJson(reader, Config.class);
            return config.getDBUser() + "-" + config.getPassword() + "-" + config.getIp()+"-"+config.getDb_port()+"-"+config.getDb_name();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the file --> " + filePath.toString() + " (" + e.getMessage() + ")");
        }
        return null;
    }

    /**
     * @return the recharge time in milliseconds
     *
     */
    @Override
    public int getTime() {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath.toString());
            Config config = new Gson().fromJson(reader, Config.class);
            return config.getTime();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the file --> " + filePath.toString() + " (" + e.getMessage() + ")");
        }
        return 0;
    }
}
