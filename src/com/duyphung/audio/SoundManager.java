package com.duyphung.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

public class SoundManager {
    public static void play(String name) {
        try {
            File file = new File("data/" + name);
            String url = "data/" + name;
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
