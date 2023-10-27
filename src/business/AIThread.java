package business;

import business.model.GameModel;
import presentation.PlayController;

import java.util.ArrayList;

/**
 * This class is used to generate shots for the AIPlayers.
 */
public class AIThread extends Thread{
    private static final String YOU_WIN = "YOU WIN!";
    private static final String DRAW = "DRAW!";
    private static final String AI_WIN = "WINS!";
    /**
     * ArrayList of AIPlayers that will be used to generate shots.
     */
    private ArrayList<AIPlayer> aiPlayers;
    /**
     * GameModel that will be used to check if shots are valid.
     */
    private GameModel gameModel;
    /**
     * Recharge time for the AIPlayers.
     */
    private int rechargeTime;
    /**
     * PlayController that will be used to update the view.
     */
    private PlayController controller;
    private boolean gameOver;
    private boolean pause;

    public AIThread(GameModel gameModel){
        aiPlayers = new ArrayList<>();
        this.gameModel = gameModel;
        rechargeTime = 0;
        controller = null;
        gameOver = false;
        pause = false;
    }

    /**
     * Run method for AIThread. Generates shots for each AIPlayer and checks if they are valid.
     * If they are valid, it checks if they are hits or misses and updates the view accordingly.
     * If the AIPlayer has no more ships, it is eliminated.
     */
    @Override
    public void run() {
        boolean correctShot;
        gameLoop:
        while (!gameOver) {
            while (!pause) {
                try {
                    Thread.sleep(rechargeTime);
                } catch (InterruptedException ignored) {
                }
                for (int i = 0; i < aiPlayers.size(); i++) {
                    // Initialize shot
                    int[] shot = new int[3];
                    do {
                        // Generate shot. If it is not valid, generate another one.
                        int[] aux = aiPlayers.get(i).generateNewShootCoordinates();
                        shot[0] = aux[0];
                        shot[1] = aux[1];
                        correctShot = !gameModel.checkShot(shot, i);
                    } while (!correctShot);
                    // Shoot at other AIPlayers
                    for (int j = 0; j < aiPlayers.size(); j++) {
                        // Check if the AIPlayer is not shooting at itself and if it is not eliminated
                        if (j != i && !gameModel.checkGameOver(j)) {
                            // Check if the shot has already been made in the current board.
                            if (!gameModel.checkCurrentShot(shot, j)) {
                                gameModel.addHitToGame();
                                // Check if the shot is a hit or a miss
                                if (gameModel.checkHit(shot, j, i)) {
                                    // Hit
                                    gameModel.addHit(j);
                                    controller.AIHit(shot, j, i);
                                    aiPlayers.get(i).hitsShip(shot);
                                    // Check if the ship has been sunk
                                    if (gameModel.checkSunk(aiPlayers.get(i).getHitCells(), j)) {
                                        aiPlayers.get(i).sunk();
                                    }
                                    // Check if the AIPlayer has no more ships
                                    if (gameModel.checkGameOver(j)) {
                                        controller.eliminatePlayer(j);
                                    }
                                    // Check if the game has ended
                                    if (checkEndGame()) {
                                        break gameLoop;
                                    }
                                } else {
                                    // Miss
                                    controller.AIMiss(shot, j, i);
                                    aiPlayers.get(i).missShip(shot);
                                }
                            }
                        }
                    }
                    // Check if the Player has already been shot at shot coordinates.
                    if (!gameModel.checkCurrentShot(shot, -1)) {
                        // Check if the Player has no more ships
                        if (!gameModel.checkGameOver(-1)) {
                            gameModel.addHitToGame();
                            // Check if the shot is a hit or a miss
                            if (gameModel.checkHit(shot, -1, i)) {
                                // Hit
                                gameModel.addHit(-1);
                                controller.AIHit(shot, -1, i);
                                aiPlayers.get(i).hitsShip(shot);
                                // Check if the ship has been sunk
                                if (gameModel.checkSunk(aiPlayers.get(i).getHitCells(), -1)) {
                                    aiPlayers.get(i).sunk();
                                }
                                // Check if the Player has no more ships
                                if (gameModel.checkGameOver(-1)) {
                                    controller.eliminatePlayer(-1);
                                }
                                // Check if the game has ended
                                if (checkEndGame()) {
                                    break gameLoop;
                                }
                            } else {
                                // Miss
                                controller.AIMiss(shot, -1, i);
                                aiPlayers.get(i).missShip(shot);
                            }
                        }
                    }
                    if (checkEndGame()) {
                        break;
                    }
                }
            }
        }
        }

    private boolean checkEndGame() {
        // Check if the game has ended
        if(gameModel.checkEndGame().size() == 0 && !gameModel.checkGameOver(-1)){
            controller.gameOver(AIThread.YOU_WIN);
            gameModel.addWin();
            this.pause = true;
            this.gameOver = true;
            return true;
        }else if(gameModel.checkEndGame().size() == 1 && gameModel.checkGameOver(-1)){
            controller.gameOver(gameModel.checkEndGame().get(0).getName()+" "+AIThread.AI_WIN);
            this.pause = true;
            this.gameOver = true;
            return true;
        }else if(gameModel.checkEndGame().size() == 0 && gameModel.checkGameOver(-1)){
            controller.gameOver(AIThread.DRAW);
            this.pause = true;
            this.gameOver = true;
            return true;
        }
        return false;
    }

    /**
     * @param playController PlayController that will be used to update the view.
     *                       Registers the PlayController.
     */
    public void registerController(PlayController playController) {
        this.controller = playController;
    }

    /**
     * @param aiPlayers ArrayList of AIPlayers that will be used to generate shots.
     * @param time Recharge time for the AIPlayers.
     *             Initializes the AIThread.
     */
    public void init(ArrayList<AIPlayer> aiPlayers, int time) {
        this.aiPlayers = new ArrayList<>();
        this.aiPlayers.addAll(aiPlayers);
        this.rechargeTime = time;
        this.gameOver = false;
    }

    /**
     * Pauses the thread.
     */
    public void pauseThread() {
        pause = true;
    }

    /**
     * Resumes the thread.
     */
    public void resumeThread() {
        pause = false;
    }

    /**
     * Ends the game.
     */
    public void endGame() {
        gameOver = true;
        pause = true;
    }
}
