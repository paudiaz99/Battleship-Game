package presentation;

import business.model.GameModel;
import presentation.view.EndGameView;
import presentation.view.JBarraPanel;
import presentation.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the end game view.
 */
public class EndGameController implements ActionListener {
    private final MainView mainView;
    private final EndGameView endGameView;
    private final GameModel gameModel;

    /**
     * @param mainView the main view of the application
     * @param gameModel the model of the game
     */
    public EndGameController(MainView mainView, GameModel gameModel){
        this.mainView = mainView;
        this.endGameView = mainView.getEndGameView();
        this.gameModel = gameModel;
        endGameView.registerListener(this);
    }

    /**
     * @param e the event to be processed by the controller
     *          Processes the event and calls the appropriate method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case JBarraPanel.EXIT:
                mainView.returnToMenu();
                break;
            case JBarraPanel.LOG_OUT_BUTTON:
                if(endGameView.logOutConfirmation()) {
                    gameModel.resetDataInfo();
                    mainView.logout();
                }
                break;
            case JBarraPanel.STATS_BUTTON:
                mainView.showStats(gameModel.getAllUsernames());
                break;

        }
    }
}
