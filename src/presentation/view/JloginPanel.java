package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The login panel. It is used to log in the user.
 */
public class JloginPanel extends JPanel {
    public static final String LOGIN_BUTTON = "LOGIN_BUTTON";
    public static final String CHANGE_TO_REGISTER_BUTTON = "CHANGE_TO_REGISTER_BUTTON";
    /**
     * The username text field. It is used to enter the username.
     */
    private final JTextField username;
    /**
     * The password text field. It is used to enter the password.
     */
    private final JTextField password;
    /**
     * The login button. It is used to log in the user.
     */
    private final JButton loginButton;
    /**
     * The register button. It is used to change to the register panel.
     */
    private final JButton registerButton;
    /**
     * The password label.
     */
    private JLabel passwordLabel;
    private JLabel battleship;

    /**
     * Creates a new login panel. It contains two text boxes and two buttons.
     */
    public JloginPanel() {
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Add top border to the login panel
        this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        username = new JTextField();
        password = new JPasswordField();
        Font font = new Font("Arial", Font.BOLD, 80);
        battleship = new JLabel("BATTLESHIP");
        battleship.setFont(font);
        battleship.setForeground(Color.WHITE);
        battleship.setAlignmentX(Component.CENTER_ALIGNMENT);
        battleship.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        username.setMaximumSize(new Dimension(300, 30));
        password.setMaximumSize(new Dimension(300, 30));
        this.add(battleship);
        loginButton = new JButton("Login");
        loginButton.setActionCommand(LOGIN_BUTTON);

        registerButton = new JButton("Register");
        registerButton.setActionCommand(CHANGE_TO_REGISTER_BUTTON);

        loginButton.setMargin(new Insets(7, 28, 7, 28));
        registerButton.setMargin(new Insets(7, 20, 7, 20));

        this.addUser();
        this.addPassword();
        this.addButtons();
    }

    /**
     * Adds the username label and username text field to the view.
     */
    private void addUser(){
        JLabel usernameLabel = new JLabel("Username");
        Font font = new Font("Arial", Font.BOLD, 20);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(font);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(usernameLabel);
        this.add(username);

    }

    /**
     * Adds the password label and password text field to the view.
     */
    protected void addPassword(){
        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 20);
        passwordLabel.setFont(font);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add((Box.createVerticalStrut(10)));
        this.add(passwordLabel);
        this.add(password);
        this.add((Box.createVerticalStrut(10)));

    }

    /**
     * Adds the login button and register button to the view.
     */
    protected void addButtons() {
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add((Box.createVerticalStrut(10)));
        this.add(loginButton);
        this.add((Box.createVerticalStrut(10)));
        this.add(registerButton);
    }



    /**
     * Removes the password label, password text field, login button and register button from the view.
     */
    protected void resetView(){
        this.remove(passwordLabel);
        this.remove(password);
        this.remove(registerButton);
        this.remove(loginButton);
    }

    /**
     * @param listener the listener to register to the login button.
     *                 Registers the listener to the login and register buttons.
     */
    public void registerLoginController(ActionListener listener) {
        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
    }

    /**
     * @return the username that the user has entered.
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * @return the password that the user has entered.
     */
    public String getPassword() {
        return password.getText();
    }

    /**
     * Resets the text fields to be empty.
     */
    public void resetTextFields() {
        username.setText("");
        password.setText("");
    }

    /**
     * @param changeToLoginButton the loginButton to set as the new login button.
     */
    protected void changeLoginActionCommand(String changeToLoginButton) {
        this.loginButton.setActionCommand(changeToLoginButton);

    }

    /**
     * @param registerButton the registerButton to set as the new register button.
     */
    protected void changeRegisterActionCommand(String registerButton) {
        this.registerButton.setActionCommand(registerButton);
    }

    /**
     * Shows a message dialog with the given message.
     */
    public void loginFailed() {
        JOptionPane.showMessageDialog(this, "Login failed. Please try again.");
    }
}
