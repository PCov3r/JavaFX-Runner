package com.runner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class Main extends Application{

    /**
     * All the Scenes that compose the runner
     */
    GameScene game;
    MenuScene menu;
    LosingScene lose;
    OptionScene options;

    /**
     * The "hello world" code of the Java Project.
     * Set the different panes to all scenes and link the latter between them.
     * @param primaryStage the stage on which the game will be shown
     */
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
        options.setKeys();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
	// write your code here
    }
}
