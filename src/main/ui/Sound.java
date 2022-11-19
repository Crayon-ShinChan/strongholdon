package ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL;
    int status; // 0 stop 1 playing 2 pause
    long clipTime;

    public Sound() {
        soundURL = new URL[2];
        soundURL[0] = getClass().getClassLoader().getResource("music/basic-bounce-drums-for-producers-drake.wav");
        soundURL[1] = getClass().getClassLoader().getResource("music/procidis.wav");
        status = 0;
    }

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

    public void play() {
        if (status == 2) {
            clip.setMicrosecondPosition(clipTime);
        }
        clip.start();
        status = 1;
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        status = 0;
        clip.stop();
    }

    public void pause() {
        status = 2;
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void setClipTime(long clipTime) {
        this.clipTime = clipTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
