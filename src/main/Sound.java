package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        // Canciones
        soundURL[0] = getClass().getResource("/sound/music/Mercury.wav");
        soundURL[1] = getClass().getResource("/sound/music/Map.wav");
        soundURL[2] = getClass().getResource("/sound/music/BossMain.wav");
        soundURL[3] = getClass().getResource("/sound/music/Venus.wav");

        // Efectos
        soundURL[4] = getClass().getResource("/sound/effects/deathb.wav");
        soundURL[5] = getClass().getResource("/sound/effects/deathr.wav");
        soundURL[6] = getClass().getResource("/sound/effects/deaths.wav");
        soundURL[7] = getClass().getResource("/sound/effects/paind.wav");
        soundURL[8] = getClass().getResource("/sound/effects/painr.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
