package presentation;

import business.UserManager;
import business.model.GameModel;
import presentation.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used to control the start screen view.
 */
public class StartScreenViewController implements ActionListener {
    /**
     * StartScreenView object that will be controlled by this class.
     */
    private final StartScreenView startScreenView;
    /**
     * GameModel object manage game logic.
     */
    private final GameModel gameModel;
    /**
     * UserManager object to call the deleteUser method.
     */
    private final UserManager userManager;
    /**
     * MainView of the application.
     */
    private final MainView mainView;
    private final SelectPlayer selectGameToLoad;

    /**
     * @param mainView  mainView of the application.
     * @param gameModel gameModel object to call the loadGame method.
     */
    public StartScreenViewController(MainView mainView, GameModel gameModel) {
        this.mainView = mainView;
        this.startScreenView = mainView.getGamePanel();
        this.startScreenView.registerListeners(this);
        this.selectGameToLoad = mainView.getSelectGameToLoad();
        this.selectGameToLoad.registerListeners(this);
        this.gameModel = gameModel;
        this.userManager = new UserManager();
    }

    /**
     * @param e the event to be processed by the controller.
     *          Process the event and call the corresponding method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case StartScreenView.START_BUTTON -> mainView.startPreparation();
            case StartScreenView.EXIT_BUTTON, JBarraPanel.LOG_OUT_BUTTON -> {
                mainView.logout();
                gameModel.resetDataInfo();
            }
            case StartScreenView.DELETE_ACCOUNT_BUTTON -> {
                if (startScreenView.showConfirmation() == 0) {
                    userManager.deleteUser(startScreenView.getCurrentUserName());
                    mainView.logout();
                    gameModel.resetGamesInfo();
                    gameModel.resetDataInfo();
                }
            }
            case StartScreenView.LOAD_GAME_BUTTON -> {
                if(gameModel.loadGameNames().size()>0) {
                    mainView.showGamesToLoad(gameModel.loadGameNames());
                }else {
                    startScreenView.showNoGamesToLoadMessage();
                }
            }
            case StartScreenView.STATS_BUTTON, JBarraPanel.STATS_BUTTON -> {mainView.showStats(gameModel.getAllUsernames());}
            case SelectPlayer.GO_BUTTON -> {
                gameModel.startLoadedGame(selectGameToLoad.getSelectedItem());
                mainView.startGame();
            }
            case JBarraPanel.EXIT -> {
                mainView.returnToMenu();
            }
        }
    }
}
