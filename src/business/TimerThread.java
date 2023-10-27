package business;

import presentation.PlayController;


/**
 * Thread that will update the time in the view. It will be executed in parallel with the game.
 */
public class TimerThread extends Thread{
    private static final int MAX_SECONDS = 59;
    private static final int MAX_MINUTES = 99;
    /**
     * The minutes of the clock.
     */
    private int minutes;
    /**
     * The seconds of the clock.
     */
    private int seconds;
    /**
     * The time of the clock in the format mm:ss.
     */
    private String clockTime;
    /**
     * The controller that will update the time in the view.
     */
    private PlayController controller;
    /**
     * The time that the player has to wait to be able to shoot again.
     */
    private boolean stop;
    private boolean pause;

    /**
     *                   Constructor for the TimerThread class.
     */
    public TimerThread() {
        this.minutes = 0;
        this.seconds = 0;
        this.clockTime = "00:00";
        this.stop = false;
    }

    /**
     * Increments the time of the clock every second.
     */
    @Override
    public void run() {
        while (!stop) {
            while (!pause) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                // Manges the game time
                if (this.seconds >= MAX_SECONDS) {
                    this.seconds = 0;
                    this.minutes++;
                    if (this.minutes >= MAX_MINUTES) {
                        this.minutes = 0;
                    }
                } else {
                    this.seconds++;
                }

                this.clockTime = String.format("%02d:%02d", this.minutes, this.seconds);
                String[] timeParts = clockTime.split("\\s*:\\s*");
                String formattedTime = timeParts[0].replaceAll("(\\d)", "$1") + ":" + timeParts[1].replaceAll("(\\d)", "$1");
                controller.updateViewTime(formattedTime);
            }
        }
    }

    /**
     * @param playController The controller that will update the time in the view.
     */
    public void registerController(PlayController playController) {
        this.controller = playController;
    }

    /**
     * This method is used to stop the thread.
     */
    public void stopThread() {
        this.pause = true;
        this.stop = true;
    }

    /**
     * This method is used to reset the time of the clock.
     */
    public void resetTime() {
        controller.resetTime();
    }

    /**
     * This method is used to pause the thread.
     */
    public void pauseThread() {
        this.pause = true;
    }

    /**
     * This method is used to resume the thread.
     */
    public void unPauseThread() {
        this.pause = false;
    }

    /**
     * @return The time of the clock in the format mm:ss.
     */
    public String getTime() {
        return this.clockTime;
    }

    /**
     * @param currentTime The time that will be set to the clock.
     *                    This method is used to set the time of the clock.
     */
    public void initTime(String currentTime) {
        String[] timeParts = currentTime.split("\\s*:\\s*");
        this.minutes = Integer.parseInt(timeParts[0]);
        this.seconds = Integer.parseInt(timeParts[1]);
        this.clockTime = currentTime;
        controller.updateViewTime(currentTime);
    }
}

