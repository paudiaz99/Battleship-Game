package presentation;

import business.model.GameModel;
import presentation.view.JPreparationPanel;
import presentation.view.JBarraPanel;
import presentation.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the controller for the preparation panel. It handles the events that occur in the panel.
 */
public class PreparationController implements ActionListener {
    /**
     * The main view of the application
     */
    private final MainView mainView;
    /**
     * The game model of the application
     */
    private final GameModel gameModel;
    /**
     * The preparation panel of the application (where the ships are placed).
     */
    private final JPreparationPanel preparationPanel;


    /**
     * The AI thread that will be started when the game starts.
     */
    private Thread tableUpdater;

    /**
     * @param mainView  The main view of the application
     * @param gameModel The game model
     */
    public PreparationController(MainView mainView, GameModel gameModel) {
        this.mainView = mainView;
        preparationPanel = mainView.getPreparationPanel();
        preparationPanel.addListener(this);
        this.gameModel = gameModel;
        Runnable tableRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                    for (int i = 0; i < 5; i++) {
                        if (gameModel.checkShipPositioned(i)) {
                            preparationPanel.updateTablePositioned(i);
                        } else {
                            preparationPanel.updateTableNotPositioned(i);
                        }
                    }
                }
            }
        };

        tableUpdater = new Thread(tableRunnable);
        tableUpdater.start();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(JPreparationPanel.START_GAME)) {
            if (gameModel.gameReady()) {
                mainView.startGame();
                gameModel.startGame(preparationPanel.getAIPlayers());
                preparationPanel.resetGameBoard();
            } else {
                preparationPanel.placeAllShipsError();
            }
        } else if (e.getActionCommand().equals(JBarraPanel.EXIT)) {
            gameModel.resetPreparation();
            preparationPanel.resetGameBoard();
            mainView.returnToMenu();
        } else if (e.getActionCommand().equals(JBarraPanel.LOG_OUT_BUTTON)) {
            if(preparationPanel.logOutConfirmation()){
                gameModel.resetPreparation();
                gameModel.resetDataInfo();
                preparationPanel.resetGameBoard();
                mainView.logout();
            }
        } else if (e.getActionCommand().equals(JBarraPanel.STATS_BUTTON)) {
            preparationPanel.showStatsError();
        }
    }
}