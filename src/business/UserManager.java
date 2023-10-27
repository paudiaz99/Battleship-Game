package business;

import persistence.SQLConnector;
import persistence.SQLUserDAO;
import persistence.UserDAO;

import java.util.ArrayList;

/**
 * Class that manages the users. It allows to create, delete and check users in the database.
 */
public class UserManager {
    /**
     * UserDAO to access the database.
     */
    private final UserDAO userDAO;

    /**
     * Constructor of the UserManager. It creates a new SQLUserDAO to access the database.
     */
    public UserManager() {
        this.userDAO = new SQLUserDAO(new SQLConnector());
    }

    /**
     * @param username username to create
     * @param mail mail to associate to the username
     * @param password password to associate to the username
     *                 Create a new user with the username, mail and password given in parameter
     */
    public void createUser(String username, String mail, String password) {
        this.userDAO.insertUser(username, mail, password);
    }

    /**
     * @param username username to delete
     *                 Delete the user with the username given in parameter
     */
    public void deleteUser(String username) {
        this.userDAO.deleteUser(username);

    }

    /**
     * @param username username to check
     * @param password password to check
     * @return true if the username or mail and the password are valid for login.
     */
    public boolean checkUser(String username, String password) {
        return this.userDAO.checkUser(username, password);
    }

    /**
     * @param username username to check
     * @param email email to check
     * @return true if the username and the email are valid (not already used).
     */
    public boolean checkRegister(String username, String email) {
        return this.userDAO.checkUserName(username, email);
    }

    /**
     * @param passwordToRegister password to check
     * @return 0 if the password is valid, 1 if the password is invalid
     */
    public int checkPassword(String passwordToRegister){
        int error = 0;
        // q tingui maj min num i 8 caracters
        if(passwordToRegister.length()< 8){
            error=1;
        }
        if(passwordToRegister.equals(passwordToRegister.toLowerCase())){
            error=1;
        }
        if(passwordToRegister.equals(passwordToRegister.toUpperCase())){
            error=1;
        }
        if(!passwordToRegister.matches(".*[0-9].*")){
            error=1;
        }
        return error;
    }

    /**
     * @param email email to check
     * @return true if the email is valid
     */
    public boolean checkEmail(String email){
        return (email.contains("@") && email.contains("."));

    }

    /**
     * @param userToRegister username to check
     * @return true if the username is empty
     */
    public boolean checkUser(String userToRegister){
        if(userToRegister.length()< 1){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param user user to add a game to
     *             Add a game to the user given in parameter
     */
    public void addWin(User user) {
        this.userDAO.addWin(user.getName());
    }

    /**
     * @param currentUser user to add a game to
     *                    Add a game to the user given in parameter
     */
    public void addGameToHistory(User currentUser) {
        this.userDAO.addGameToHistory(currentUser.getName());
    }

    /**
     * @param currentUser user to get the stats from
     * @return an array containing the number of wins and the number of games played by the user given in parameter
     */
    public int[] getStats(String currentUser) {
        return new int[]{this.userDAO.getWins(currentUser), this.userDAO.getGamesPlayed(currentUser)};
    }

    /**
     * @param username username or mail to get the name from
     * @return the name of the user with the username or mail given in parameter
     */
    public String getPlayerName(String username) {
        return this.userDAO.getPlayerName(username);
    }

    /**
     * @param currentUser user to reset.
     *                    Reset the user given in parameter. This method is used when the user wants to delete his account
     *                    All information stored in RAM is deleted
     */
    public void resetUser(User currentUser) {
        currentUser.reset();
    }

    /**
     * @param user user to get the name from
     * @return the name of the user given in parameter
     */
    public String getName(User user) {
        return user.getName();
    }

    /**
     * @return an ArrayList containing all the usernames in the database
     */
    public ArrayList<String> getAllUsernames() {
        return this.userDAO.getAllUsernames();
    }
}
