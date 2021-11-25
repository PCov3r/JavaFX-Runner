package com.runner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{

    GameScene game;
    MenuScene menu;
    LosingScene lose;

    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        Pane menupane = new Pane(root);
        Pane losepane = new Pane(root);
        lose = new LosingScene(game,primaryStage, losepane, 600, 400, true);
        game = new GameScene(primaryStage, lose, pane, true,600, 400, true, 0, 0, 100);
        menu = new MenuScene(primaryStage,menupane,game, 600, 400, true);
        lose.setGameScene(game);
        primaryStage.setScene(menu);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
