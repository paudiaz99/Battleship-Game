package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * This class represents a cell that can be clicked and its color ccn be changed.
 */
public class ClickyCell extends JPanel {
    /**
     * The row and column of the cell
     */
    private final int row;
    /**
     * The row and column of the cell
     */
    private final int col;

    /**
     * @param row the row of the cell
     * @param col the column of the cell
     *            This constructor creates a cell that can be clicked
     */
    public ClickyCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @param controller the controller to make the cell clickable
     */
    public void activateCell(MouseListener controller){
        this.addMouseListener(controller);
    }

    public int[] getPos() {
        return new int[]{row, col, -1};
    }

    /**
     * @param color the color to set the cell to
     *              This method is called when the user clicks on a cell and hits the ship
     */
    public void hitShip(Color color) {
        this.setBackground(color);
        this.revalidate();
        this.repaint();
    }

    /**
     * This method is called when the user clicks on a cell and misses the ship
     */
    public void missShip(Color color) {
        this.setBackground(color);
        this.revalidate();
        this.repaint();
    }
}
