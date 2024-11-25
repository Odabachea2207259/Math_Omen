package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
        /// Canciones
        soundURL[0] = getClass().getResource("/sound/music/Mercury.wav");
        soundURL[1] = getClass().getResource("/sound/music/Map.wav");
        soundURL[2] = getClass().getResource("/sound/music/BossMain.wav");
        soundURL[3] = getClass().getResource("/sound/music/Venus.wav");

        /// Efectos
        // Daño a enemigos
        soundURL[4] = getClass().getResource("/sound/effects/deathb.wav");
        soundURL[5] = getClass().getResource("/sound/effects/deathr.wav");
        soundURL[6] = getClass().getResource("/sound/effects/deaths.wav");
        soundURL[7] = getClass().getResource("/sound/effects/paind.wav");
        soundURL[8] = getClass().getResource("/sound/effects/painr.wav");

        // Proyectiles
        soundURL[9] = getClass().getResource("/sound/effects/Fire2.wav");

        // UI
        soundURL[10] = getClass().getResource("/sound/effects/Item2A.wav");
        soundURL[11] = getClass().getResource("/sound/effects/Menu2A.wav");
        soundURL[12] = getClass().getResource("/sound/effects/teleport.wav");
        soundURL[13] = getClass().getResource("/sound/effects/Win Jingle2.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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

    public void checkVolume(){
        switch(volumeScale){
            case 0 -> {volume = -80f; break;}
            case 1-> {volume = -20f; break;}
            case 2-> {volume = -12f; break;}
            case 3-> {volume = -5f; break;}
            case 4-> {volume = 1f; break;}
            case 5-> {volume = 6f; break;}
        }

        fc.setValue(volume);
    }
}