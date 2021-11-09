package com.runner;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{

    GameScene game;
    MenuScene menu;
    LosingScene lose;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        Pane menupane = new Pane(root);
        Pane losepane = new Pane(root);
        lose = new LosingScene(primaryStage, losepane, 600, 400, true);
        game = new GameScene(primaryStage, lose, pane, 600, 400, true, 0, 0); //On définit la taille de la fenêtre de jeu et la position de la caméra
        menu = new MenuScene(primaryStage,menupane,game, 600, 400, true);
        primaryStage.setScene(menu);
        primaryStage.setResizable(true); //On définit la taille de fenêtre comme fixe
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
