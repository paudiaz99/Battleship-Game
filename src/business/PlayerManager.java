package business;

/**
 * This class is responsible for managing the player
 */
public class PlayerManager {

    /**
     * @param currentPlayer the player to get the game board from
     * @return the game board of the player
     */
    public GameBoard getGameBoard(Player currentPlayer) {
       return currentPlayer.getGameBoard();
    }

    /**
     * @param currentPlayer the player to reset
     */
    public void resetPlayer(Player currentPlayer) {
        currentPlayer.reset();
    }
}
