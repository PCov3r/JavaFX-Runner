package com.runner;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {
    private MediaPlayer player;

    public MusicPlayer(String musicPath, int beginTime, int endTime) {
        String uriString = new File(musicPath).toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.setStartTime(Duration.seconds(beginTime));
        player.setStopTime(Duration.seconds(endTime));
        player.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void startMusic(){
        player.play();
    }

    public void stopMusic(){
        player.stop();
    }


}
