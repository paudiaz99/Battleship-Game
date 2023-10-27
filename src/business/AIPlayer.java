package business;

import java.util.ArrayList;

/**
 * AIPlayer class. This class is used to represent an AI player. It contains the logic for the AI player.
 */
public class AIPlayer extends Player{
    private static final int MAX_BORDER = 14;
    private static final int MIN_BORDER = 0;
    /**
     * The next shot coordinates.
     */
    private int[] lastShot;
    /**
     * ArrayList that contains the coordinates of the cells that have been hit.
     */
    private ArrayList<int[]> hitCells;
    /**
     * Boolean that indicates if the AI is focusing on a ship.
     */
    private boolean focusingShip;
    /**
     * Boolean that indicates if the ship that the AI is focusing on is vertical.
     */
    private boolean isFocusedShipVertical;
    /**
     * Boolean that indicates if the ship that the AI is focusing up/down/left/right.
     */
    private int shotDirection;
    /**
     * ArrayList that contains the coordinates of the cells that have been missed.
     */
    private final ArrayList<int[]> missShots;
    /**
     * The number of ships that the AI has sunk.
     */
    private boolean lastHitMiss;
    /**
     * The coordinates of the first shot of the ship that the AI is focusing on.
     */
    private int[] firstShot;

    /**
     * Boolean that indicates if the AI player is random.
     */
    private final boolean random;
    private static final int MAX_SIZE = 15;

    /**
     * @param gameBoard The game board of the AI player.
     *                  Constructor for the AI player. Initializes the game board and the lists of shot cells.
     */
    public AIPlayer(GameBoard gameBoard, String name, boolean random) {
        super(gameBoard, name);
        this.focusingShip = false;
        this.hitCells = new ArrayList<>();
        this.missShots = new ArrayList<>();
        lastShot = new int[2];
        this.random = random;
    }

    /**
     * Generates new coordinates for the next shot. These are generated depending on the previous shots.
     * If the AI is not focusing on a ship, it will generate random coordinates.
     * If the AI is focusing on a ship, it will generate coordinates around the last hit.
     * If the AI is focusing on a ship and the last hit was a miss, it will change the direction of the shot.
     */
    public int[] generateNewShootCoordinates() {
        if (!focusingShip) {
            // Generate random coordinates
            int shotX = (int) (Math.random() * MAX_SIZE);
            int shotY = (int) (Math.random() * MAX_SIZE);
            firstShot = new int[]{shotX, shotY};
            lastShot[0] = shotX;
            lastShot[1] = shotY;
            return lastShot;
        } else {
            // Generate coordinates around the last hit
            int shotX;
            int shotY;
            if(hitCells.size() > 1){

                if(checkBorders(lastShot) || lastHitMiss || checkMissed(lastShot)){
                    changeDirection();
                    hitCells.remove(0);
                    if(lastHitMiss) {
                        hitCells.add(firstShot);
                    }else {
                        hitCells.add(firstShot);
                    }
                }

                shotX = hitCells.get(hitCells.size() - 1)[0];
                shotY = hitCells.get(hitCells.size() - 1)[1];

                lastShot = moveInDirection(shotX, shotY);
                return lastShot;
            }else{
                shotX = firstShot[0];
                shotY = firstShot[1];
                int [] nextShot = (moveRandomly(shotX, shotY));

                isFocusedShipVertical = hitCells.get(0)[0] == nextShot[0];

                if(isFocusedShipVertical){
                    shotDirection = hitCells.get(0)[1] < nextShot[1] ? 0 : 1;
                }else {
                    shotDirection = hitCells.get(0)[0] < nextShot[0] ? 2 : 3;
                }

                lastShot[0] = nextShot[0];
                lastShot[1] = nextShot[1];
                return lastShot;
            }
        }
    }

    /**
     * @return true if the shot provided has misses around it.
     */
    private boolean checkMissed(int[] lastShot) {
        for (int[] missedShot : missShots) {
            if (isFocusedShipVertical) {
                if (lastShot[1] - 1 == missedShot[1] && lastShot[0] == missedShot[0]) {
                    return true;
                } else if (lastShot[1] + 1 == missedShot[1] && lastShot[0] == missedShot[0]) {
                    return true;
                }
            } else {
                if (lastShot[0] - 1 == missedShot[0] && lastShot[1] == missedShot[1]) {
                    return true;
                } else if (lastShot[0] + 1 == missedShot[0] && lastShot[1] == missedShot[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return true if the player must change the direction of the shot due to borders.
     */
    private boolean checkBorders(int[] nextShot) {
        // Check if the last hit is on the border or has a miss shot next to it
        if(isFocusedShipVertical){
            return nextShot[1] == MIN_BORDER || nextShot[1] == MAX_BORDER;
        }else {
            return nextShot[0] == MIN_BORDER || nextShot[0] == MAX_BORDER;
        }
    }

    /**
     * @param shotX the x coordinate of the shot
     * @param shotY the y coordinate of the shot
     * @return the new coordinates of the shot generated by the direction of the ship.
     */
    private int[] moveInDirection(int shotX, int shotY) {
        switch (shotDirection){
            case 2 -> {
                if(shotX != MAX_BORDER){
                    shotX++;
                }
            }
            case 3 -> {
                if(shotX != MIN_BORDER){
                    shotX--;
                }
            }
            case 0 -> {
                if(shotY != MAX_BORDER){
                    shotY++;
                }
            }
            case 1 -> {
                if(shotY != MIN_BORDER){
                    shotY--;
                }
            }
        }
        return new int[]{shotX, shotY};
    }

    /**
     * @param shotX the x coordinate of the shot
     * @param shotY the y coordinate of the shot
     * @return the next shot of the player generated randomly,
     */
    private int[] moveRandomly(int shotX, int shotY) {
        int shotDirection = (int) (Math.random() * 4);

        switch (shotDirection) {
            case 0 -> {
                if (shotX != MAX_BORDER) {
                    shotX++;
                }
            }
            case 1 -> {
                if (shotX != MIN_BORDER) {
                    shotX--;
                }
            }
            case 2 -> {
                if (shotY != MAX_BORDER) {
                    shotY++;
                }
            }
            case 3 -> {
                if (shotY != MIN_BORDER) {
                    shotY--;
                }
            }
        }
        return new int[]{shotX, shotY};
    }

    /**
     * @param shot the coordinates of the shot
     *             Adds the shot to the hitCells list. If the AI is not focusing on a ship, it will start focusing on it.
     */
    public void hitsShip(int[] shot){
        if(!random) {
            if (!focusingShip) {
                focusingShip = true;
                hitCells.add(new int[]{shot[0], shot[1]});
                this.lastHitMiss = false;
            } else {
                this.lastHitMiss = false;
                hitCells.add(new int[]{shot[0], shot[1]});
            }
        }
    }

    /**
     * @param shot the coordinates of the shot
     *             Adds the shot to the missShots list.
     */
    public void missShip(int[] shot){
        missShots.add(new int[]{shot[0], shot[1]});
        lastHitMiss = true;
    }

    /**
     * Changes the direction of the shot.
     */
    private void changeDirection(){
        if(shotDirection == 3) {
            shotDirection = 2;
        }else if(shotDirection == 2){
            shotDirection = 3;
        }else if(shotDirection == 1){
            shotDirection = 0;
        }else if(shotDirection == 0){
            shotDirection = 1;
        }
    }

    /**
     * Resets the player's hit cells and sets the focusingShip to false.
     */
    public void sunk(){
        this.focusingShip = false;
        hitCells = new ArrayList<>();
    }

    /**
     * @return the list of the cells that the player has hit
     */
    public ArrayList<int[]> getHitCells() {
        return hitCells;
    }

    /**
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }
}
