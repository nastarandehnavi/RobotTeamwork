import lejos.hardware.Sound;
import java.io.File;

/**
 * Date:04.04.2024
 * The PlayWavFile class implements a thread for playing a WAV file.
 * It loads the specified WAV file and plays it using the Sound class.
 * Author:Team2
 */
public class PlayWavFile implements Runnable {
    
    /**
     * Runs the WAV file playing thread.
     * It loads the specified WAV file and plays it if the file exists.
     * If the file does not exist, it prints a message to the console.
     */
    @Override
    public void run() {
        File musicFile = new File("selfish.wav"); // Specify the path to the WAV file

        // Check if the WAV file exists
        if (musicFile.exists()) {
            Sound.setVolume(100); // Set the volume level to maximum
            Sound.playSample(musicFile); // Play the WAV file
        } else {
            System.out.println("Music file not found!"); // Print a message if the file is not found
        }
    }
}
//music