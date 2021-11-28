package com.runner;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * MusicPlayer is a simple class used to add music in our game.
 */
public class MusicPlayer {
    private MediaPlayer player;

    /**
     *
     * @param musicPath the path to the audio file
     * @param beginTime the beginning of the music file in seconds
     * @param endTime the end of the music file in seconds
     */
    public MusicPlayer(String musicPath, int beginTime, int endTime) {
        String uriString = new File(musicPath).toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.setStartTime(Duration.seconds(beginTime));
        player.setStopTime(Duration.seconds(endTime));
        player.setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * Start the music player
     */
    public void startMusic(){
        player.play();
    }

    /**
     * Stop the music player
     */
    public void stopMusic(){
        player.stop();
    }


}
