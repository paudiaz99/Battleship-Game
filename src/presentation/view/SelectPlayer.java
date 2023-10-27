package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates a new SelectPlayer panel. This panel is used to select the player to show stats or the game to load.
 */
public class SelectPlayer extends JPanel {
    public static final String GO_BUTTON = "GO_BUTTON";
    private final JBarraPanel jBarraPanel;
    private JComboBox<String> jcb;

    private JButton goButton;

    /**
     * @param text the text to display in the panel
     *             Creates a new SelectPlayer panel.
     */
    public SelectPlayer(String text){
        this.setOpaque(false);
        this.jBarraPanel = new JBarraPanel();
        this.jBarraPanel.setOpaque(false);
        setLayout(new BorderLayout());
        this.add(jBarraPanel, BorderLayout.NORTH);
        configureView(text);
    }

    /**
     * @param text the text to display in the panel
     *             Configures the view of the panel
     */
    private void configureView(String text) {
        JPanel container = new JPanel();
        container.setOpaque(false);
        JLabel text1 = new JLabel(text);
        text1.setForeground(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 30);
        text1.setFont(font);
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        jcb = new JComboBox<>();
        jcb.setFont(new Font("Arial", Font.BOLD, 20));
        jcb.setMaximumSize(new Dimension(400, jcb.getPreferredSize().height));
        jcb.setFocusable(false);
        container.add(text1);
        container.add((Box.createHorizontalStrut(10)));
        container.add(jcb);
        container.setBorder(BorderFactory.createEmptyBorder(0, 270, 100, 0));
        this.goButton = new JButton("GO");
        this.goButton.setFocusable(false);
        this.goButton.setActionCommand(GO_BUTTON);

        this.setAlignmentX(Component.RIGHT_ALIGNMENT);
        goButton.setFont(font);
        goButton.setMargin(new Insets(2,40,2,40));
        container.add((Box.createHorizontalStrut(10)));

        container.add(goButton);
        this.add(container, BorderLayout.CENTER);
    }

    /**
     * @param games the list of games to load in the JComboBox
     *              Loads the list of games in the JComboBox
     */
    public void loadJCB(ArrayList<String> games) {
        jcb.removeAllItems();
        for (String game : games) {
            jcb.addItem(game);
        }
        jcb.setSelectedIndex(0);
    }

    /**
     * @param actionListener the action listener to register.
     *                       Registers the action listener to the go button and the JBarraPanel.
     */
    public void registerListeners(ActionListener actionListener) {
        goButton.addActionListener(actionListener);
        jBarraPanel.registerListener(actionListener);
    }

    /**
     * @return the selected item in the JComboBox
     */
    public String getSelectedItem() {
        return (String) jcb.getSelectedItem();
    }

}
