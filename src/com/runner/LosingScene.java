package com.runner;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LosingScene extends Scene {
    private Pane p;
    private VBox box;
    Stage primaryStage;
    GameScene gameScene;

    public LosingScene(GameScene gms, Stage primaryStage, Pane p, double v, double v1, boolean b) {
        super(p, v, v1, b);
        this.p = p;
        this.primaryStage = primaryStage;
        this.gameScene = gms;
        Image back = new Image(".\\menuback.jpg",v, v1,false,true);
        ImageView background = new ImageView(back);

        Rectangle lineTop = new Rectangle(100, 2);
        lineTop.setX(10);
        lineTop.setY(50);


        Button reloadBtn = new Button("Rejouer");
        reloadBtn.setOnAction(e -> {
            gameScene.reload();
            primaryStage.setScene(gameScene);
        });

        Button quitBtn = new Button("Quitter");
        quitBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ce n'est qu'un au revoir");
            alert.setHeaderText("Vous êtes sur le point de fermer l'application");
            alert.setContentText("Vous êtes sûr de vouloir quitter le jeu ?");
            if(alert.showAndWait().get() == ButtonType.OK) {
                primaryStage.close();
            }
        });

        Rectangle lineBot = new Rectangle(100, 2);
        lineBot.setX(10);
        lineBot.setY(100+quitBtn.getHeight());

        box = new VBox(5);
        box.setTranslateX(25);
        box.setTranslateY(75);
        box.getChildren().addAll(quitBtn, reloadBtn);

        p.getChildren().addAll( background, lineTop, lineBot, box);
    }

    public void setGameScene(GameScene gms){
        this.gameScene = gms;
    }
}
