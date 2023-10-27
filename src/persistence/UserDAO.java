package persistence;

import java.util.ArrayList;

/**
 * Interface for the UserDAO. It contains the methods to insert, delete and check a user.
 */
public interface UserDAO {

    void insertUser(String username, String email, String password);

    boolean deleteUser(String username);

    boolean checkUser(String username, String password);

    boolean checkUserName(String username, String email);

    int getWins(String username);

    int getGamesPlayed(String username);

    void addWin(String username);

    void addGameToHistory(String name);

    String getPlayerName(String username);

    ArrayList<String> getAllUsernames();
}
