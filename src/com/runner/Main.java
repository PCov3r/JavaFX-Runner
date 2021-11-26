package com.runner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{

    GameScene game;
    MenuScene menu;
    LosingScene lose;
    OptionScene options;

    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        Pane menupane = new Pane(root);
        Pane losepane = new Pane(root);
        Pane optionspane = new Pane(root);
        lose = new LosingScene(primaryStage, losepane, 600, 400);
        game = new GameScene(primaryStage, pane, true,600, 400,  0, 0, 100);
        menu = new MenuScene(primaryStage,menupane, 600, 400);
        options = new OptionScene(primaryStage,optionspane,600,400);

        lose.setScene(game);
        game.setScene(lose);
        menu.setScene(game, options);
        options.setScene(menu, game);

        primaryStage.setScene(menu);
        options.setJumpKey();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
