package main;

import presentation.*;
import business.model.GameModel;
import presentation.view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView mainView = new MainView();
                GameModel gameModel = new GameModel();

                EndGameController endGameController = new EndGameController(mainView, gameModel);
                StatsViewController statsViewController = new StatsViewController(mainView, gameModel);
                LoginController loginController = new LoginController(mainView, gameModel);
                StartScreenViewController startScreenViewController = new StartScreenViewController(mainView, gameModel);
                GameBoardController gameBoardController = new GameBoardController(mainView, gameModel);
                PlayController playController = new PlayController(mainView, gameModel);
                PreparationController preparationController = new PreparationController(mainView, gameModel);

                mainView.start();
            }
        });

    }
}