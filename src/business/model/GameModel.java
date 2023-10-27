package business.model;

import business.*;
import persistence.ConfigurationDAO;
import persistence.ConfigurationJsonDAO;
import presentation.PlayController;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * GameModel class. This class is responsible for managing the Game logic. It is the intermediary between the Presentation layer and the Business layer.
 */
public class GameModel {
    /**
     * GameBoardManager to manage GameBoard objects
     */
    private final GameBoardManager gameBoardManager;
    /**
     * ShipManager to manage Ship objects
     */
    private final ShipManager shipManager;
    /**
     * GameManager to manage Game objects
     */
    private final GameManager gameManager;

    /**
     * GameBoard object of the current player.
     */
    private GameBoard playerGameBoard;
    /**
     * User object of the current user.
     */
    private User currentUser;
    /**
     * ArrayList of AIPlayer objects. This list contains all the AI players in the game.
     */
    private ArrayList<AIPlayer> aiPlayers;
    /**
     * Boolean to determine the submarine selected.
     */
    private boolean submarineToAlternate;
    /**
     * ConfigurationDAO to read the configuration file.
     */
    private final ConfigurationDAO configurationDAO;
    /**
     * TimerThread to manage the timer.
     */
    private TimerThread timerThread;
    /**
     * TrafficLightThread to manage the traffic light.
     */
    private TrafficLightThread trafficLightThread;
    /**
     * AIThread to manage the AI players.
     */
    private AIThread aiThread;
    /**
     * PlayController to register the controller to the threads.
     */
    private PlayController playController;
    /**
     * UserManager to manage User objects
     */
    private final UserManager userManager;
    /**
     * Player object of the current player
     */
    private Player currentPlayer;
    /**
     * PlayerManager to manage Player objects
     */
    private final PlayerManager playerManager;
    private String currentGameName;
    private int originalHits;

    /**
     * GameModel constructor. Initializes the GameBoardManager and ShipManager.
     */
    public GameModel() {
        gameBoardManager = new GameBoardManager();
        playerManager = new PlayerManager();
        userManager = new UserManager();
        shipManager = new ShipManager();
        playerGameBoard = new GameBoard();
        gameBoardManager.initBoard(playerGameBoard);
        this.aiPlayers = new ArrayList<>();
        this.configurationDAO = new ConfigurationJsonDAO();
        this.aiThread = new AIThread(this);
        this.gameManager = new GameManager();
        this.timerThread = new TimerThread();
        this.trafficLightThread = new TrafficLightThread(getRechargeTime());
    }

    /**
     *
     */
    public void startGame(int aiPlayers) {
        this.currentGameName = gameManager.getNotDefinedName();
        gameManager.createGame(this.currentGameName, (new Date()).toString(), GameManager.NOT_ENDED, timerThread.getTime(), GameManager.PATH_NOT_DEFINED, userManager.getName(currentUser), GameManager.ZERO_HITS);
        timerThread.registerController(playController);
        aiThread.registerController(playController);
        trafficLightThread.registerController(playController);
        initAIPlayers(aiPlayers);
        aiThread.init(this.aiPlayers, configurationDAO.getTime());
        timerThread.resetTime();
        this.currentPlayer = new Player(playerGameBoard, userManager.getName(currentUser));
        aiThread.start();
        timerThread.start();
        trafficLightThread.start();
    }

    /**
     * @param aiPlayers number of AI players to add to the game
     *                  Initializes the AI players. If there is only one AI player, it will be a intelligent AI player.
     */
    private void initAIPlayers(int aiPlayers) {
        if(aiPlayers == 1){
            this.aiPlayers.add(new AIPlayer(gameBoardManager.generateRandomGameBoard(), "AI Number 1", false));
        }else {
            for (int i = 0; i < aiPlayers; i++) {
                this.aiPlayers.add(new AIPlayer(gameBoardManager.generateRandomGameBoard(), "AI Number " + (i+1), true));
            }
        }
    }

    /**
     * @param direction direction to move the ship
     * @param index    index of the ship to move
     * @return int[][] of the new coordinates of the ship
     */
    public int[][] moveShip(String direction, int index) {
        shipManager.moveShip(direction, gameBoardManager.getShip(playerGameBoard, index));
        return shipManager.getCoordinates(gameBoardManager.getShip(playerGameBoard, index));
    }

    /**
     * @param index index of the ship to select
     * @return int[][] of the new coordinates of the ship
     */
    public int[][] selectShip(int index) {
        Ship selectedShip = gameBoardManager.getShip(playerGameBoard, index);
        return shipManager.getCoordinates(selectedShip);
    }

    /**
     * @param index index of the ship to drop
     * @return int[][] of the new coordinates of the ship
     * Drops the ship in the GameBoard.
     */
    public int[][] dropShip(int index) {
        Ship selectedShip = gameBoardManager.getShip(playerGameBoard, index);
        shipManager.postShip(selectedShip);
        //JStateTable.setState("arriba","siuuu","j","h","u");
        return shipManager.getCoordinates(selectedShip);
    }

    /**
     * @param index index of the ship to get
     * @return int[][] of the coordinates of the ship
     */
    public int[][] getSelectedShip(int index) {
        Ship selectedShip = gameBoardManager.getShip(playerGameBoard, index);
        return shipManager.getCoordinates(selectedShip);
    }

    /**
     * @param confirmedCoordinates coordinates of the ship to confirm
     * @param SELECTED_INDEX      index of the ship to confirm
     * @return boolean if the ship is in a valid position returns true, else returns false.
     */
    public boolean confirmShip(ArrayList<int[][]> confirmedCoordinates, int SELECTED_INDEX) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        for(int[][] coordinate : confirmedCoordinates) {
            coordinates.addAll(shipManager.getCellCoordinates(coordinate));
        }
        return shipManager.confirmShip(coordinates, this.getSelectedShip(SELECTED_INDEX));
    }

    /**
     * @return The list of saved games of the current user.
     */
    public ArrayList<String> loadGameNames() {
        return gameManager.getGameNames(userManager.getName(currentUser));
    }

    /**
     * Saves the current game.
     */
    public void saveGame(String name) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, currentPlayer);
        players.addAll(this.aiPlayers);
        gameManager.saveGame(name, players);
        gameManager.updateGame(name, timerThread.getTime(), Path.of("files", name+".json").toString(), this.currentGameName);
    }

    /**
     * @param name name of the ship to free
     * @return int[][] of the new coordinates of the ship
     * Unpost the ship in the GameBoard.
     */
    public int[][] freeShip(String name) {
        Ship selectedShip = gameBoardManager.getShipByName(playerGameBoard, name);
        shipManager.freeShip(selectedShip);
        return shipManager.getCoordinates(selectedShip);
    }

    /**
     * @return String of the name of the ship to alternate
     * Alternates the submarine to place.
     */
    public String alternateSubmarine() {
        submarineToAlternate = !submarineToAlternate;
        if(submarineToAlternate) {
            return Ship.SUBMARINE_2;
        }else {
            return Ship.SUBMARINE_1;
        }

    }

    /**
     * @return boolean if the game is ready to start
     */
    public boolean gameReady() {
        return gameBoardManager.allShipsPlaced(playerGameBoard);
    }

    /**
     * @param pos  position to check
     * @param index index of the player to check
     * @param shooterIndex index of the player that is shooting
     * @return true if the position is a hit
     */
    public boolean checkHit(int[] pos, int index, int shooterIndex) {
        if(index == -1) {
            return gameBoardManager.checkHit(playerGameBoard, pos, shooterIndex);
        }else {
            return gameBoardManager.checkHit(aiPlayers.get(index).getGameBoard(), pos, shooterIndex);
        }
    }

    /**
     * @return true if any of the players has been defeated.
     */
    public ArrayList<AIPlayer> checkEndGame() {
        ArrayList<AIPlayer> alivePlayers = new ArrayList<>();
        for (AIPlayer aiPlayer:aiPlayers) {
            if(!gameBoardManager.checkEndGame(aiPlayer.getGameBoard())){
                alivePlayers.add(aiPlayer);
            }
        }
        return alivePlayers;
    }


    /**
     * @return int of the recharge time
     */
    public int getRechargeTime(){
        return configurationDAO.getTime();
    }

    /**
     * @return int of the number of players
     * This will be used to create the number of players in the game.
     */
    public int getPlayerNumber() {
        return aiPlayers.size();
    }

    /**
     * @param pos position to check
     * @param shooterIndex index of the player that shot
     * @return true if all the players have at least one player has the position free from any shot.
     */
    public synchronized boolean checkShot(int[] pos, int shooterIndex) {
        for (int i = 0; i < aiPlayers.size(); i++) {
            if(i != shooterIndex) {
                if(!gameBoardManager.checkShot(aiPlayers.get(i).getGameBoard(), pos) && !checkGameOver(i)){
                    return false;
                }
            }
        }
        // Check shot on the user
        if(shooterIndex != -1) {
            if(!gameBoardManager.checkShot(playerGameBoard, pos) && !checkGameOver(-1)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param gameBoardIndex index of the player to check
     * @return true if the game is over
     */
    public synchronized boolean checkGameOver(int gameBoardIndex) {
        if(gameBoardIndex == -1) {
            return gameBoardManager.checkEndGame(playerGameBoard);
        }else {
            return gameBoardManager.checkEndGame(aiPlayers.get(gameBoardIndex).getGameBoard());
        }
    }

    /**
     * @param hitCells ArrayList of the hit cells
     * @param j index of the player to check
     * @return true if the ship has been sunk
     * This method will be used to check if the ship has been sunk. This method uses ship positions and hit cells.
     */
    public boolean checkSunk(ArrayList<int[]> hitCells, int j) {
        if(j == -1) {
            return gameBoardManager.checkSunk(playerGameBoard, hitCells);
        }else {
            return gameBoardManager.checkSunk(aiPlayers.get(j).getGameBoard(), hitCells);
        }
    }

    /**
     * @param j index of the player to add the hit
     *          This method will be used to add a hit to the player's GameBoard.
     */
    public void addHit(int j) {
        if(j == -1) {
            gameBoardManager.addHit(playerGameBoard);
        }else {
            gameBoardManager.addHit(aiPlayers.get(j).getGameBoard());
        }
    }

    /**
     * @param shot position to check
     * @param gameBoardIndex index of the player to check
     * @return true if the shot is valid
     */
    public boolean checkCurrentShot(int[] shot, int gameBoardIndex) {
        if(gameBoardIndex == -1) {
            return gameBoardManager.checkShot(playerGameBoard, shot);
        }else {
            return gameBoardManager.checkShot(aiPlayers.get(gameBoardIndex).getGameBoard(), shot);
        }
    }

    /**
     * @param i index of the ship to check
     * @return true if the ship is positioned
     * This method will be used to check if a determined ship is positioned.
     */
    public boolean checkShipPositioned(int i) {
        return gameBoardManager.checkShipPositioned(playerGameBoard, i);
    }

    /**
     * @param j index of the player to check
     * @param i index of the ship to check
     * @return true if the ship is alive
     * This method will be used to check if a determined ship is alive. This method uses the ship's index.
     */
    public boolean checkShipAlive(int j, int i) {
        if(j == -1) {
            return gameBoardManager.checkShipAlive(playerGameBoard, i);
        }else {
            return gameBoardManager.checkShipAlive(aiPlayers.get(j).getGameBoard(), i);
        }
    }

    /**
     * Reset the game to the initial state. This ables the user to play again.
     */
    public void resetGame() {
        aiThread.endGame();
        timerThread.stopThread();
        trafficLightThread.stopThread();
        this.timerThread = new TimerThread();
        this.trafficLightThread = new TrafficLightThread(getRechargeTime());
        this.aiThread = new AIThread(this);
        this.aiPlayers = new ArrayList<>();
    }
    public void resetPreparation() {
        this.playerGameBoard = new GameBoard();
        gameBoardManager.initBoard(playerGameBoard);
        this.submarineToAlternate = false;
    }

    /**
     * @param playController controller to register
     *                       This method will be used to register the controller to the AI thread.
     */
    public void registerControllerAiThread(PlayController playController) {
        this.playController = playController;
    }

    /**
     * Add a win to the user.
     */
    public void addWin() {
        userManager.addWin(this.currentUser);
    }

    /**
     * @param username username of the player
     *                 This method will be used to set the username of the player.
     */
    public void setPlayerName(String username) {
        this.currentUser = new User(username);
    }

    /**
     * Add a game to the user's history.
     */
    public void addGameToHistory() {
        userManager.addGameToHistory(this.currentUser);
    }

    /**
     * @return ArrayList of the user's history.
     */
    public boolean attackAvailable() {
        return trafficLightThread.isAvailable();
    }

    /**
     * Reset the attack. This will be used to reset the attack after the user has attacked.
     */
    public void resetAttack() {
        trafficLightThread.resetAttack();
    }

    /**
     * Reset the data info. This will be used to reset the data info after the user has logged out or deleted the account.
     */
    public void resetDataInfo() {
        this.userManager.resetUser(this.currentUser);
        if(this.currentPlayer != null) {
            this.playerManager.resetPlayer(this.currentPlayer);
        }

    }

    /**
     * Reset the games info. This will be used to reset the games info after the user has deleted the account.
     */
    public void resetGamesInfo() {
        this.gameManager.deleteAllGames(userManager.getName(this.currentUser));
    }

    /**
     * @return array of the user's stats read from the database.
     */
    public int[] getStats(String username) {
        return userManager.getStats(username);
    }

    /**
     * Pause the game.
     */
    public void pauseGame() {
        timerThread.pauseThread();
        trafficLightThread.pauseThread();
        aiThread.pauseThread();
    }

    /**
     * Resume the game.
     */
    public void resumeGame() {
        timerThread.unPauseThread();
        trafficLightThread.unPauseThread();
        aiThread.resumeThread();
    }

    /**
     * @param selectedItem name of the game to load
     *                     This method will be used to load a game.
     */
    public void startLoadedGame(String selectedItem) {
        String[] split = selectedItem.split("-");
        String gameName = split[0].substring(0, split[0].length() - 1);
        this.currentGameName = gameName;
        ArrayList<AIPlayer> players = gameManager.loadGame(gameManager.getFilePath(gameName));
        this.originalHits = Integer.parseInt(gameManager.getHits(gameName));
        timerThread.registerController(playController);
        aiThread.registerController(playController);
        trafficLightThread.registerController(playController);
        this.aiPlayers = new ArrayList<>();
        timerThread.initTime(gameManager.getCurrentTime(gameName));

        for (int i = 1; i < players.size(); i++) {
            this.aiPlayers.add(players.get(i));
        }
        ArrayList<ArrayList<int[]>> hitShots = new ArrayList<>();
        for (AIPlayer player : players) {
            hitShots.add(gameBoardManager.getHitShots(player.getGameBoard()));
        }
        ArrayList<ArrayList<int[]>> missShots = new ArrayList<>();
        for (AIPlayer player : players) {
            missShots.add(gameBoardManager.getMissShots(player.getGameBoard()));
        }

        playController.loadGame(hitShots, missShots);
        aiThread.init(this.aiPlayers, configurationDAO.getTime());
        this.currentPlayer =  players.get(0);
        this.playerGameBoard = playerManager.getGameBoard(currentPlayer);
        aiThread.start();
        timerThread.start();
        trafficLightThread.start();
    }

    /**
     * @return ArrayList of all the users in the database.
     */
    public ArrayList<String> getAllUsernames() {
        return userManager.getAllUsernames();
    }

    /**
     * Finish the game. This will be used to finish the game when the user has won. Update the database.
     */
    public void finishGame() {
        gameManager.finishGame(this.currentGameName);
    }

    /**
     * @param name name of the game
     * @return true if the game can be loaded, false if can't
     */
    public boolean checkGame(String name) {
        if(name.contains(GameManager.NOT_DEFINED)) {
            return false;
        }else {
            if(gameManager.checkGameName(name)){
                return this.currentGameName.equals(name);
            }else {
                return true;
            }
        }
    }

    /**
     * @param name name of the game
     * @return true if the game can be updated, false if it has to create a new game
     */
    public boolean checkUpdateGame(String name) {
        // Check if name contains spaces
        return Objects.equals(this.currentGameName, name) || this.currentGameName.contains(GameManager.NOT_DEFINED);
    }

    /**
     * @param name name of the game
     *             This method will be used to create a game in the database.
     */
    public void createGame(String name) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, currentPlayer);
        players.addAll(this.aiPlayers);
        gameManager.saveGame(name, players);
        gameManager.createGame(name, (new Date()).toString(),GameManager.NOT_ENDED, this.timerThread.getTime(), Path.of("files", name + ".json").toString(), userManager.getName(this.currentUser), gameManager.getHits(this.currentGameName));
        gameManager.updateHits(this.currentGameName, this.originalHits);
    }

    /**
     *Adds a hit to the game in the database.
     */
    public synchronized void addHitToGame() {
        gameManager.addHitToGame(this.currentGameName);
    }

    /**
     * @param username username of the player
     * @return ArrayList of the user's hits read from the database.
     */
    public ArrayList<Point> getStatsHits(String username) {
        ArrayList<String> hits = gameManager.getStatsHits(username);
        ArrayList<String> auxHits = new ArrayList<>(hits);
        ArrayList<Point> points = new ArrayList<>();

        for (String hit:hits) {
            if (auxHits.contains(hit)) {
                points.add(new Point(Integer.parseInt(hit), Collections.frequency(hits, hit)));
                auxHits.removeAll(Collections.singleton(hit));
            }
        }
        return points;
    }
}
