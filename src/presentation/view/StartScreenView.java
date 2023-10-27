package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Starting screen of the game. It shows the title, the buttons to start a new game, load a game, delete an account and exit the game.
 */
public class StartScreenView extends JPanel {
    /**
     * The start button.
     */
    private JButton startButton;
    /**
     * The load game button.
     */
    private JButton loadGameButton;
    /**
     * The exit button.
     */
    private JButton exitButton;
    /**
     * The delete account button.
     */
    private JButton deleteAccountButton;
    private JButton statsButton;

    public static final String START_BUTTON = "START_BUTTON";
    public static final String EXIT_BUTTON = "EXIT_BUTTON";
    public static final String DELETE_ACCOUNT_BUTTON = "DELETE_ACCOUNT_BUTTON";
    public static final String LOAD_GAME_BUTTON = "LOAD_GAME_BUTTON";
    public static final String STATS_BUTTON = "STATS_BUTTON";
    /**
     * The username label. It shows the current username.
     */
    private JLabel usernameLabel;
    /**
     * The current username.
     */
    private String currentUserName;

    /**
     * Constructor of the class. It creates the panel and configures it.
     */
    public StartScreenView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        configureCenter();
    }

    /**
     * Configures the center of the panel. It adds the title, the buttons and the username label.
     */
    private void configureCenter() {
        this.setOpaque(false);
        JLabel title = new JLabel("BattleShips Game");
        title.setForeground(Color.WHITE);
        startButton = new JButton("Start New Game");
        exitButton = new JButton("Log Out");
        deleteAccountButton = new JButton("Delete Account");
        loadGameButton = new JButton("Load Game");
        statsButton = new JButton("Show Stats");
        Font font = new Font("Arial", Font.BOLD, 20);
        startButton.setFont(font);
        startButton.setMargin(new Insets(10,20,10,20));
        startButton.setFocusable(false);
        exitButton.setFont(font);
        exitButton.setMargin(new Insets(10,59,10,58));
        exitButton.setFocusable(false);
        deleteAccountButton.setFont(font);
        deleteAccountButton.setMargin(new Insets(10,25,10,25));
        deleteAccountButton.setFocusable(false);
        loadGameButton.setFont(font);
        loadGameButton.setMargin(new Insets(10,43,10,42));
        loadGameButton.setFocusable(false);
        statsButton.setFont(font);
        statsButton.setMargin(new Insets(10,43,10,42));
        statsButton.setFocusable(false);
        statsButton.setActionCommand(STATS_BUTTON);

        startButton.setActionCommand(START_BUTTON);
        exitButton.setActionCommand(EXIT_BUTTON);
        deleteAccountButton.setActionCommand(DELETE_ACCOUNT_BUTTON);
        loadGameButton.setActionCommand(LOAD_GAME_BUTTON);

        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel = new JLabel();
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 50, 0));

        this.add(title);
        this.add(startButton);
        this.add((Box.createVerticalStrut(10)));
        this.add(loadGameButton);
        this.add((Box.createVerticalStrut(10)));
        this.add(statsButton);
        this.add((Box.createVerticalStrut(10)));
        this.add(exitButton);
        this.add(usernameLabel);
        this.add(deleteAccountButton);
    }

    /**
     * @param listener ActionListener to register to the buttons
     *                 Register the listener to the buttons
     */
    public void registerListeners(ActionListener listener) {
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
        deleteAccountButton.addActionListener(listener);
        loadGameButton.addActionListener(listener);
        statsButton.addActionListener(listener);
    }

    /**
     * @param username String representing the current user's name
     *                 Set the current user's name and show it on the screen
     */
    public void setUsername(String username) {
        this.currentUserName = username;
        this.usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.usernameLabel.setForeground(Color.WHITE);
        this.usernameLabel.setText("Welcome " + currentUserName);
    }

    /**
     * @return String representing the current user's name
     */
    public String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * @return int representing the user's choice
     */
    public int showConfirmation() {
        return JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
    }

    /**
     * Shows an error message when there are no games to load.
     */
    public void showNoGamesToLoadMessage() {
        JOptionPane.showMessageDialog(this, "There are no games to load", "Load Game", JOptionPane.INFORMATION_MESSAGE);
    }
}
