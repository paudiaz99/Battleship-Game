package business;

import java.util.ArrayList;

/**
 * ShipManager class. This class is responsible for managing the ships.
 */
public class ShipManager {
    /**
     * @param ship - ship to get the coordinates from.
     * @return an array with the coordinates of the ship.
     */
    public int[][] getCoordinates(Ship ship){
        if(ship != null) {
            int[][] coordinates = new int[2][2];
            coordinates[0] = ship.getX();
            coordinates[1] = ship.getY();
            return coordinates;
        }else {
            return null;
        }
    }

    /**
     * @param direction - direction to move the ship
     * @param ship - ship to be moved
     *             Moves the ship in the given direction.
     */
    public void moveShip(String direction, Ship ship) {
        if(ship != null){
            if(!ship.isPositioned()) {
                switch (direction) {
                    case Ship.UP -> ship.moveUp();
                    case Ship.DOWN -> ship.moveDown();
                    case Ship.LEFT -> ship.moveLeft();
                    case Ship.RIGHT -> ship.moveRight();
                    case Ship.ROTATE -> ship.rotate();
                }
            }
        }

    }

    /**
     * @param selectedShip - ship to be dropped
     *                     Sets the ship as positioned.
     */
    public void postShip(Ship selectedShip) {
        selectedShip.setPositioned();
    }

    /**
     * @param name - name of the ship
     * @return a new ship with the given name
     */
    public Ship createShip(String name) {
        return new Ship(name);
    }

    /**
     * @param ship - ship to be checked.
     * @return true if the ship is positioned.
     */
    public boolean isPositioned(Ship ship) {
        return ship.isPositioned();
    }

    /**
     * @param confirmedCoordinates - coordinates of the cells of the ships already positioned
     * @param selectedShip - coordinates of the ship to be positioned
     * @return true if the ship is positioned in a valid position (one coordinate away from another ship)
     */
    public boolean confirmShip(ArrayList<int[]> confirmedCoordinates, int[][] selectedShip) {
        // Check if the ship is positioned in a valid position (one coordinate away from another ship)
        ArrayList<int[]> cellCoordinates = getCellCoordinates(selectedShip);
        for(int[] coordinate : confirmedCoordinates) {
            for(int[] cellCoordinate : cellCoordinates) {
                // Check if arrays are equal
                if((coordinate[0] == cellCoordinate[0] && coordinate[1] == cellCoordinate[1]) || (coordinate[0] == cellCoordinate[0] && coordinate[1] == cellCoordinate[1]+1) || (coordinate[0] == cellCoordinate[0] && coordinate[1] == cellCoordinate[1]-1) || (coordinate[0] == cellCoordinate[0]+1 && coordinate[1] == cellCoordinate[1]) || (coordinate[0] == cellCoordinate[0]-1 && coordinate[1] == cellCoordinate[1]) || (coordinate[0] == cellCoordinate[0]+1 && coordinate[1] == cellCoordinate[1]+1) || (coordinate[0] == cellCoordinate[0]-1 && coordinate[1] == cellCoordinate[1]-1) || (coordinate[0] == cellCoordinate[0]+1 && coordinate[1] == cellCoordinate[1]-1) || (coordinate[0] == cellCoordinate[0]-1 && coordinate[1] == cellCoordinate[1]+1)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param shipCoordinates - coordinates of the ship
     * @return ArrayList of coordinates of the cells of the ship.
     * This function traduces the coordinates of the ship to the coordinates of the cells of the ship.
     */
    public ArrayList<int[]> getCellCoordinates(int[][] shipCoordinates) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        int x1 = shipCoordinates[0][0];
        int y1 = shipCoordinates[1][0];
        int x2 = shipCoordinates[0][1];
        int y2 = shipCoordinates[1][1];
        if(x1 == x2){
            int size = y2 - y1 + 1;
            for (int i = 0; i < size; i++) {
                int[] aux_coordinate = new int[2];
                aux_coordinate[0] = x1;
                if(y1 < y2)
                    aux_coordinate[1] = y1 + i;
                else
                    aux_coordinate[1] = y2 - i;
                coordinates.add(aux_coordinate);
            }

        }
        else if(y1 == y2){
            int size = x2 - x1 + 1;
            for (int i = 0; i < size; i++) {
                int[] aux_coordinate = new int[2];
                aux_coordinate[1] = y1;
                if(x1 < x2)
                    aux_coordinate[0] = x1 + i;
                else
                    aux_coordinate[0] = x2 - i;
                coordinates.add(aux_coordinate);
            }
        }
        return coordinates;
    }

    /**
     * @param selectedShip Ship to free
     *                     Sets free the ship
     */
    public void freeShip(Ship selectedShip) {
        selectedShip.setFree();
    }

    /**
     * @param ships List of ships
     * @param index Index of the ship to move
     * @return Ship to move
     */
    public Ship getShipToMove(ArrayList<Ship> ships, int index) {
        if(index == 5){
            // If index is 5 (all ships are placed), check if there is a ship that is not placed
            for (Ship ship:ships) {
                if (!ship.isPositioned()) {
                    return ship;
                }
            }
            return null;
        }else {
            return ships.get(index);
        }
    }

    /**
     * @param ship Ship to check
     * @param pos Position to check
     * @return True if the ship is hit, false otherwise
     */
    public boolean checkHit(Ship ship, int[] pos) {
        ArrayList<int[]> coordinates = getCellCoordinates(getCoordinates(ship));
        for(int[] coordinate : coordinates) {
            if(coordinate[0] == pos[1] && coordinate[1] == pos[0]) {
                ship.hit();
                return true;
            }
        }
        return false;
    }

    /**
     * @param ship Ship to randomly position
     * @param occupiedCoordinates Coordinates of the occupied cells
     */
    public void randomlyPositionShip(Ship ship, ArrayList<int[]> occupiedCoordinates) {
        do {
            ship.generateRandomPosition();
        }while (!confirmShip(occupiedCoordinates, getCoordinates(ship)));
    }

    /**
     * @param ship Ship to check
     * @return True if the ship is destroyed, false otherwise
     */

    public boolean checkSunk(Ship ship, ArrayList<int[]> hitCells) {
        ArrayList<int[]> coordinates = getCellCoordinates(getCoordinates(ship));
        if(coordinates.size() != hitCells.size()) {
            return false;
        }else {
            boolean contains;
            for (int[] coordinate : coordinates) {
                contains = false;
                for (int[] hitCell : hitCells) {
                    if ((coordinate[0] == hitCell[1] && coordinate[1] == hitCell[0])) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    return false;
                }
            }
            return true;
        }
    }
}
