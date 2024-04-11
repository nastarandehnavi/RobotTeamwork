import lejos.utility.Delay;

/**
 * Date:04.04.2024
 * The MainClass class is responsible for starting and coordinating the different threads in the program.
 * It initializes instances of ColorDetector, MotorDrive, ObstacleDetector, and PlayWavFile, creates threads for each, and starts them.
 * The threads run concurrently, performing their respective tasks.
 * Author:Team2
 */
public class MainClass {

    /**
     * The main method of the program. It initializes instances of the main components, creates threads, starts them, and waits for their completion.
     *
     * @param args The command line arguments (not used)
     */
    public static void main(String[] args) {

        // Create an instance of DataExchange
        DataExchange DE = new DataExchange();

        // Create instances of ColorDetector, MotorDrive, ObstacleDetector, and PlayWavFile
        ColorDetector colorDetector = new ColorDetector(DE);
        MotorDrive motorDriver = new MotorDrive(DE);
        ObstacleDetector obstacleDetector = new ObstacleDetector(DE);
        PlayWavFile PlayWavFile = new PlayWavFile();

        // Create threads for each component
        Thread colorDetectJob = new Thread(colorDetector);
        Thread motorDriveJob = new Thread(motorDriver);
        Thread obstacleDetectJob = new Thread(obstacleDetector);
        Thread PlayWavFileJob = new Thread(PlayWavFile);

        // Start the threads
        colorDetectJob.start();
        motorDriveJob.start();
        obstacleDetectJob.start();
        PlayWavFileJob.start();

        // Record the start time of the program
        DE.setFirstTime(System.currentTimeMillis());

        // Delay for synchronization purposes
        Delay.msDelay(1000);

        try {
            // Wait for all threads to complete their tasks
            colorDetectJob.join();
            motorDriveJob.join();
            obstacleDetectJob.join();
            PlayWavFileJob.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
