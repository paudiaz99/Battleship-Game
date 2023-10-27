package presentation.view;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class BoardTablePanel. Extends JPanel. It is the panel that contains the grid and the table
 */
public class BoardTablePanel extends JPanel {
    private JStateTable table;
    private GameBoardPanel gameBoardPanel;

    /**
     * Constructor. Creates the panel with the grid and the table
     */
    public BoardTablePanel() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        configureGrid();

    }
    private void configureGrid() {
        table = new JStateTable();
        initGameboard();
        gameBoardPanel = new GameBoardPanel();

        gameBoardPanel.removeButtons();
        setColor(Color.GREEN);

        // Create a container panel with BoxLayout in Y_AXIS
        JPanel containerPanel;
        containerPanel = createBoardTablePanel(gameBoardPanel, table);
        containerPanel.setOpaque(false);
        this.add(containerPanel, BorderLayout.CENTER);
    }

    /**
     * @param gridPanel GameBoardPanel to add to the panel
     * @param table JStateTable to add to the panel
     * @return JPanel with the gridPanel and the table
     *
     */
    private JPanel createBoardTablePanel(GameBoardPanel gridPanel, JStateTable table) {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new FlowLayout());
        containerPanel.setOpaque(false);
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        gridPanel.setPreferredSize(new Dimension(50,50));
        containerPanel.add(gridPanel);
        containerPanel.add(table);

        containerPanel.setFocusable(true);

        return containerPanel;

    }


    /**
     * @param pos   Position to hit
     *              changes the color of the position to red
     * @param color Color to set the position
     */
    public void hit(int[] pos, Color color) {
        gameBoardPanel.hit(pos, color);
    }

    /**
     * @param pos   Position to miss
     *              changes the color of the position to blue
     * @param color Color to set the position
     */
    public void miss(int[] pos, Color color) {
        gameBoardPanel.miss(pos, color);
    }

    /**
     * @param color Color to set the border
     */
    public void setColor(Color color){
        int borderWidth = 5;
        Border greenBorder = BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, color);
        gameBoardPanel.setBorder(greenBorder);
    }

    /**
     * Eliminates the player from the game
     */
    public void eliminatePlayer(){
        this.setColor(Color.RED);
    }

    /**
     * @param shipIndex Index of the ship to update
     *                  Updates the table with the new state of the ship
     */
    public void updateTable(Integer shipIndex) {
        table.setState(shipIndex, "SUNK");
    }

    /**
     * Resets the gameboard
     */
    public void resetAll() {
        gameBoardPanel.resetAll();
        initGameboard();
        this.setColor(Color.GREEN);
    }

    /**
     * Initializes the gameboard
     */
    public void initGameboard(){
        for(int i=0; i<5; i++){
            table.setState(i,"FLOATING");
        }
    }
}
