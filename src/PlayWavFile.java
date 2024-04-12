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
/*
from ev3dev2.sound import Sound

# 创建音频对象
sound = Sound()

# 定义音符和持续时间
notes = ['C3', 'D4', 'E4', 'C4', 'C4', 'D4', 'E4', 'C4', 'E4', 'F4', 'G4', 'E4', 'F4', 'G4', 'G4', 'A4', 'G4', 'F4', 'E4', 'D4', 'C4']
durations = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]

# 播放音乐
for note, duration in zip(notes, durations):
    sound.tone(note, duration * 0.5)  # 每个音符的播放时间为0.5秒

*/