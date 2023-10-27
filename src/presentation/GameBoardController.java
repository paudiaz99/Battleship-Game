package presentation;

import business.Ship;
import business.model.GameModel;
import presentation.view.GameBoardPanel;
import presentation.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controller of the GameBoardPanel. This controller controls the movement of the ships and the confirmation of them during the preparation phase.
 */
public class GameBoardController implements KeyListener, ActionListener {
    /**
     * GameBoardPanel to paint the ships on.
     */
    private final GameBoardPanel gameBoardPanel;
    /**
     * GameModel object to get the ships from.
     */
    private final GameModel gameModel;

    /**
     * @param mainView MainView to get the GameBoardPanel from.
     * @param gameModel GameModel object to get the ships from.
     */
    public GameBoardController(MainView mainView, GameModel gameModel) {
        this.gameBoardPanel = mainView.getGameBoardPanel(GameBoardPanel.PREPARATION_MODE);
        this.gameBoardPanel.addActionListener(this);
        this.gameBoardPanel.addKeyListener(this);
        this.gameModel = gameModel;
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        gameBoardPanel.reset();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> gameBoardPanel.paintShip(gameModel.moveShip(Ship.UP, gameBoardPanel.getShipIndex()));
            case KeyEvent.VK_DOWN -> gameBoardPanel.paintShip(gameModel.moveShip(Ship.DOWN, gameBoardPanel.getShipIndex()));
            case KeyEvent.VK_LEFT -> gameBoardPanel.paintShip(gameModel.moveShip(Ship.LEFT, gameBoardPanel.getShipIndex()));
            case KeyEvent.VK_RIGHT -> gameBoardPanel.paintShip(gameModel.moveShip(Ship.RIGHT, gameBoardPanel.getShipIndex()));
            case KeyEvent.VK_SPACE -> gameBoardPanel.paintShip(gameModel.moveShip(Ship.ROTATE, gameBoardPanel.getShipIndex()));
            case KeyEvent.VK_ENTER -> {
                if (!this.gameBoardPanel.allShipsConfirmed()) {
                    if (gameModel.confirmShip(gameBoardPanel.getConfirmedCoordinates(), gameBoardPanel.getShipIndex())) {
                        gameBoardPanel.dropShip(gameModel.selectShip(gameBoardPanel.getShipIndex()));
                        gameBoardPanel.paintShip(gameModel.dropShip(gameBoardPanel.getShipIndex()));
                        gameBoardPanel.checkIndex();
                    } else {
                        gameBoardPanel.paintShip(gameModel.selectShip(gameBoardPanel.getShipIndex()));
                    }
                }
            }
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gameBoardPanel.focus();
        if(gameBoardPanel.allShipsConfirmed()){
            switch (e.getActionCommand()) {
                case GameBoardPanel.SUBMARINE_BUTTON ->
                        gameBoardPanel.removeConfirmedCoordinates(gameModel.freeShip(gameModel.alternateSubmarine()));
                case GameBoardPanel.DESTROYER_BUTTON ->
                        gameBoardPanel.removeConfirmedCoordinates(gameModel.freeShip(Ship.DESTROYER));
                case GameBoardPanel.BOAT_BUTTON ->
                        gameBoardPanel.removeConfirmedCoordinates(gameModel.freeShip(Ship.BOAT));
                case GameBoardPanel.AIRCRAFT_CARRIER_BUTTON ->
                        gameBoardPanel.removeConfirmedCoordinates(gameModel.freeShip(Ship.AIRCRAFT_CARRIER));
            }
        }
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
