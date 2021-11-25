package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public final class GameSound {

    private GameSound() {
        //..
    }

    public static void playSound(final String theSound, final boolean theLoops) {
        File file = new File(theSound);
        Clip clip;
        try (AudioInputStream input = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(input);
            if(theLoops)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException | IOException
                 | UnsupportedAudioFileException exception) {
            exception.printStackTrace();
        }
    }
}
