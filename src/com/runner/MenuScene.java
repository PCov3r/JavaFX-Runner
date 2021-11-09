
package com.runner;

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

    public MenuScene(Stage primaryStage, Pane p, GameScene game, double v, double v1, boolean b) {
        super(p, v, v1, b);
        this.p = p;
        Image back = new Image("D:\\Documents\\Java projects\\Runner\\src\\menuback.jpg",v, v1,false,true);
        ImageView background = new ImageView(back);
        Text title = new Text(50,90,"JavaFX Runner");
        title.setFill(Color.SNOW);
        title.setStroke(Color.BLACK);
        title.setFont(Font.font ("Impact", 60));

        Rectangle backBox = new Rectangle(80, 100);
        backBox.setFill(Color.WHITESMOKE);
        backBox.setOpacity(0.5);
        backBox.setX(30);
        backBox.setY(130);

        Button playBtn = new Button("Play");
        playBtn.setMinWidth(150);
        playBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        playBtn.setOnAction(e -> {
            primaryStage.setScene(game);
            game.listenKeys();
            game.getTimer().start();
        });

        Button quitBtn = new Button("Quit");
        quitBtn.setMinWidth(150);
        quitBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
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
        box.setTranslateX(50);
        box.setTranslateY(150);
        box.getChildren().addAll(playBtn,quitBtn);

        p.getChildren().addAll( background,title, backBox, box);
    }
}

