package business;

import java.util.ArrayList;

/**
 * Class to manage the GameBoard entities.
 */
public class GameBoardManager {
    /**
     * ShipManager instance to create ships and manage them.
     */
    private final ShipManager shipManager;

    /**
     * Constructor for GameBoardManager. Initializes the ShipManager.
     */
    public GameBoardManager() {
        shipManager = new ShipManager();
    }

    /**
     * @param gameBoard GameBoard to initialize.
     *                  Initializes the GameBoard with the default ships.
     */
    public void initBoard(GameBoard gameBoard) {
        // Creates x2 Submarines, x1 Boat, x1 Destroyer, x1 Aircraft Carrier
        gameBoard.addBoat(shipManager.createShip(Ship.SUBMARINE_1));
        gameBoard.addBoat(shipManager.createShip(Ship.SUBMARINE_2));
        gameBoard.addBoat(shipManager.createShip(Ship.BOAT));
        gameBoard.addBoat(shipManager.createShip(Ship.DESTROYER));
        gameBoard.addBoat(shipManager.createShip(Ship.AIRCRAFT_CARRIER));
    }

    /**
     * @param gameBoard GameBoard to get the ship from.
     * @param index    Index of the ship to get.
     * @return Ship at the specified index.
     */
    public Ship getShip(GameBoard gameBoard, int index) {
        return shipManager.getShipToMove(gameBoard.getShips(), index);
    }

    /**
     * @param gameBoard GameBoard to get the ship from.
     * @param shipName Name of the ship to get.
     * @return Ship with the specified name.
     */
    public Ship getShipByName(GameBoard gameBoard, String shipName) {
        return gameBoard.getShipByName(shipName);
    }

    /**
     * @param gameBoard GameBoard to get the ship from.
     * @return True if all ships are placed, false otherwise.
     */
    public boolean allShipsPlaced(GameBoard gameBoard) {
        for (Ship ship: gameBoard.getShips()){
            if (!shipManager.isPositioned(ship)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param gameBoard GameBoard to get the ship from.
     * @param pos     Position to check.
     * @return True if the position is occupied by a ship, false otherwise.
     */
    public boolean checkHit(GameBoard gameBoard, int[] pos, int shooterIndex) {
        gameBoard.shoot(pos, shooterIndex);
        for (Ship ship: gameBoard.getShips()){
            if (shipManager.checkHit(ship, pos)){
                gameBoard.hit(pos, shooterIndex);
                return true;
            }
        }
        gameBoard.miss(pos, shooterIndex);
        return false;
    }

    /**
     * @return GameBoard with all ships randomly placed.
     * Generates a random GameBoard with all ships randomly placed.
     */
    public GameBoard generateRandomGameBoard(){
        // Places all ships randomly on the board
        GameBoard gameBoard = new GameBoard();
        ArrayList<int[]> occupiedCoordinates = new ArrayList<>();
        initBoard(gameBoard);
        for (Ship ship: gameBoard.getShips()){
            shipManager.randomlyPositionShip(ship, occupiedCoordinates);
            occupiedCoordinates.addAll(shipManager.getCellCoordinates(shipManager.getCoordinates(ship)));
        }
        return gameBoard;
    }

    /**
     * @param gameBoard GameBoard to check.
     * @return True if all ships are destroyed, false otherwise.
     */
    public boolean checkEndGame(GameBoard gameBoard) {
        return gameBoard.checkEndGame();
    }

    /**
     * @param gameBoard GameBoard to check.
     * @param pos    Position to check.
     * @return True if the position has been already shot, false otherwise.
     */
    public boolean checkShot(GameBoard gameBoard, int[] pos) {
        return gameBoard.checkShot(pos);
    }

    /**
     * @param gameBoard GameBoard to check.
     * @param hitCells Cells that have been hit.
     * @return True if a ship has been sunk, false otherwise.
     */
    public boolean checkSunk(GameBoard gameBoard, ArrayList<int[]> hitCells) {
        for (int i = 0; i < gameBoard.getShips().size(); i++){
            if (shipManager.checkSunk(gameBoard.getShips().get(i), hitCells)){
                return true;
            }
        }
        return false;
    }
    /**
     * @param gameBoard GameBoard to add the hit to.
     *                  Adds a hit to the GameBoard.
     */
    public void addHit(GameBoard gameBoard) {
        gameBoard.addHit();
    }

    /**
     * @param gameBoard GameBoard to check.
     * @param i  Index of the ship to check.
     * @return True if the ship is positioned, false otherwise.
     */
    public boolean checkShipPositioned(GameBoard gameBoard, int i) {
        return shipManager.isPositioned(gameBoard.getShips().get(i));
    }

    /**
     * @param gameBoard GameBoard to check.
     * @param i   Index of the ship to check.
     * @return True if the ship is alive, false otherwise.
     */
    public boolean checkShipAlive(GameBoard gameBoard, int i) {
        return !gameBoard.getShips().get(i).isDestroyed();
    }

    /**
     * @param gameBoard GameBoard to get the hit shots from.
     * @return ArrayList of hit shots.
     */
    public ArrayList<int[]> getHitShots(GameBoard gameBoard) {
        return gameBoard.getHitShots();
    }

    /**
     * @param gameBoard GameBoard to get the miss shots from.
     * @return ArrayList of miss shots.
     */
    public ArrayList<int[]> getMissShots(GameBoard gameBoard) {
        return gameBoard.getMissShots();
    }
}
