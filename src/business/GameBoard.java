package business;

import java.util.ArrayList;

/**
 * This class represents the gameBoard of a player. It contains the ships and the shots.
 */
public class GameBoard {
    private static final int MAX_HITS = 17;
    /**
     * The ships of the gameBoard
     */
    private final ArrayList<Ship> ships;
    /**
     * The shots of the gameBoard
     */
    private final ArrayList<int[]> hitsCells;
    private final ArrayList<int[]> missesCells;
    private final ArrayList<int[]> shots;
    private int hits;

    /**
     * Constructor of the class. Initializes the ships and shots.
     */
    public GameBoard() {
        ships = new ArrayList<>();
        hitsCells = new ArrayList<>();
        missesCells = new ArrayList<>();
        shots = new ArrayList<>();
        hits = 0;
    }

    /**
     * @param ship The ship to add to the gameBoard
     *             Adds a ship to the gameBoard
     */
    public void addBoat(Ship ship) {
        this.ships.add(ship);
    }

    /**
     * @return The ships of the gameBoard
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * @param aircraftCarrier The name of the ship to get
     * @return The ship with the given name
     */
    public Ship getShipByName(String aircraftCarrier) {
        for (Ship ship:ships) {
            if (ship.getName().equals(aircraftCarrier)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * @param pos The position to check
     * @return True if the position is already shot, false otherwise
     */
    public boolean checkShot(int[] pos) {
        for (int[] shot: shots) {
            if (shot[0] == pos[0] && shot[1] == pos[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param pos The position to shoot
     *            Adds a shot to the gameBoard.
     */
    public void shoot(int[] pos, int shooterIndex) {
        pos[2] = shooterIndex;
        shots.add(pos);
    }

    /**
     * @return Checks if the number of hits is 17, which means that all the ships have been sunk.
     */
    public boolean checkEndGame() {
        return hits == MAX_HITS;
    }

    /**
     * Adds a hit to the gameBoard.
     */
    public void addHit() {
        hits++;
    }

    /**
     * @param pos The position to add to the hit shots
     * @param shooterIndex The index of the player that shot
     */
    public void hit(int[] pos, int shooterIndex) {
        pos[2] = shooterIndex;
        hitsCells.add(pos);
    }

    /**
     * @param pos The position to add to the missed shots
     * @param shooterIndex The index of the player that shot
     */
    public void miss(int[] pos, int shooterIndex) {
        pos[2] = shooterIndex;
        missesCells.add(pos);
    }

    /**
     * @return The hit shots of the gameBoard
     */
    public ArrayList<int[]> getHitShots() {
        return hitsCells;
    }

    /**
     * @return The missed shots of the gameBoard
     */
    public ArrayList<int[]> getMissShots() {
        return missesCells;
    }
}
