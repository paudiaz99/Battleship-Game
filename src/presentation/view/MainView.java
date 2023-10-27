package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This class is the main view of the application. It contains all the other views.
 * It is used to switch between the views when the user interacts with the application.
 */
public class MainView extends JFrame {
    /**
     * The login view of the application.
     */
    private JloginPanel loginPanel;
    /**
     * The register view of the application.
     */
    private JRegisterPanel registerPanel;
    /**
     * The main panel of the application. It contains all the other views.
     */
    private final JPanel mainPanel;
    /**
     * The game view of the application.
     */
    private StartScreenView gamePanel;
    /**
     * The card layout of the main panel. It is used to switch between the views.
     */
    private final CardLayout cardLayout;
    /**
     * The preparation view of the application.
     */
    private JPreparationPanel preparationPanel;
    /**
     * The play view of the application.
     */
    private PlayView playView;
    /**
     * The stats view of the application.
     */
    private GraphicStats statsPanel;
    private EndGameView endView;
    private SelectPlayer selectStats;
    private SelectPlayer selectGameToLoad;


    /**
     * Creates a new MainView. It is the main view of the application, it contains all the other views.
     */
    public MainView() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        JBackgroundPanel backgroundPanel = new JBackgroundPanel(Path.of("resources", "Background.jpg").toString());
        backgroundPanel.setLayout(new BorderLayout());
        configureCardLayout();
        backgroundPanel.add(mainPanel,BorderLayout.CENTER);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 838);
        this.setLocationRelativeTo(null);
        this.add(backgroundPanel, BorderLayout.CENTER);

    }

    /**
     * Configures the card layout of the main panel.
     */
    private void configureCardLayout() {
        this.loginPanel = new JloginPanel();
        this.gamePanel = new StartScreenView();
        this.registerPanel = new JRegisterPanel();
        this.preparationPanel = new JPreparationPanel();
        this.playView = new PlayView();
        this.statsPanel = new GraphicStats();
        this.endView = new EndGameView();
        this.selectStats = new SelectPlayer("Select Player to see stats");
        this.selectGameToLoad = new SelectPlayer("Select Game to load");

        mainPanel.setLayout(cardLayout);
        mainPanel.add(loginPanel, "login");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(registerPanel, "register");
        mainPanel.add(preparationPanel, "preparation");
        mainPanel.add(playView, "playing");
        mainPanel.add(statsPanel, "stats");
        mainPanel.add(endView, "end");
        mainPanel.add(selectStats, "statSelection");
        mainPanel.add(selectGameToLoad, "gameSelection");
    }

    /**
     * Shows the login view. This method is called when the application starts.
     */
    public void start(){
        this.setVisible(true);
        cardLayout.show(mainPanel, "login");
    }


    /**
     * @return the JloginPanel of the MainView.
     */
    public JloginPanel getLoginPanel() {
        return loginPanel;
    }

    /**
     * @param username the username of the player. It is used to set the username in the game view and to show it in the game.
     */
    public void loginSuccessful(String username){
        gamePanel.setUsername(username);
        cardLayout.show(mainPanel, "game");
    }

    /**
     * @return the JGameBoardPanel of the MainView.
     */
    public StartScreenView getGamePanel() {
        return gamePanel;
    }

    /**
     * returns to the login view.
     */
    public void logout() {
        cardLayout.show(mainPanel, "login");
    }

    /**
     * @return the JRegisterPanel of the MainView.
     */
    public JRegisterPanel getRegisterPanel() {return registerPanel;}

    /**
     * Method to change the view to the register view.
     */
    public void changeToRegister() {
        cardLayout.show(mainPanel, "register");
    }

    /**
     * Method to start the preparation. It changes the view to the preparation view.
     */
    public void startPreparation() {
        cardLayout.show(mainPanel, "preparation");
        preparationPanel.focus();
    }

    /**
     * Method to start the game. It changes the view to the game view.
     */
    public void startGame(){
        cardLayout.show(mainPanel, "playing");
    }

    /**
     * @return the JPreparationPanel of the MainView.
     */
    public JPreparationPanel getPreparationPanel(){
        return this.preparationPanel;
    }

    /**
     * @param panel the panel to get
     * @return the GameBoardPanel of the given panel.
     */
    public GameBoardPanel getGameBoardPanel(String panel) {
        return switch (panel) {
            case GameBoardPanel.PREPARATION_MODE -> preparationPanel.getGameBoardPanel();
            case GameBoardPanel.PLAY_MODE -> playView.getGameBoardPanel();
            default -> null;
        };
    }

    /**
     * @return the PlayView of the MainView
     */
    public PlayView getPlayView() {
        return playView;
    }

    /**
     * Method to end the game and show the final screen
     */
    public void endGame(String name) {
        endView.changeName(name);
        cardLayout.show(mainPanel, "end");
    }

    /**
     * Method to return to the menu
     */
    public void returnToMenu(){
        cardLayout.show(mainPanel, "game");
    }

    /**
     * Method to show the stats panel
     */
    public void showStats(ArrayList<String> players){
        selectStats.loadJCB(players);
        cardLayout.show(mainPanel, "statSelection");
    }

    /**
     * @return the GraphicStats of the MainView
     */
    public GraphicStats getStatsPanel(){
        return statsPanel;
    }

    /**
     * @return the EndGameView of the MainView
     *      Method to get the end game view
     */
    public EndGameView getEndGameView() {
        return endView;
    }

    /**
     * @param games the games to load
     *              Method to show the games to load
     */
    public void showGamesToLoad(ArrayList<String> games) {
        selectGameToLoad.loadJCB(games);
        cardLayout.show(mainPanel, "gameSelection");
    }

    /**
     * @return the SelectPlayer of the MainView
     */
    public SelectPlayer getSelectGameToLoad() {
        return selectGameToLoad;
    }

    /**
     * @return the SelectPlayer of the MainView
     */
    public SelectPlayer getSelectStatsPlayer() {return selectStats;}

    /**
     * Method to show the stats view
     */
    public void showStatsView() {
        cardLayout.show(mainPanel, "stats");
    }
}
