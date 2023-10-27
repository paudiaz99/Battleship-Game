package business;

/**
 * This class represents a player. It contains the gameBoard and the name of the player.
 */
public class Player {

    protected final GameBoard gameBoard;

    protected String name;

    /**
     * @param gameBoard The GameBoard of the player
     * @param name The name of the player
     *             Constructor of the class. Initializes the gameBoard and the name of the player.
     */
    public Player(GameBoard gameBoard, String name) {
        this.gameBoard = gameBoard;
        this.name = name;
    }

    /**
     * @return The GameBoard of the player
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Resets the name of the player
     */
    public void reset() {
        this.name = null;
    }
}
