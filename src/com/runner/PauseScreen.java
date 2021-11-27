package com.runner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class contains all the element required to pause the game while playing.
 * The benefit of creating a class for it is to reduce the amount of "stylizing" code lines in the GameScene.
 */
public class PauseScreen {
    private boolean paused;
    private Stage primaryStage;
    private Pane p;
    private AnimationTimer timer;
    private double screenHeight, screenWidth;
    private VBox box;
    private Rectangle Pause;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";

    /**
     * Create a new pause screen
     * @param p the pane to which the screen will be added
     * @param primaryStage the stage we will close if the player wants to quit
     * @param screenWidth the gameScene width
     * @param screenHeight the GameScene height
     * @param timer the GameScene timer to pause the game
     */
    public PauseScreen(Pane p, Stage primaryStage, double screenWidth, double screenHeight, AnimationTimer timer) {
        this.p = p;
        this.primaryStage = primaryStage;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.timer = timer;

        Pause = new Rectangle(0,0,screenWidth,screenHeight);
        Pause.setFill(Color.GRAY);
        Pause.setOpacity(0.75);
        Pause.setVisible(false);

        Button playBtn = new Button("Reprendre");
        playBtn.setMinWidth(150);
        playBtn.setMinHeight(30);
        playBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        playBtn.setOnMouseEntered(e -> playBtn.setStyle(HOVERED_BUTTON_STYLE));
        playBtn.setOnMouseExited(e -> playBtn.setStyle(IDLE_BUTTON_STYLE));
        playBtn.setOnAction(e -> {
            paused = false;
            box.setVisible(false);
            timer.start();
            Pause.setVisible(false);
        });

        Button quitBtn = new Button("Quitter");
        quitBtn.setMinWidth(150);
        playBtn.setMinHeight(30);
        quitBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        quitBtn.setOnMouseEntered(e -> quitBtn.setStyle(HOVERED_BUTTON_STYLE));
        quitBtn.setOnMouseExited(e -> quitBtn.setStyle(IDLE_BUTTON_STYLE));
        quitBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ce n'est qu'un au revoir");
            alert.setHeaderText("Vous êtes sur le point de fermer l'application");
            alert.setContentText("Vous êtes sûr de vouloir quitter le jeu ?");
            if(alert.showAndWait().get() == ButtonType.OK) {
                primaryStage.close();
            }
        });

        box = new VBox(10);
        box.setVisible(false);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(playBtn,quitBtn);
        box.setTranslateY(screenHeight/2-35);
        box.setTranslateX((screenWidth-150)/2);

        p.getChildren().addAll(Pause, box);
    }

    /**
     * Method to hide the screen by setting all its element to invisible.
     * It also launches the game by starting the timer.
     */
    public void hideScreen(){
        paused = false;
        box.setVisible(false);
        Pause.setVisible(false);
        timer.start();
    }

    /**
     * Method to show the pause screen by setting all its elements to visible and putting it on top of the other elements.
     * It also pauses the game using the GameScene AnimationTimer.
     */
    public void showScreen(){
        paused = true;
        timer.stop();
        Pause.toFront();
        Pause.setVisible(true);
        box.setVisible(true);
        box.toFront();
    }

    /**
     * A method to get the game state : paused or not.
     * @return wether the game is paused or not
     */
    public boolean isPaused() {
        return paused;
    }

}
