package presentation;

import business.model.GameModel;
import presentation.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is the controller for the play panel. It handles the events that occur in the panel such as clicks of the cells.
 */
public class PlayController implements MouseListener, ActionListener {
    /**
     * The main view is used to get the play view.
     */
    private final MainView mainView;
    /**
     * Playview to the controller will work on it.
     */
    private final PlayView playView;
    /**
     * Game model where all the information about the game is stored.
     */
    private final GameModel gameModel;
    /**
     * Boolean to know if the game is over.
     */

    /**
     * @param mainView The main view is used to get the play view.
     * @param gameModel The game model is used to get the player number.
     */
    public PlayController(MainView mainView, GameModel gameModel) {
        this.mainView = mainView;
        this.playView = mainView.getPlayView();
        this.playView.addListener(this);
        this.gameModel = gameModel;
        mainView.getGameBoardPanel(GameBoardPanel.PLAY_MODE).activateCells(this);
        gameModel.registerControllerAiThread(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                    for (int i = 0; i < gameModel.getPlayerNumber(); i++) {
                        for (int j = 0; j < 5; j++) {
                            if (!gameModel.checkShipAlive(i, j)) {
                                playView.updateTable(j, i);
                            }
                        }
                    }
                    for (int i = 0; i < 5; i++) {
                        if(!gameModel.checkShipAlive(-1, i)){
                            playView.updateTable(i, -1);
                        }
                    }
                }
            }
        };
        Thread tableUpdater = new Thread(runnable);
        tableUpdater.start();
    }


    /**
     * @param formattedTime The formatted time to be updated.
     *                      This method is used to update the time in the play view.
     */
    public void updateViewTime(String formattedTime) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playView.updateTime(formattedTime);
            }
        });
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        ClickyCell clickyCell = (ClickyCell) e.getSource();
        if(gameModel.attackAvailable() && !gameModel.checkShot(clickyCell.getPos(), -1) && !gameModel.checkGameOver(-1)){
            gameModel.resetAttack();
            playView.redLight();
            for (int i = 0; i < gameModel.getPlayerNumber(); i++) {
                if(!gameModel.checkGameOver(i)){
                    if(!gameModel.checkCurrentShot(clickyCell.getPos(), i)) {
                        gameModel.addHitToGame();
                        if (gameModel.checkHit(clickyCell.getPos(), i, -1)) {
                            gameModel.addHit(i);
                            playView.aiHit(clickyCell.getPos(), i, -1);
                            if (gameModel.checkGameOver(i)) {
                                eliminatePlayer(i);
                            }
                        } else {
                            playView.aiMiss(clickyCell.getPos(), i, -1);
                        }
                    }
                }
            }

        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * @param nextShot The next shot to be updated.
     * @param gameBoardNumber The game board number to be updated.
     *                        This method is used to update the view when the AI misses.
     */
    public synchronized void AIMiss(int[] nextShot, int gameBoardNumber, int shooterIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playView.aiMiss(nextShot, gameBoardNumber, shooterIndex);
            }
        });
    }

    /**
     * @param nextShot The next shot to be updated.
     * @param gameBoardNumber The game board number to be updated.
     *                        This method is used to update the view when the AI hits.
     */
    public synchronized void AIHit(int[] nextShot, int gameBoardNumber, int shooterIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playView.aiHit(nextShot, gameBoardNumber, shooterIndex);
            }
        });
    }

    /**
     * @param name The name of the winner.
     *             This method is used to end the game.
     */
    public synchronized void gameOver(String name) {
        gameModel.addGameToHistory();
        gameModel.finishGame();
        mainView.endGame(name);
        gameModel.resetGame();
        gameModel.resetPreparation();
        playView.resetAll();
    }

    /**
     * This method is used to exit the game.
     */
    private void exit(){
        mainView.returnToMenu();
        gameModel.resetGame();
        gameModel.resetPreparation();
        playView.resetAll();
    }

    /**
     * @param j The player to be eliminated.
     *          This method is used to eliminate a player.
     */
    public synchronized void eliminatePlayer(int j) {
        playView.eliminatePlayer(j);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), EndGameView.END_GAME)){
            mainView.returnToMenu();
        }else if(e.getActionCommand().equals(JBarraPanel.EXIT)){
            gameModel.pauseGame();
            boolean continua = false;
            while(!continua) {
                if (playView.checkSaveGame()) {
                    String name = playView.askForName();
                    if (gameModel.checkGame(name) && !name.equals("null")) {
                        if(gameModel.checkUpdateGame(name)) {
                            gameModel.saveGame(name);
                        }else{
                            gameModel.createGame(name);
                        }
                        mainView.returnToMenu();
                        exit();
                        continua = true;
                    }else if(name.equals("null")){
                        mainView.returnToMenu();
                        exit();
                        continua = true;
                    }
                }else {
                    mainView.returnToMenu();
                    exit();
                    continua = true;
                }
            }
        }else if(e.getActionCommand().equals(JBarraPanel.LOG_OUT_BUTTON)){
            gameModel.pauseGame();
            if(playView.logOutConfirmation()) {
                boolean continua = false;
                while(!continua) {
                    if (playView.checkSaveGame()) {
                        String name = playView.askForName();
                        if (gameModel.checkGame(name) && !name.equals("null")) {
                            if(gameModel.checkUpdateGame(name)) {
                                gameModel.saveGame(name);
                            }else{
                                gameModel.createGame(name);
                            }
                            continua = true;
                        }else if(name.equals("null")){
                            continua = true;
                        }
                    }else {
                        continua = true;
                    }
                }
                gameModel.resetGame();
                gameModel.resetPreparation();
                playView.resetAll();
                mainView.logout();
                gameModel.resetDataInfo();
            }else{
                gameModel.resumeGame();
            }
        }else if(e.getActionCommand().equals(JBarraPanel.STATS_BUTTON)){
            playView.statsErrorMessage();
        }
    }

    /**
     * This method is used to update the view when the AI is playing. It will turn the light red.
     */
    public void greenLight() {
        playView.greenLight();
    }

    /**
     * This method is used to reset the timer to 00:00.
     */
    public void resetTime() {
        playView.resetTimer();
    }

    /**
     * @param hits The hits to be loaded.
     * @param misses The misses to be loaded.
     *               This method is used to load a game in the view.
     */
    public void loadGame(ArrayList<ArrayList<int[]>> hits, ArrayList<ArrayList<int[]>> misses) {
        playView.loadGameBoards(hits, misses);
    }
}
