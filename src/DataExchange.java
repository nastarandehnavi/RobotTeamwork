/**
 * The DataExchange class manages shared data and synchronization between different parts of the program.
 * It provides methods to access and update various parameters used for communication and control.
 * author: Qiaoqiao
 */
public class DataExchange {

    private float currentThreshold = 0; // Current threshold value for obstacle detection
    private float currentDistance = 0; // Current distance measured by sensors
    private int current_CMD = 0; // Current command for robot behavior

    private long firstTime = 0; // Timestamp for the start time of the program
    private long secondTime = 0; // Timestamp for the end time of the program
    private int obstacleCount = 0; // Count of detected obstacles

    private boolean avoidingObstacle = false; // Flag indicating whether the robot is currently avoiding an obstacle
    private boolean stopThreads = false; // Flag indicating whether the threads should stop

    // Command constants
    public final static int followLine = 1; // Command to follow the line
    public final static int end = 2; // Command to end the program
    public final static int avoid = 3; // Command to avoid an obstacle

    /**
     * Retrieves the current distance measured by sensors.
     *
     * @return The current distance in centimeters
     */
    public synchronized float getCurrentDistance() {
        return currentDistance;
    }

    /**
     * Sets the current distance measured by sensors.
     *
     * @param currentDistance The current distance to set in centimeters
     */
    public synchronized void setCurrentDistance(float currentDistance) {
        this.currentDistance = currentDistance;
        notifyAll();
    }

    /**
     * Retrieves the count of detected obstacles.
     *
     * @return The count of detected obstacles
     */
    public synchronized int getObstacleCount() {
        return obstacleCount;
    }

    /**
     * Sets the count of detected obstacles.
     *
     * @param obstacleCount The count of detected obstacles to set
     */
    public synchronized void setObstacleCount(int obstacleCount) {
        this.obstacleCount = obstacleCount;
        notifyAll();
    }

    /**
     * Retrieves the current command for robot behavior.
     *
     * @return The current command
     */
    public synchronized int getCurrent_CMD() {
        return current_CMD;
    }

    /**
     * Sets the current command for robot behavior.
     *
     * @param current_CMD The command to set
     */
    public synchronized void setCurrent_CMD(int current_CMD) {
        this.current_CMD = current_CMD;
        notifyAll();
    }

    /**
     * Retrieves the timestamp for the start time of the program.
     *
     * @return The timestamp for the start time
     */
    public synchronized long getFirstTime() {
        return firstTime;
    }

    /**
     * Sets the timestamp for the start time of the program.
     *
     * @param firstTime The timestamp for the start time to set
     */
    public synchronized void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
        notifyAll();
    }

    /**
     * Retrieves the timestamp for the end time of the program.
     *
     * @return The timestamp for the end time
     */
    public synchronized long getSecondTime() {
        return secondTime;
    }

    /**
     * Sets the timestamp for the end time of the program.
     *
     * @param secondTime The timestamp for the end time to set
     */
    public synchronized void setSecondTime(long secondTime) {
        this.secondTime = secondTime;
        notifyAll();
    }

    /**
     * Retrieves the current threshold value for obstacle detection.
     *
     * @return The current threshold value
     */
    public synchronized float getCurrentThreshold() {
        return currentThreshold;
    }

    /**
     * Sets the current threshold value for obstacle detection.
     *
     * @param currentThreshold The current threshold value to set
     */
    public synchronized void setCurrentThreshold(float currentThreshold) {
        this.currentThreshold = currentThreshold;
        notifyAll();
    }

    /**
     * Checks if the robot is currently avoiding an obstacle.
     *
     * @return true if the robot is avoiding an obstacle, otherwise false
     */
    public synchronized boolean isAvoidingObstacle() {
        return avoidingObstacle;
    }

    /**
     * Sets the flag indicating whether the robot is avoiding an obstacle.
     *
     * @param avoidingObstacle The flag value to set
     */
    public synchronized void setAvoidingObstacle(boolean avoidingObstacle) {
        this.avoidingObstacle = avoidingObstacle;
        notifyAll();
    }

    /**
     * Date:04.04.2024
     * Checks if the threads should stop.
     *
     * @return true if the threads should stop, otherwise false
     * Author:Team2
     */
    public synchronized boolean isStopThreads() {
        return stopThreads;
    }

    /**
     * Sets the flag indicating whether the threads should stop.
     *
     * @param stopThreads The flag value to set
     */
    public synchronized void setStopThreads(boolean stopThreads) {
        this.stopThreads = stopThreads;
        notifyAll();
    }

    /**
     * Records the detection of an obstacle.
     * If the robot is not currently avoiding an obstacle, increments the obstacle count and sets the avoidingObstacle flag to true.
     */
    public synchronized void recordObstacleDetection() {
        if (!isAvoidingObstacle()) {
            obstacleCount++;
            avoidingObstacle = true;
        }
    }
}
