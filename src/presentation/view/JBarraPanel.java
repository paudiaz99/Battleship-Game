package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A JPanel with the options to show the stats, log out and exit.
 */
public class JBarraPanel extends JPanel {
    private static final String TITLE = "BATTLESHIP";
    private JButton showStatsButton;
    public static final String STATS_BUTTON="STATS";
    private JButton logOutButton;
    public static final String LOG_OUT_BUTTON="LOG OUT";
    private JButton exitButton;
    public static final String EXIT="EXIT";

    /**
     * Constructor. Create the panel and configure it.
     */
    public JBarraPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        configurationPanel();
    }

    /**
     * Configure the panel.
     */
    private void configurationPanel(){

        JPanel configPanel = new JPanel();
        configPanel.setOpaque(false);
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

        JLabel title=new JLabel(JBarraPanel.TITLE);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(false);
        configPanel.add(title);

        JPanel botonsPanell =new JPanel();

        botonsPanell.setOpaque(false);
        showStatsButton=new JButton("SHOW STATS");
        showStatsButton.setPreferredSize(new Dimension(120,15));
        showStatsButton.setActionCommand(STATS_BUTTON);
        botonsPanell.add(showStatsButton);
        showStatsButton.setFocusable(false);
        logOutButton=new JButton("LOG OUT");
        logOutButton.setPreferredSize(new Dimension(120,15));
        botonsPanell.add(logOutButton);
        logOutButton.setFocusable(false);
        exitButton=new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(120,15));
        exitButton.setFocusable(false);
        botonsPanell.add(exitButton);
        configPanel.add(botonsPanell);
        this.add(configPanel);

    }

    /**
     * @return The option selected by the user.
     *       Show a confirmation dialog to the user to confirm if he wants to save the game.
     */
    public int optionSave(){
        return JOptionPane.showOptionDialog(null, "Do you really want to save game?", "Save game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    /**
     * @return The option selected by the user.
     *        Show a confirmation dialog to the user to confirm if he wants to log out.
     */
    public int logOut(){
        return  JOptionPane.showOptionDialog(null, "Do you really want to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }


    /**
     * @param listener The listener to be registered.
     *                 Register the listener to the buttons.
     */
    public void registerListener(ActionListener listener) {
        showStatsButton.addActionListener(listener);
        logOutButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}
