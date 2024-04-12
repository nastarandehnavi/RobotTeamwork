import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

/**
 * Date:04.04.2024
 * The ColorDetector class detects colors using an EV3 color sensor and updates
 * the data exchange object accordingly.
 * It implements the Runnable interface to run as a separate thread.
 * Author:Team2
 */
public class ColorDetector implements Runnable {

    private DataExchange DE; // DataExchange object for communication
    private EV3ColorSensor colorSensor; // EV3 color sensor
    private SampleProvider colorProvider; // Sample provider for color sensor
    private float[] colorProviderSample; // Array to store color sample data

    /**
     * Constructs a ColorDetector object with the specified DataExchange object.
     *
     * @param dataExchange The DataExchange object for communication
     */
    public ColorDetector(DataExchange dataExchange) {
        this.DE = dataExchange;
        colorSensor = new EV3ColorSensor(SensorPort.S3); // Initialize the color sensor
        colorProvider = colorSensor.getRedMode(); // Get the color provider
        colorProviderSample = new float[colorProvider.sampleSize()]; // Initialize the sample array
    }

    /**
     * Runs the thread to detect colors and update the DataExchange object.
     */
    @Override
    public void run() {
        colorSensor.setFloodlight(true); // Activate the color sensor's floodlight

        // Main loop for color detection
        while (!DE.isStopThreads()) {
            // Fetch the color sample and store it in the sample array
            colorProvider.fetchSample(colorProviderSample, 0);
            float intensity = colorProviderSample[0] * 100; // Calculate color intensity
            DE.setCurrentThreshold(intensity); // Update the current threshold in DataExchange

            // Determine the appropriate command based on obstacle count and avoidance status
            if (DE.getObstacleCount() == 0) {
                DE.setCurrent_CMD(DataExchange.followLine);
            } else if (DE.getObstacleCount() == 1 && DE.isAvoidingObstacle()) {
                DE.setCurrent_CMD(DataExchange.avoid);
            } else if (DE.getObstacleCount() == 2) {
                DE.setCurrent_CMD(DataExchange.end);
            } else {
                DE.setCurrent_CMD(DataExchange.followLine);
            }
        }

        colorSensor.close(); // Close the color sensor after thread execution
    }
}

// explanation
//getRedMode() == getMode(Red)