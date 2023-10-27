package presentation.view;

import presentation.PlayController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Panel where the game board is placed. This panel is used to place the ships and to play the game.
 */
public class GameBoardPanel extends JPanel {
    /**
     * Number of columns
     */
    private final int GRID_SIZE = 15;
    /**
     * Number of rows
     */
    private final int GRID_HEIGHT = 15;
    /**
     * Width of the grid
     */
    private final int GRID_WIDTH = 200;
    /**
     * Height of the grid
     */
    private final int GRID_HEIGHT_SIZE = 200;
    /**
     * Grid panel where the cells are placed
     */
    private JPanel gridPanel;
    /**
     * Button to move submarine
     */
    private JButton submarineButton;
    /**
     * Button to move destroyer
     */
    private JButton destroyerButton;
    /**
     * Button to move boat
     */
    private JButton boatButton;
    /**
     * Button to move aircraft carrier
     */
    private JButton aircraftCarrierButton;
    /**
     * Confirmed coordinates of the ships
     */
    private final ArrayList<int[][]> confirmedCoordinates = new ArrayList<>();

    /**
     * Index of the selected ship. This index is used to get the ship from the GameModel.
     */
    private int SELECTED_SHIP_INDEX = 0;

    public final static String SUBMARINE_BUTTON = "SUBMARINE_BUTTON";
    public final static String DESTROYER_BUTTON = "DESTROYER_BUTTON";
    public final static String BOAT_BUTTON = "BOAT_BUTTON";
    public final static String AIRCRAFT_CARRIER_BUTTON = "AIRCRAFT_CARRIER_BUTTON";
    public final static String PREPARATION_MODE = "PREPARATION_MODE";
    public final static String PLAY_MODE = "PLAY_MODE";
    /**
     * Panel where the buttons are placed.
     */
    private JPanel buttonsPanel;

    /**
     * Constructor of the class. Configures the panel and its components.
     */
    public GameBoardPanel() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT_SIZE));
        configureGrid();
        configureButtons();
    }

    /**
     * Configures the button panel
     */
    private void configureButtons() {
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonsPanel.setPreferredSize(new Dimension(GRID_WIDTH, 50));
        submarineButton = new JButton("Submarine");
        destroyerButton = new JButton("Destroyer");
        boatButton = new JButton("Boat");
        aircraftCarrierButton = new JButton("Air. Carrier");


        submarineButton.setActionCommand(SUBMARINE_BUTTON);
        destroyerButton.setActionCommand(DESTROYER_BUTTON);
        boatButton.setActionCommand(BOAT_BUTTON);
        aircraftCarrierButton.setActionCommand(AIRCRAFT_CARRIER_BUTTON);
        submarineButton.setFocusable(false);
        destroyerButton.setFocusable(false);
        boatButton.setFocusable(false);
        aircraftCarrierButton.setFocusable(false);
        buttonsPanel.add(submarineButton);
        buttonsPanel.add(destroyerButton);
        buttonsPanel.add(boatButton);
        buttonsPanel.add(aircraftCarrierButton);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     *  Configures the grid panel
     */
    private void configureGrid() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_HEIGHT));
        gridPanel.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT_SIZE));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create border for each cell and make cells perfect squares
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                ClickyCell cell = new ClickyCell(i, j);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setPreferredSize(new Dimension(GRID_WIDTH / GRID_SIZE, GRID_HEIGHT_SIZE / GRID_HEIGHT));
                gridPanel.add(cell);
            }
        }
        gridPanel.setFocusable(true);
        this.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * @param coordinates The coordinates of the ship to paint
     *                    Paints the ship with the given coordinates
     */
    public void paintShip(int[][] coordinates){
        if(coordinates != null) {
            boolean isVertical = coordinates[1][0] == coordinates[1][1];
            int size;
            if (isVertical) {
                size = Math.abs(coordinates[0][1] - coordinates[0][0]) + 1;
            } else {
                size = Math.abs(coordinates[1][1] - coordinates[1][0]) + 1;
            }
            // Paint the ship
            for (int i = 0; i < size; i++) {
                ClickyCell cell;
                if (isVertical) {
                    // Get the cell by its two coordinates
                    cell = (ClickyCell) gridPanel.getComponent(coordinates[1][0] * GRID_SIZE + coordinates[0][0] + i);
                } else {
                    cell = (ClickyCell) gridPanel.getComponent(coordinates[1][0] * GRID_SIZE + coordinates[0][0] + i * GRID_SIZE);
                }
                cell.setBackground(Color.BLACK);
            }
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * @param listener The listener to add to the buttons
     *                 Adds the given listener to the buttons
     */
    public void addActionListener(ActionListener listener) {
        submarineButton.addActionListener(listener);
        destroyerButton.addActionListener(listener);
        boatButton.addActionListener(listener);
        aircraftCarrierButton.addActionListener(listener);
    }

    /**
     * Resets the grid by removing all confirmed ships
     */
    public void reset() {
        boolean delete;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                // Check if the cell is part of a confirmed ship
                delete = this.checkConfirmedCoordinates(i, j);
                if (delete) {
                    ClickyCell cell = (ClickyCell) gridPanel.getComponent(i * GRID_SIZE + j);
                    cell.setBackground(null);
                }
            }
        }
    }

    /**
     * @param i The row of the cell
     * @param j The column of the cell
     * @return True if the cell is not part of a confirmed ship, false otherwise
     */
    private boolean checkConfirmedCoordinates(int i, int j) {
        for (int[][] coordinates : confirmedCoordinates) {
            // Check if the coordinates are vertical or horizontal
            boolean isVertical = coordinates[0][0] == coordinates[0][1];
            int size;
            if (isVertical) {
                size = Math.abs(coordinates[1][1] - coordinates[1][0]) + 1;
            } else {
                size = Math.abs(coordinates[0][1] - coordinates[0][0]) + 1;
            }
            // Check if the cell is part of a confirmed ship
            for (int k = 0; k < size; k++) {
                if (isVertical) {
                    if (j == coordinates[0][0] && i == Math.min(coordinates[1][0], coordinates[1][1]) + k) {
                        return false;
                    }
                } else {
                    if (j == Math.min(coordinates[0][0], coordinates[0][1]) + k && i == coordinates[1][0]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @return The confirmed coordinates
     *
     */
    public ArrayList<int[][]> getConfirmedCoordinates() {
        return confirmedCoordinates;
    }

    /**
     * @param dropShip The ship to add to the confirmed coordinates
     *                 Adds a ship to the confirmed coordinates
     */
    public void dropShip(int[][] dropShip) {
        this.confirmedCoordinates.add(dropShip);
    }

    /**
     * Focuses the grid to allow for keyboard input
     */
    public void focus() {
        this.requestFocus();
    }

    /**
     * @param freeShip The ship to remove from the confirmed coordinates
     *                 Removes a ship from the confirmed coordinates
     *
     */
    public void removeConfirmedCoordinates(int[][] freeShip) {
        for (int[][] coordinates : confirmedCoordinates) {
            if (Arrays.deepEquals(coordinates, freeShip)) {
                confirmedCoordinates.remove(coordinates);
                break;
            }
        }
    }

    /**
     * @return True if all ships have been confirmed
     */
    public boolean allShipsConfirmed() {
        return confirmedCoordinates.size() == 5;
    }

    /**
     * @param controller The controller that will handle when a cell is clicked
     */
    public void activateCells(PlayController controller){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                ClickyCell cell = (ClickyCell) gridPanel.getComponent(i * GRID_SIZE + j);
                cell.activateCell(controller);
            }
        }
    }

    /**
     * @param pos The position of the cell that was hit
     */
    public void hit(int[] pos, Color color) {
        ClickyCell cell = (ClickyCell) gridPanel.getComponent(pos[0] * GRID_SIZE + pos[1]);
        cell.hitShip(color);
        this.revalidate();
        this.repaint();
    }

    /**
     * @param pos   The position of the cell that was missed
     * @param color The color of the cell that was missed
     */
    public void miss(int[] pos, Color color) {
        ClickyCell cell = (ClickyCell) gridPanel.getComponent(pos[0] * GRID_SIZE + pos[1]);
        cell.missShip(color);
        this.revalidate();
        this.repaint();
    }

    /**
     * Removes the buttons from the panel
     */
    public void removeButtons(){
        // Remove buttons from panel
        this.remove(buttonsPanel);
    }

    /**
     * Resets the grid and the confirmed coordinates.
     */
    public void resetAll() {
        this.confirmedCoordinates.clear();
        this.reset();
        this.SELECTED_SHIP_INDEX = 0;
    }

    /**
     * @return The index of the selected ship
     */
    public int getShipIndex() {
        return SELECTED_SHIP_INDEX;
    }

    /**
     * Increments the ship index if it is less than 5
     */
    public void checkIndex() {
        if (SELECTED_SHIP_INDEX < 5) {
            SELECTED_SHIP_INDEX++;
        }
    }
}