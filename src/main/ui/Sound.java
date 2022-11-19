package ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

// Sound manager
public class Sound {
    Clip clip;
    URL[] soundURL;
    int status; // 0 stop 1 playing 2 pause
    long clipTime;

    // EFFECTS: construct sound
    public Sound() {
        soundURL = new URL[2];
        soundURL[0] = getClass().getClassLoader().getResource("music/basic-bounce-drums-for-producers-drake.wav");
        soundURL[1] = getClass().getClassLoader().getResource("music/procidis.wav");
        status = 0;
    }

    // MODIFIES: this
    // EFFECTS: sets a music file
    public void setFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: play or continue to play music
    public void play() {
        if (status == 2) {
            clip.setMicrosecondPosition(clipTime);
        }
        clip.start();
        status = 1;
    }

    // MODIFIES: this
    // EFFECTS: play in loop
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // MODIFIES: this
    // EFFECTS: stop music
    public void stop() {
        status = 0;
        clip.stop();
    }

    public void pause() {
        status = 2;
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }

    // MODIFIES: this
    // EFFECTS: set clip time
    public void setClipTime(long clipTime) {
        this.clipTime = clipTime;
    }

    // MODIFIES: this
    // EFFECTS: set status
    public void setStatus(int status) {
        this.status = status;
    }
}
