package com.runner;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        GameScene scene = new GameScene(pane, 600, 400, true, 100, 0); //On définit la taille de la fenêtre de jeu et la position de la caméra
        root.getChildren().addAll(scene.getBackground1().getImgview(),scene.getBackground2().getImgview(), scene.getMyhero().getImgview()); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); //On définit la taille de fenêtre comme fixe
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
