package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class is the view that is shown when the game is over.
 */
public class EndGameView extends JPanel {
    public static final String END_GAME = "END_GAME";
    private final JLabel name;
    private final JBarraPanel barraPanel;

    /**
     * Creates a new EndGameView. It is the view that is shown when the game is over.
     */
    public EndGameView(){
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.barraPanel = new JBarraPanel();
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        name = new JLabel();
        name.setFont(new Font("Arial", Font.BOLD, 40));
        name.setForeground(Color.WHITE);
        name.setBorder(BorderFactory.createEmptyBorder(300, 0, 0, 0));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the components to the panel
        panel.add(name);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(panel, BorderLayout.CENTER);
        this.add(barraPanel, BorderLayout.NORTH);
    }

    /**
     * @param name message to show
     *             Changes the name of the label
     */
    public void changeName(String name){
        this.name.setText(name);
    }

    /**
     * @param actionListener the action listener to register
     *                       Registers the action listener to the buttons
     */
    public void registerListener(ActionListener actionListener){
       this.barraPanel.registerListener(actionListener);
    }

    /**
     * @return true if the user wants to log out
     */
    public boolean logOutConfirmation() {
        return barraPanel.logOut() == JOptionPane.YES_OPTION;
    }
}
