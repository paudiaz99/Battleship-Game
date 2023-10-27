package presentation;

import business.UserManager;
import business.model.GameModel;
import presentation.view.JRegisterPanel;
import presentation.view.JloginPanel;
import presentation.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used to control the login and register panel.
 */
public class LoginController implements ActionListener {
    /**
     * JloginPanel object that will be controlled by this class.
     */
    private final JloginPanel jloginPanel;
    /**
     * RegisterPanel object that will be controlled by this class.
     */
    private final JRegisterPanel jRegisterPanel;
    /**
     * UserManager object to call the checkUser method.
     */
    private final UserManager userManager;
    /**
     * The main view of the application
     */
    private final MainView mainView;
    private final GameModel gameModel;

    /**
     * @param mainView The main view of the application
     *                 The constructor of the class. It initializes the attributes.
     */
    public LoginController(MainView mainView, GameModel gameModel) {
        this.gameModel = gameModel;
        this.mainView = mainView;
        this.jloginPanel = mainView.getLoginPanel();
        this.jloginPanel.registerLoginController(this);
        this.jRegisterPanel = mainView.getRegisterPanel();
        this.jRegisterPanel.setActionListener(this);
        this.userManager = new UserManager();
    }

    /**
     * @param e the event to be processed by the controller.
     *          Process the event and call the corresponding method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case JloginPanel.LOGIN_BUTTON -> {
                String username = jloginPanel.getUsername();
                String password = jloginPanel.getPassword();
                if (userManager.checkUser(username, password)) {
                    gameModel.setPlayerName(userManager.getPlayerName(username));
                    jloginPanel.resetTextFields();
                    mainView.loginSuccessful(userManager.getPlayerName(username));
                } else {
                    jloginPanel.loginFailed();
                }
            }
            case JRegisterPanel.REGISTER_BUTTON -> {
                String userToRegister = jRegisterPanel.getUsername();
                String passwordToRegister = jRegisterPanel.getPassword();
                String passwordConfirmation = jRegisterPanel.getPasswordConfirmation();
                String email = jRegisterPanel.getEmail();
                if (userManager.checkPassword(passwordToRegister) == 1) {
                    jRegisterPanel.passwordCreationError(JRegisterPanel.PASSWORD_CHAR_ERROR);

                } else {
                    if (!userManager.checkEmail(email)) {
                        jRegisterPanel.passwordCreationError(JRegisterPanel.EMAIL_ERROR);

                    } else {
                        if (userManager.checkUser(userToRegister)){
                            jRegisterPanel.passwordCreationError(JRegisterPanel.LENGTH_ERROR);
                        } else {
                            if (!passwordConfirmation.equals(passwordToRegister)) {
                                jRegisterPanel.passwordCreationError(JRegisterPanel.PASSWORD_MATCH_ERROR);
                            } else {
                                if (userManager.checkRegister(userToRegister, email)) {
                                    jRegisterPanel.passwordCreationError(JRegisterPanel.USER_ERROR);
                                } else {
                                    userManager.createUser(userToRegister, email, passwordConfirmation);
                                    jRegisterPanel.resetTextFields();
                                    mainView.loginSuccessful(userToRegister);
                                    gameModel.setPlayerName(userToRegister);
                                }
                            }
                        }
                    }
                }
            }
            case JloginPanel.CHANGE_TO_REGISTER_BUTTON -> {
                jloginPanel.resetTextFields();
                mainView.changeToRegister();
            }
            case JRegisterPanel.CHANGE_TO_LOGIN_BUTTON -> {
                jRegisterPanel.resetTextFields();
                mainView.logout();
            }
        }
    }
}

