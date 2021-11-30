package com.runner;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FadingRectangle {
    private Stage primaryStage;
    private Rectangle fadeRect;
    private FadeTransition fade;

    public FadingRectangle(Stage ps, Pane p, double x, double y, double width, double height, Color color, int duration, int direction) {
        this.primaryStage = ps;
        fadeRect= new Rectangle(x,y,width,height);

        fadeRect.setFill(color);

        fade = new FadeTransition(Duration.millis(duration), fadeRect);

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

    public void setAfter(Scene scene){
        fade.setOnFinished(event -> {
            primaryStage.setScene(scene);
        });
    }

    public void play(){
        fadeRect.toFront();
        fade.play();
    }

    public void reset(){
        fadeRect.setOpacity(0);
        fade.jumpTo(Duration.ZERO);
    }
}
