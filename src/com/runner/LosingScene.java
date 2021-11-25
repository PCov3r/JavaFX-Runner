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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LosingScene extends Scene {
    Text scoreTxt;
    private Pane p;
    private VBox box;
    Stage primaryStage;
    GameScene gameScene;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";


    public LosingScene(GameScene gms, Stage primaryStage, Pane p, double v, double v1, boolean b) {
        super(p, v, v1, b);
        this.p = p;
        this.primaryStage = primaryStage;
        this.gameScene = gms;
        Image back = new Image(".\\menuback.jpg",v, v1,false,true);
        ImageView background = new ImageView(back);


        Text title = new Text(50,90,"Votre course s'arrête ici");
        title.setFill(Color.SNOW);
        title.setStroke(Color.BLACK);
        title.setFont(Font.font ("Impact", 40));

        Button reloadBtn = new Button("Rejouer");
        reloadBtn.setMinWidth(150);
        reloadBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        reloadBtn.setOnMouseEntered(e -> reloadBtn.setStyle(HOVERED_BUTTON_STYLE));
        reloadBtn.setOnMouseExited(e -> reloadBtn.setStyle(IDLE_BUTTON_STYLE));
        reloadBtn.setOnAction(e -> {
            gameScene.reset();
            primaryStage.setScene(gameScene);
        });

        Button quitBtn = new Button("Quitter");
        quitBtn.setMinWidth(150);
        quitBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
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
        box.getChildren().addAll(reloadBtn, quitBtn);

        scoreTxt = new Text(350,250,"");
        scoreTxt.setFill(Color.SNOW);
        scoreTxt.setStroke(Color.BLACK);
        scoreTxt.setFont(Font.font ("Helvetica", 80));

        p.getChildren().addAll( background, title, box, scoreTxt);
    }

    public void showScore(Integer score){
        scoreTxt.setText(score.toString()+"m");
    }

    public void setGameScene(GameScene gms){
        this.gameScene = gms;
    }
}
