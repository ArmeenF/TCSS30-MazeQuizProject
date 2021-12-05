package view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A static class for playing sounds.
 * @author Joseph Graves
 * @version Fall 2021
 */
public final class GameSound {

    /**
     * Private Constructor to prevent instantiation.
     */
    private GameSound() {
        //..
    }

    /**
     * Plays a sound given the file name and whether it loops.
     * @param theSound the file name.
     * @param theLoops whether it loops.
     */
    public static void playSound(final String theSound, final boolean theLoops) {
        final File file = new File(theSound);
        final Clip clip;
        try (AudioInputStream input = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(input);
            if (theLoops) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch (final LineUnavailableException | IOException
                 | UnsupportedAudioFileException exception) {
            exception.printStackTrace();
        }
    }
}
