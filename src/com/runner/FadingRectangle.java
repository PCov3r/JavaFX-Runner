package com.runner;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A class to add fading transition between scenes.
 * Draws a rectangle that fades in/out.
 */
public class FadingRectangle {
    private Stage primaryStage;
    private Rectangle fadeRect;
    private FadeTransition fade;
    private int direction;

    /**
     * The constructor allows for very basic controls, like the duration of the transition, the size of it or the direction (fade in/out).
     * @param ps the Stage used to switch between scenes
     * @param p the pane used for the scene we wish to add a transition to
     * @param x the x coordinate of the transition rectangle
     * @param y he y coordinate of the transition rectangle
     * @param width the width of the transition rectangle
     * @param height the height of the transition rectangle
     * @param color the color of the transition rectangle
     * @param duration the duration of our transition
     * @param direction the direction of the fading. Positive for a fade in rectangle, negative for a fade out rectangle
     */
    public FadingRectangle(Stage ps, Pane p, double x, double y, double width, double height, Color color, int duration, int direction) {
        this.primaryStage = ps;
        fadeRect= new Rectangle(x,y,width,height);

        fadeRect.setFill(color);

        fade = new FadeTransition(Duration.millis(duration), fadeRect);

        this.direction = direction;

        if(direction > 0) {
            fade.setFromValue(0);
            fade.setToValue(10);
        }
        else {
            fade.setFromValue(10);
            fade.setToValue(0);
        }

        p.getChildren().add(fadeRect);
    }

    /**
     * Set the scene that will come after the transition end.
     * @param scene the scene to show after transition
     */
    public void setAfter(Scene scene){
        fade.setOnFinished(event -> {
            primaryStage.setScene(scene);
        });
    }

    /**
     * Start the transition. Move it to the front of the screen to make sure it will be visible.
     */
    public void play(){
        fadeRect.toFront();
        fade.play();
    }

    /**
     * Reset the transition.
     */
    public void reset(){
        int opacity = (direction > 0) ? 0 : 10;
        fadeRect.setOpacity(opacity);
        fade.jumpTo(Duration.ZERO);
    }
}
