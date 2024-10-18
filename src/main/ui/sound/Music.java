package ui.sound;

import sun.audio.*;

import java.io.FileInputStream;
import java.io.IOException;

/*
 * Represents a music player for .wav files
 */
public class Music {

    public static final String SAD_SOUND = "./data/music/sadSound.wav";
    public static final String SOUND_ONE = "./data/music/smallSound.wav";
    public static final String SOUND_TWO = "./data/music/secondSmallSound.wav";
    public static final String SOUND_THREE = "./data/music/thirdSmallSound.wav";
    public static final String SOUND_FOUR = "./data/music/fourthSmallSound.wav";

    /*
     * EFFECTS: plays a sound file
     */
    public static void playSound(String directory) {
        AudioPlayer player = AudioPlayer.player;
        AudioStream stream;
        AudioData data;
        AudioDataStream noLoop = null;

        try {
            stream = new AudioStream(new FileInputStream(directory));
            data = stream.getData();
            noLoop = new AudioDataStream(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start(noLoop);
    }
}