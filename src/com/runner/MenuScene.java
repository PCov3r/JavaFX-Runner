
package com.runner;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;


public class MenuScene extends Scene {
    private Pane p;
    private VBox box;
    private Hero myhero;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";

    public MenuScene(Stage primaryStage, Pane p, GameScene game, double v, double v1, boolean b) {
        super(p, v, v1, b);
        this.p = p;
        Image back = new Image(".\\img\\menuback.jpg",v, v1,false,true);
        ImageView background = new ImageView(back);

        myhero = new Hero(300, 100, 0, 0,100_000_000,6,85,100,85);
        myhero.getImgview().setPreserveRatio(true);
        myhero.getImgview().setFitHeight(250);

        Text title = new Text(50,90,"JavaFX Runner");
        title.setFill(Color.SNOW);
        title.setStroke(Color.BLACK);
        title.setFont(Font.font ("Impact", 60));

        Rectangle backBox = new Rectangle(150, 100);
        backBox.setFill(Color.WHITESMOKE);
        backBox.setOpacity(0.5);
        backBox.setX(50);
        backBox.setY(150);

        Button playBtn = new Button("Jouer");
        playBtn.setMinWidth(150);
        playBtn.setStyle("-fx-hover-color: red ; -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        playBtn.setOnMouseEntered(e -> playBtn.setStyle(HOVERED_BUTTON_STYLE));
        playBtn.setOnMouseExited(e -> playBtn.setStyle(IDLE_BUTTON_STYLE));
        playBtn.setOnAction(e -> {
            primaryStage.setScene(game);
            game.listenKeys();
            game.Start();
        });

        Button quitBtn = new Button("Quitter");
        quitBtn.setMinWidth(150);
        quitBtn.setStyle("-fx-hover-color: red ; -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
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

        box = new VBox(5);
        box.setTranslateX(70);
        box.setTranslateY(170);
        box.getChildren().addAll(playBtn,quitBtn);

        p.getChildren().addAll( background,myhero.getImgview(),title, backBox, box);
        timerAnim.start();
    }

    public void render() {
        myhero.getImgview().setX(myhero.getXcoor());
        myhero.getImgview().setY(myhero.getYcoor());
    }


    private AnimationTimer timerAnim = new AnimationTimer() {
        private long lastUpdate = 0 ;
        private long lastfoeGen = 0;

        @Override
        public void handle(long now) {
                myhero.update(now, 0);
                render();
                lastUpdate = now ;
            }
    };
}

