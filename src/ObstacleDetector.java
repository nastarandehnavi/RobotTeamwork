import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Date:04.04.2024
 * The ObstacleDetector class implements a thread for detecting obstacles using an ultrasonic sensor.
 * It continuously measures the distance to objects and updates a DataExchange object based on the distance measurements.
 * Author:Team2
 */
public class ObstacleDetector implements Runnable {

    private DataExchange DE; // DataExchange object for communication with other parts of the program
    private EV3UltrasonicSensor ultrasonicSensor; // Ultrasonic sensor object
    private SampleProvider distanceProvider; // Sample provider for distance measurements
    private float[] distanceSample; // Array to store distance samples

    /**
     * The threshold distance beyond which an obstacle is considered detected.
     */
    final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f;

    /**
     * Constructs a new ObstacleDetector object with the specified DataExchange object.
     *
     * @param dataExchange The DataExchange object for communication
     */
    public ObstacleDetector(DataExchange dataExchange) {
        this.DE = dataExchange;
        ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2); // Initialize ultrasonic sensor
        distanceProvider = ultrasonicSensor.getDistanceMode(); // Get distance mode from the sensor
        distanceSample = new float[distanceProvider.sampleSize()]; // Initialize distance sample array
    }

    /**
     * Runs the obstacle detection thread.
     * Continuously measures the distance to objects and updates the DataExchange object.
     */
    @Override
    public void run() {
        while (!DE.isStopThreads()) {
            distanceProvider.fetchSample(distanceSample, 0); // Fetch distance sample from the sensor
            float distance = distanceSample[0] * 100; // Convert distance sample to centimeters
            DE.setCurrentDistance(distance); // Update current distance in DataExchange

            // Check if the current distance is less than or equal to the obstacle distance threshold
            if (DE.getCurrentDistance() <= OBSTACLE_DISTANCE_THRESHOLD) {
                // If an obstacle is detected, display a message on the LCD and record the detection
                LCD.drawString("Find Obstacle!", 1, 5);
                DE.recordObstacleDetection();
            }

            try {
                Thread.sleep(500); // Delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Delay.msDelay(100); // Additional delay for stability
        }
        ultrasonicSensor.close(); // Close the ultrasonic sensor
    }
}
