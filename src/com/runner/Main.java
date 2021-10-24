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
import javafx.stage.Stage;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        GameScene scene = new GameScene(pane, 600, 400, true, 0, 0); //On définit la taille de la fenêtre de jeu et la position de la caméra
        root.getChildren().addAll(scene.getBackground1().getImgview(),scene.getBackground2().getImgview(), scene.getMyhero().getImgview(), scene.getMyhero().getImgHearts()); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
        scene.listenKeys();
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); //On définit la taille de fenêtre comme fixe
        //start the timer
        scene.getTimer().handle(0);
        primaryStage.show();
        scene.getTimer().start();

    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
