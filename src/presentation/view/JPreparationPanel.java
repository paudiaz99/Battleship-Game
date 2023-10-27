package presentation.view;
import presentation.PreparationController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Class that represents the preparation panel of the application. Allows the user to place the ships on the board.
 */
public class JPreparationPanel extends JPanel {

    public static final String START_GAME = "START_GAME";

    /**
     * Table of the preparation stage. It shows the state of the ships.
     */
    private JStateTable table;

    /**
     * Game board panel of the preparation stage.
     */
    private GameBoardPanel gameBoard;
    /**
     * Bar panel of the application.
     */
    private final JBarraPanel jBarraPanel;
    /**
     * Button to start the game.
     */
    private JButton startButton;
    /**
     * Combo box to select the number of rivals.
     */
    private JComboBox<String> jcb;

    /**
     *             Constructor of the preparation panel. It creates the panel and its components.
     */
    public JPreparationPanel() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.jBarraPanel = new JBarraPanel();
        this.add(this.jBarraPanel, BorderLayout.NORTH);

        configureView();
    }

    /**
     * Configures the view of the preparation panel and its components.
     */
    private void configureView() {
        JPanel question = new JPanel();
        question.setOpaque(false);
        JLabel title = new JLabel("HOW MANY RIVALS DO YOU WANT?");
        title.setForeground(Color.WHITE);
        String[] rivals = {"1", "2", "3", "4"};
        jcb = new JComboBox<>(rivals);
        jcb.setSelectedIndex(0);

        jcb.setMaximumSize(new Dimension(75, jcb.getPreferredSize().height));
        jcb.setFocusable(false);
        question.setLayout(new BoxLayout(question, BoxLayout.X_AXIS));
        question.setAlignmentX(Component.LEFT_ALIGNMENT);
        question.add(title);
        question.add(Box.createHorizontalStrut(10)); // Espacio en blanco
        question.add(jcb);

        question.setBorder(BorderFactory.createEmptyBorder(0,10,20,0));
        gameBoard = new GameBoardPanel();
        gameBoard.setOpaque(false);
        gameBoard.setBorder(BorderFactory.createEmptyBorder(10,10,50,10));
        table = new JStateTable();
        table.setFocusable(false);
        table.setOpaque(false);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.startButton = new JButton("Start");
        this.startButton.setFocusable(false);
        this.startButton.setActionCommand(START_GAME);
        startButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        buttonPanel.add(Box.createHorizontalStrut(85));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Box.createVerticalStrut(25)); // espacio en blanco
        buttonPanel.add(table);
        buttonPanel.add(Box.createVerticalStrut(500));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameBoard, buttonPanel);
        splitPane.setOneTouchExpandable(false);
        splitPane.setEnabled(false);
        splitPane.setDividerLocation(698);
        splitPane.setOpaque(false);
        splitPane.setDividerSize(0);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.PAGE_AXIS));
        panelPrincipal.add(question);
        panelPrincipal.add(splitPane);
        this.add(panelPrincipal);

    }

    /**
     * Focus the gameBoard panel to be able to place the ships.
     */
    public void focus() {
        this.gameBoard.focus();
    }

    /**
     * @param preparationController The controller that will be added as a listener to the startButton.
     */
    public void addListener(PreparationController preparationController) {
        startButton.addActionListener(preparationController);
        jBarraPanel.registerListener(preparationController);
    }

    /**
     * @return the gameBoard panel. This panel contains the ships placed.
     */
    public GameBoardPanel getGameBoardPanel() {
        return gameBoard;
    }

    /**
     * Advertises the user that all ships have been placed before starting the game.
     */
    public void placeAllShipsError() {
        JOptionPane.showMessageDialog(this, "You must place all ships before starting the game", "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * @return The number of AI players selected by the user in the preparation stage.
     */
    public int getAIPlayers() {
        return Integer.parseInt((String) Objects.requireNonNull(jcb.getSelectedItem()));
    }

    /**
     * @param i The index of the ship that will be updated.
     *          updates the state of the ship in the table to "POSITIONED".
     */
    public void updateTablePositioned(int i) {
        table.setState(i,"POSITIONED");

    }

    /**
     * @param i The index of the ship that will be updated.
     *          updates the state of the ship in the table to "IN THE BASE".
     */
    public void updateTableNotPositioned(int i) {
        table.setState(i,"IN THE BASE");
    }

    /**
     * Resets the game board to its initial state.
     */
    public void resetGameBoard() {
        gameBoard.resetAll();
    }

    /**
     * @return true if the user wants to log out, false otherwise.
     */
    public boolean logOutConfirmation() {
        return jBarraPanel.logOut() == JOptionPane.YES_OPTION;
    }

    /**
     * shows an error message when the user tries to show stats before finishing the game.
     */
    public void showStatsError() {
        JOptionPane.showMessageDialog(this, "You must finish the game before seeing the stats", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
