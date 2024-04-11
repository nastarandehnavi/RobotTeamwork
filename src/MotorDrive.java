import lejos.hardware.motor.Motor;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * Date:04.04.2024
 * The MotorDrive class controls the movement of the robot's motors based on the commands received from DataExchange.
 * It implements the Runnable interface to run as a separate thread.
 * Author:Team2
 */
public class MotorDrive implements Runnable {

    private DataExchange DE; // DataExchange object to communicate with other parts of the program

    final float EDGE_THRESHOLD = 18.0f; // Threshold for edge detection
    final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f; // Threshold for obstacle detection

    /**
     * Constructs a new MotorDrive object with the specified DataExchange object.
     *
     * @param dataExchange The DataExchange object for communication
     */
    public MotorDrive(DataExchange dataExchange) {
        this.DE = dataExchange;
    }

    /**
     * Main method controlling the behavior of the robot's motors based on the current command received from DataExchange.
     * Runs in a loop until the stopThreads flag is set to true.
     */
    @Override
    public void run() {
        // Display team information
        System.out.println("");
        System.out.println("Hi, we are Team-2!");

        // Main loop for motor control
        while (!DE.isStopThreads()) {
            if (DE.getCurrent_CMD() == DataExchange.followLine) {
                // Display message for following the line
                LCD.clear();
                LCD.drawString("Following line!", 1, 6);
                followLine(); // Follow the line behavior
            } else if (DE.getCurrent_CMD() == DataExchange.avoid) {
                // Display message for avoiding obstacles
                LCD.clear();
                LCD.drawString("Avoiding Obstacle!", 0, 3);
                LCD.drawString("Detection closed!", 0, 4);
                avoidObstacle(); // Avoid obstacle behavior
            } else if (DE.getCurrent_CMD() == DataExchange.end) {
                // Perform end actions and stop the threads
                end();
                DE.setSecondTime(System.currentTimeMillis());
                LCD.clear();
                long time = DE.getSecondTime() - DE.getFirstTime();
                int timeSeconds = (int) (time / 1000);
                LCD.drawString("Time is:", 1, 3);
                LCD.drawString(timeSeconds + " seconds", 1, 4);
                LCD.drawString("Program ends!", 1, 6);
                LCD.drawString("Thank you!", 1, 7);
                Delay.msDelay(5000); // Delay for readability
                DE.setStopThreads(true); // Set stopThreads flag to true
            }
        }
    }

    /**
     * Controls the movement of the motors to follow the line based on the current threshold.
     */
    private void followLine() {
        if (DE.getCurrentThreshold() < EDGE_THRESHOLD) {
            // Move motors forward with different speeds for line following
            Motor.C.setSpeed(200);
            Motor.D.setSpeed(50);
            Motor.C.forward();
            Motor.D.forward();
        } else {
            // Move motors forward with different speeds for line following
            Motor.C.setSpeed(50);
            Motor.D.setSpeed(200);
            Motor.C.forward();
            Motor.D.forward();
        }
    }

    /**
     * Controls the movement of the motors to perform the end actions.
     * Stops and closes the motors.
     */
    private void end() {
        // Set different speeds sequentially and then stop the motors
        Motor.C.setSpeed(150);
        Motor.D.setSpeed(150);
        Motor.C.setSpeed(100);
        Motor.D.setSpeed(100);
        Motor.C.setSpeed(50);
        Motor.D.setSpeed(50);
        Motor.C.setSpeed(20);
        Motor.D.setSpeed(20);
        Motor.C.stop();
        Motor.D.stop();
        Motor.C.close();
        Motor.D.close();
    }

    /**
     * Controls the movement of the motors to avoid obstacles.
     * Stops the motors after avoiding the obstacle.
     */
    private void avoidObstacle() {
        // Move motors forward with different speeds for obstacle avoidance
        Motor.C.setSpeed(100);
        Motor.D.setSpeed(200);
        Motor.C.forward();
        Motor.D.forward();
        Delay.msDelay(2350); // Delay for moving forward

        // Turn the robot to avoid the obstacle
        Motor.C.setSpeed(200);
        Motor.D.setSpeed(120);
        Motor.C.forward();
        Motor.D.forward();
        Delay.msDelay(2900); // Delay for turning

        // Continue moving forward after avoiding the obstacle
        Motor.C.setSpeed(200);
        Motor.D.setSpeed(120);
        Motor.C.forward();
        Motor.D.forward();
        Delay.msDelay(2900); // Delay for moving forward

        DE.setAvoidingObstacle(false); // Set avoidingObstacle flag to false after avoiding
    }
}
