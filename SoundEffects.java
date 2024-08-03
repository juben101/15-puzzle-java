import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundEffects {

    private Clip moveClip; 
    private Clip completionClip; 

    public SoundEffects() {

        try {
            // Load sound files for move, success, and completion sound effects
            moveClip = AudioSystem.getClip();
            AudioInputStream moveStream = AudioSystem.getAudioInputStream(new File("Pop-Sound-Effect.wav"));
            moveClip.open(moveStream);

            completionClip = AudioSystem.getClip();
            AudioInputStream completionStream = AudioSystem.getAudioInputStream(new File("Success-1-Sound-EFFECT.wav"));
            completionClip.open(completionStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMoveSound() {
        if (moveClip.isRunning()) {
            moveClip.stop(); 
        }
        moveClip.setFramePosition(0); 
        moveClip.start();
    }

    public void playCompletionSound() {
        if (completionClip.isRunning()) {
            completionClip.stop(); 
        }
        completionClip.setFramePosition(0); 
        completionClip.start(); 
    }
}
