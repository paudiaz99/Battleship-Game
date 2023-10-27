package business;

/**
 * This class represents a ship in the game. It has a name, a size, a number of hits, and a boolean that indicates whether it has been positioned or not.
 *
 */
public class Ship {
    public final static String UP = "UP";
    public final static String DOWN = "DOWN";
    public final static String LEFT = "LEFT";
    public final static String RIGHT = "RIGHT";

    public final static String BOAT = "BOAT";
    public final static String DESTROYER = "DESTROYER";
    public final static String AIRCRAFT_CARRIER = "AIRCRAFT_CARRIER";
    public final static String SUBMARINE_1 = "SUBMARINE_1";
    public final static String SUBMARINE_2 = "SUBMARINE_2";
    public static final String ROTATE = "ROTATE";
    private static final int MAX_BORDER = 14;
    private static final double MAX_SIZE = 15;

    /**
     * The size of the ship
     */
    private final int size;
    /**
     * The X1 coordinate of the ship
     */
    private int x_1;
    /**
     * The Y1 coordinate of the ship
     */
    private int y_1;
    /**
     * The X2 coordinate of the ship
     */
    private int x_2;
    /**
     * The Y2 coordinate of the ship
     */
    private int y_2;
    /**
     * Boolean that indicates whether the ship is vertical or horizontal
     */
    private boolean isVertical;
    /**
     * Boolean that indicates whether the ship has been positioned or not
     */
    private boolean positioned;
    /**
     * The name of the ship
     */
    private final String name;
    /**
     * The number of hits the ship has received
     */
    private int hits;

    /**
     * @param name the name of the ship
     *             Constructor for the Ship class. It initializes the ship's attributes according to its name.
     */
    public Ship(String name) {
        this.hits = 0;
        this.name = name;
        this.positioned = false;
        x_1 = 0;
        y_1 = 0;
        x_2 = 0;
        switch (name) {
            case BOAT -> y_2 = 1;
            case DESTROYER -> y_2 = 3;
            case AIRCRAFT_CARRIER -> y_2 = 4;
            case SUBMARINE_1, SUBMARINE_2 -> y_2 = 2;
        }

        isVertical = true;
        size = Math.abs(y_1 - y_2) + 1;
    }

    /**
     * @return the X coordinates of the ship
     */
    public int[] getX() {
        return new int[]{x_1, x_2};
    }

    /**
     * @return the Y coordinates of the ship
     */
    public int[] getY() {
        return new int[]{y_1, y_2};
    }

    /**
     * Moves the ship up
     */
    public void moveUp() {
        if(y_1 > 0) {
            y_1--;
            y_2--;
        }
    }
    /**
     * Moves the ship down
     */
    public void moveDown() {
        if(y_2 < MAX_BORDER) {
            y_1++;
            y_2++;
        }
    }
    /**
     * Moves the ship to the left
     */
    public void moveLeft() {
        if(x_1 > 0) {
            x_1--;
            x_2--;
        }
    }
    /**
     * Moves the ship to the right
     */
    public void moveRight() {
        if(x_2 < MAX_BORDER) {
            x_1++;
            x_2++;
        }
    }
    /**
     * Rotates the ship
     */
    public void rotate() {
        int aux = x_1;
        x_1 = y_1;
        y_1 = aux;
        aux = x_2;
        x_2 = y_2;
        y_2 = aux;
        isVertical = !isVertical;
    }

    /**
     * @return true if the ship is positioned
     */
    public boolean isPositioned() {
        return positioned;
    }

    /**
     * Sets the ship as positioned
     */
    public void setPositioned() {
        this.positioned = true;
    }

    /**
     * @return the name of the ship
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the ship as free
     */
    public void setFree() {this.positioned = false;}

    /**
     * Generates a random position for the ship
     */
    public void generateRandomPosition(){
        isVertical = Math.random() < 0.5;
        if (isVertical) {
            x_1 = (int) (Math.random() * MAX_SIZE);
            y_1 = (int) (Math.random() * (MAX_SIZE - size + 1));
            x_2 = x_1;
            y_2 = y_1 + size - 1;
        } else {
            x_1 = (int) (Math.random() * (MAX_SIZE - size + 1));
            y_1 = (int) (Math.random() * MAX_SIZE);
            x_2 = x_1 + size - 1;
            y_2 = y_1;
        }
    }

    /**
     * Increments the number of hits
     */
    public void hit () {
        hits++;
    }

    /**
     * @return true if the ship is destroyed
     */
    public boolean isDestroyed() {
        return hits == size;
    }
}
