package business;

import presentation.PlayController;
/**
 * This class is used to control the time that the player has to wait to be able to shoot again.
 */
public class TrafficLightThread extends Thread {

    private int attackSeconds;
    /**
     * The controller that will update the time in the view.
     */
    private PlayController controller;
    /**
     * The time that the player has to wait to be able to shoot again.
     */
    private final int rechargeTime;
    private boolean stop;
    private boolean resetRequested;
    private boolean pause;

    /**
     * @param rechargeTime The time that the player has to wait to be able to shoot again.
     *                     Constructor for the TimerThread class.
     */
    public TrafficLightThread(int rechargeTime) {
        this.attackSeconds = 0;
        this.rechargeTime = rechargeTime;
        this.stop = false;
        this.resetRequested = false;
        this.pause = false;
    }

    /**
     * Increments the time of the clock every second.
     */
    @Override
    public void run() {
        while (!stop) {
            while (!pause) {
                if (this.isAvailable()) {
                    if (this.isAvailable()) {
                        controller.greenLight();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    if (resetRequested) {
                        resetAttack();
                        resetRequested = false;
                    }
                    continue;
                }

                this.attackSeconds++;
            }
        }
    }

    /**
     * @return true if the time of the clock ables the player to shoot again.
     */
    public boolean isAvailable() {
        return (this.rechargeTime / 1000 <= attackSeconds);
    }

    /**
     * @param playController The controller that will update the time in the view.
     */
    public void registerController(PlayController playController) {
        this.controller = playController;
    }

    /**
     * Resets the attack seconds. This method is used when the player shoots.
     */
    public void resetAttack() {
        this.attackSeconds = 0;
        synchronized (this) {
            resetRequested = true;
            interrupt();
        }

    }

    /**
     * Stops the thread.
     */
    public void stopThread() {
        this.pause = true;
        this.stop = true;
    }

    /**
     * Pauses the thread.
     */
    public void pauseThread() {
        this.pause = true;
    }

    /**
     * Unpauses the thread.
     */
    public void unPauseThread() {
        this.pause = false;
    }
}