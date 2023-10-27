package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The register panel class. Extends the login panel class. It ables the user to enter his email and password confirmation for the registration.
 */
public class JRegisterPanel extends JloginPanel{
    public static final String CHANGE_TO_LOGIN_BUTTON = "CHANGE_TO_LOGIN_BUTTON";
    public static final String REGISTER_BUTTON = "REGISTER_BUTTON";
    public static final String PASSWORD_CHAR_ERROR = "Password must contain: lowercase, uppercase, numbers and be 8 characters long";
    public static final String PASSWORD_MATCH_ERROR = "Password confirmation does not match";
    public static final String USER_ERROR = "this email or username has already been used, try another one";
    public static final String EMAIL_ERROR = "the email format is not correct";
    public static final String LENGTH_ERROR = "you need to have a username";
    /**
     * The password confirmation text field.
     */
    private final JTextField passwordConfirmation;
    /**
     * The email text field.
     */
    private final JTextField email;

    /**
     * Constructor for the register panel. Generates a register panel from the login panel.
     */
    public JRegisterPanel() {
        super();

        this.resetView();
        email = new JTextField();
        email.setMaximumSize(new Dimension(300, 30));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(font);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(emailLabel);
        this.add(email);
        this.add(Box.createVerticalStrut(10));

        this.addPassword();
        passwordConfirmation = new JPasswordField();
        passwordConfirmation.setMaximumSize(new Dimension(300, 30));
        passwordConfirmation.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordConfirmationLabel = new JLabel("Confirm Password");
        passwordConfirmationLabel.setForeground(Color.WHITE);
        passwordConfirmationLabel.setFont(font);
        passwordConfirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalStrut(10));
        this.add(passwordConfirmationLabel);
        this.add(passwordConfirmation);
        this.changeLoginActionCommand(CHANGE_TO_LOGIN_BUTTON);
        this.changeRegisterActionCommand(REGISTER_BUTTON);
        this.addButtons();

    }

    /**
     * @return the email text.
     */
    public String getEmail(){
        return email.getText();
    }

    /**
     * @return the password confirmation text.
     */
    public String getPasswordConfirmation(){
        return passwordConfirmation.getText();
    }

    /**
     * @param actionListener the action listener to be set
     *                       sets the action listener for the buttons.
     */
    public void setActionListener(ActionListener actionListener){
        super.registerLoginController(actionListener);
    }

    /**
     * resets the text fields.
     */
    public void resetTextFields(){
        super.resetTextFields();
        email.setText("");
        passwordConfirmation.setText("");
    }

    /**
     * @param error the error to be shown
     *              shows an error message in a pop-up.
     */
    public void passwordCreationError(String error) {
        JOptionPane.showMessageDialog(this,error);
    }
}
