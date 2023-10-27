package presentation;

import business.model.GameModel;
import presentation.view.GraphicStats;
import presentation.view.JBarraPanel;
import presentation.view.MainView;
import presentation.view.SelectPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller of the StatsView.
 */
public class StatsViewController implements ActionListener {

    private final MainView mainView;
    private final GraphicStats statsPanel;
    private final GameModel gameModel;
    private final SelectPlayer selectStatsPlayer;

    /**
     * @param mainView MainView
     * @param gameModel GameModel of the game
     */
    public StatsViewController(MainView mainView, GameModel gameModel) {
        this.gameModel = gameModel;
        this.mainView = mainView;
        this.statsPanel = mainView.getStatsPanel();
        this.selectStatsPlayer = mainView.getSelectStatsPlayer();
        this.selectStatsPlayer.registerListeners(this);
        statsPanel.addActionListener(this);
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case JBarraPanel.EXIT:
                mainView.returnToMenu();
                break;
            case JBarraPanel.LOG_OUT_BUTTON:
                if(statsPanel.logOutConfirmation()) {
                    gameModel.resetDataInfo();
                    mainView.logout();
                }
                break;
            case SelectPlayer.GO_BUTTON:
                statsPanel.setStats(gameModel.getStats(selectStatsPlayer.getSelectedItem()), gameModel.getStatsHits(selectStatsPlayer.getSelectedItem()));
                mainView.showStatsView();
                break;
        }
    }
}
