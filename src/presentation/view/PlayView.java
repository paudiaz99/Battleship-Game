package presentation.view;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayView extends JPanel {
    private static final Color NAVY_BLUE = new Color(0, 0, 128);
    private static final Color ELECTRIC_PURPLE = new Color(191, 0, 255);
    private static final Color FOREST_GREEN = new Color(34, 139, 34);
    private static final Color GOLDEN_YELLOW = new Color(255, 215, 0);
    private static final Color BLUE_TEAL = new Color(0, 215, 215);

    private static final Color NAVY_BLUE_MISS = new Color(0, 0, 128, 60);
    private static final Color ELECTRIC_PURPLE_MISS = new Color(191, 0, 255, 60);
    private static final Color FOREST_GREEN_MISS = new Color(34, 139, 34, 60);
    private static final Color GOLDEN_YELLOW_MISS = new Color(255, 215, 0, 60);
    private static final Color BLUE_TEAL_MISS = new Color(0, 215, 215, 60);




    /**
     * The bar panel of the game.
     */
    private final JBarraPanel jBarraPanel;
    /**
     * The time panel of the game.
     */
    private final TimePanel timePanel;
    /**
     * The state table of the player.
     */
    private JStateTable table;
    /**
     * The panel that contains the game board and the state table.
     */
    private JPanel boardTablePanelGrande;
    /**
     * The game boards of the AI.
     */
    private final ArrayList<BoardTablePanel> gameBoardsAI;
    /**
     * The game board of the player.
     */
    private final GameBoardPanel gameBoardPlayer;

    public PlayView() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.jBarraPanel =  new JBarraPanel();
        this.add(this.jBarraPanel, BorderLayout.NORTH);
        this.timePanel = new TimePanel();
        this.gameBoardsAI = new ArrayList<>();
        this.table = new JStateTable();
        gameBoardPlayer = new GameBoardPanel();
        gameBoardPlayer.removeButtons();

        mySetState(gameBoardPlayer, NAVY_BLUE);

        table = new JStateTable();
        table.setOpaque(false);

        initGameboard();

        boardTablePanelGrande = new JPanel();
        boardTablePanelGrande = createBoardTablePanel(gameBoardPlayer, table);

        for (int i = 0; i < 4; i++) {
            gameBoardsAI.add(new BoardTablePanel());
            gameBoardsAI.get(i).setPreferredSize(new Dimension(300,800));
        }

        initColors();

        // Small panel configuration
        for (BoardTablePanel boardTablePanel : gameBoardsAI) {
            boardTablePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            boardTablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        configureGrid();
        redLight();
    }

    /**
     * @param gridPanel The panel to be configured
     * @param table The table to be configured
     * @return The panel with the grid and the table
     */
    private JPanel createBoardTablePanel(JPanel gridPanel, JStateTable table) {

        boardTablePanelGrande.setLayout(new BoxLayout(boardTablePanelGrande, BoxLayout.Y_AXIS));
        boardTablePanelGrande.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Add grid panel and table to the panel
        gridPanel.setPreferredSize(new Dimension(200,1300));
        boardTablePanelGrande.add(gridPanel, BorderLayout.NORTH);
        boardTablePanelGrande.add(table, BorderLayout.CENTER);
        boardTablePanelGrande.setFocusable(true);

        return boardTablePanelGrande;
    }
    public void addListener(ActionListener playController) {
        jBarraPanel.registerListener(playController);
    }
    /**
     * Configures the grid of the view
     */
    private void configureGrid(){
        JPanel centralPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        centralPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        leftPanel.setOpaque(false);
        // Central panel configuration
        centralPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        rightPanel.setPreferredSize(new Dimension(400,500));
        leftPanel.setPreferredSize(new Dimension(400,500));

        JLabel text1 = new JLabel("AI NUMBER 1");
        JLabel text2 = new JLabel("AI NUMBER 2");
        JLabel text3 = new JLabel("AI NUMBER 3");
        JLabel text4 = new JLabel("AI NUMBER 4");
        text1.setForeground(Color.WHITE);
        text2.setForeground(Color.WHITE);
        text3.setForeground(Color.WHITE);
        text4.setForeground(Color.WHITE);

        text1.setAlignmentX(Component.CENTER_ALIGNMENT);
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        text3.setAlignmentX(Component.CENTER_ALIGNMENT);
        text4.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font font = new Font("Arial", Font.BOLD, 20);
        text1.setFont(font);
        text1.setPreferredSize(new Dimension(30,30));
        text2.setFont(font);
        text2.setPreferredSize(new Dimension(30,30));
        text3.setFont(font);
        text3.setPreferredSize(new Dimension(30,30));
        text4.setFont(font);
        text4.setPreferredSize(new Dimension(30,30));

        centralPanel.add(boardTablePanelGrande, BorderLayout.CENTER);
        centralPanel.add(timePanel, BorderLayout.NORTH);
        rightPanel.add(text1);
        rightPanel.add(gameBoardsAI.get(0));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(text2);
        rightPanel.add(gameBoardsAI.get(1));
        leftPanel.add(text3);
        leftPanel.add(gameBoardsAI.get(2));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(text4);
        leftPanel.add(gameBoardsAI.get(3));

        centralPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        this.add(jBarraPanel, BorderLayout.NORTH);
        this.add(centralPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);

    }

    /**
     * @param formattedTime the time to update
     *                      Updates the time of the view
     */
    public void updateTime(String formattedTime) {
        timePanel.updateTime(formattedTime);
    }

    /**
     * Resets the timer
     */
    public void resetTimer() {
        timePanel.resetTimer();
        redLight();
    }

    /**
     * Changes the color of the cell to red
     */
    public void redLight() {
        timePanel.redLight();
    }

    /**
     * Changes the color of the traffic light to green
     */
    public void greenLight() {
        timePanel.greenLight();
    }

    /**
     * @return the game board of the player
     */
    public GameBoardPanel getGameBoardPanel() {
        return gameBoardPlayer;
    }

    /**
     * @param nextShot the position of the shot
     * @param gameBoardNumber the number of the board to change the color
     *                        -1 if it's the player
     *                        0-3 if it's an AI
     *                        Changes the color of the cell to red
     */
    public void aiHit(int[] nextShot, int gameBoardNumber, int shooterNumber) {
        if(gameBoardNumber!=-1) {
            gameBoardsAI.get(gameBoardNumber).hit(nextShot, this.getColorByShooterNumber(shooterNumber, true));
        }else {
            gameBoardPlayer.hit(nextShot, this.getColorByShooterNumber(shooterNumber, true));
        }
    }

    private Color getColorByShooterNumber(int shooterNumber, boolean isHit) {
        switch (shooterNumber){
            case 0-> {
                if(isHit) return ELECTRIC_PURPLE;
                else return ELECTRIC_PURPLE_MISS;
            }
            case 1-> {
                if(isHit) return FOREST_GREEN;
                else return FOREST_GREEN_MISS;
            }
            case 2-> {
                if(isHit) return GOLDEN_YELLOW;
                else return GOLDEN_YELLOW_MISS;
            }
            case 3-> {
                if(isHit) return BLUE_TEAL;
                else return BLUE_TEAL_MISS;
            }
            case -1-> {
                if(isHit) return NAVY_BLUE;
                else return NAVY_BLUE_MISS;
            }
        }
        return null;
    }

    /**
     * @param nextShot the position of the shot
     * @param gameBoardNumber the number of the board to change the color
     *                        -1 if it's the player
     *                        0-3 if it's an AI
     *                        Changes the color of the cell to blue
     */
    public void aiMiss(int[] nextShot,int gameBoardNumber, int shooterNumber) {
        if(gameBoardNumber!=-1) {
            gameBoardsAI.get(gameBoardNumber).miss(nextShot, this.getColorByShooterNumber(shooterNumber, false));
        }else {
            gameBoardPlayer.miss(nextShot, this.getColorByShooterNumber(shooterNumber, false));
        }
    }

    /**
     * @param gameBoardPlayer the board to change the color
     * @param color the color to change the board to
     */
    public void mySetState (GameBoardPanel gameBoardPlayer, Color color){
        int borderWidth = 5;
        Border greenBorder = BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, color);
        gameBoardPlayer.setBorder(greenBorder);
    }

    /**
     * @param j index of the player to eliminate
     *          -1 if it's the player
     *          0-3 if it's an AI
     *          Changes the color of the board to red
     */
    public void eliminatePlayer(int j) {
        if(j == -1) {
            mySetState(gameBoardPlayer, Color.RED);
        }
        else {
            gameBoardsAI.get(j).eliminatePlayer();
        }
    }

    /**
     * @param shipIndex index of the ship to update
     * @param boardNumber index of the board to update
     */
    public void updateTable(Integer shipIndex, int boardNumber) {
        if(boardNumber == -1) {
            table.setState(shipIndex, "SUNK");
        }
        else {
            gameBoardsAI.get(boardNumber).updateTable(shipIndex);
        }
    }

    /**
     * initializes the game board
     */
    public void initGameboard(){
        for(int i=0; i<5; i++){
            table.setState(i,"FLOATING");
        }
    }

    /**
     * Initializes the colors of the boards
     */
    public void initColors(){
        gameBoardsAI.get(0).setColor(ELECTRIC_PURPLE);
        gameBoardsAI.get(1).setColor(FOREST_GREEN);
        gameBoardsAI.get(2).setColor(GOLDEN_YELLOW);
        gameBoardsAI.get(3).setColor(BLUE_TEAL);

    }


    /**
     * Resets all the boards
     */
    public void resetAll() {
        for (BoardTablePanel boardTablePanel : gameBoardsAI) {
            boardTablePanel.resetAll();
        }
        initColors();
        gameBoardPlayer.resetAll();
        initGameboard();
        mySetState(gameBoardPlayer, NAVY_BLUE);
    }

    /**
     * @return true if the user wants to save the game
     */
    public boolean checkSaveGame(){
        return jBarraPanel.optionSave() == JOptionPane.YES_OPTION;
    }

    /**
     * Shows an error message when the user tries to load the stats while being in a game
     */
    public void statsErrorMessage() {
        JOptionPane.showMessageDialog(this, "You cannot load the stats while being in a game.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @return the name of the game to save
     */
    public String askForName() {
        return JOptionPane.showInputDialog(this, "Please enter the name of the game", "Name", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * @return true if the user wants to log out
     */
    public boolean logOutConfirmation() {
        return jBarraPanel.logOut() == JOptionPane.YES_OPTION;
    }

    /**
     * @param hits the hits to load
     * @param misses the misses to load
     */
    public void loadGameBoards(ArrayList<ArrayList<int[]>> hits, ArrayList<ArrayList<int[]>> misses) {
        loadHits(hits);
        loadMisses(misses);
    }

    /**
     * @param misses the misses to load
     *               Loads the misses on the boards
     */
    private void loadMisses(ArrayList<ArrayList<int[]>> misses) {
        for (int i = 0; i < misses.size(); i++) {
            for (int[] miss : misses.get(i)) {
                if(i == 0){
                    aiMiss(miss, -1, miss[2]);
                }else {
                    aiMiss(miss, i-1, miss[2]);
                }
            }
        }

    }

    /**
     * @param hits the hits to load
     *             Loads the hits on the boards
     */
    private void loadHits(ArrayList<ArrayList<int[]>> hits) {
        for (int i = 0; i < hits.size(); i++) {
            for (int[] hit : hits.get(i)) {
                if(i == 0) {
                    aiHit(hit, -1, hit[2]);
                }else {
                    aiHit(hit, i-1, hit[2]);
                }
            }
        }
    }

}