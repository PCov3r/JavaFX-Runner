package com.runner;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionScene extends Scene {
    private MenuScene menu;
    private GameScene game;
    private Pane p;
    private VBox box;
    private Button jumpBtn, shootBtn, returnBtn;
    private boolean jumpChange = false;
    private boolean shootChange = false;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";

    public OptionScene(Stage primaryStage, Pane p, double width, double height, boolean b) {
        super(p, width, height, b);
        this.p = p;

        returnBtn = new Button("Retour");
        returnBtn.setMinWidth(150);
        returnBtn.setStyle(IDLE_BUTTON_STYLE);
        returnBtn.setOnMouseEntered(e -> returnBtn.setStyle(HOVERED_BUTTON_STYLE));
        returnBtn.setOnMouseExited(e -> returnBtn.setStyle(IDLE_BUTTON_STYLE));
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(menu);
        });
        returnBtn.setFocusTraversable(false);

        jumpBtn = new Button("SPACE");
        jumpBtn.setMinWidth(150);
        jumpBtn.setStyle(IDLE_BUTTON_STYLE);
        jumpBtn.setOnMouseEntered(e -> jumpBtn.setStyle(HOVERED_BUTTON_STYLE));
        jumpBtn.setOnMouseExited(e -> jumpBtn.setStyle(IDLE_BUTTON_STYLE));
        jumpBtn.setOnAction(e -> {
            disableBtn();
            jumpChange = true;
        });


        shootBtn = new Button("SPACE");
        shootBtn.setMinWidth(150);
        shootBtn.setStyle(IDLE_BUTTON_STYLE);
        shootBtn.setOnMouseEntered(e -> shootBtn.setStyle(HOVERED_BUTTON_STYLE));
        shootBtn.setOnMouseExited(e -> shootBtn.setStyle(IDLE_BUTTON_STYLE));
        shootBtn.setOnAction(e -> {
            disableBtn();
            shootChange = true;
        });

        Text Jump = new Text("Jump");
        Text Shoot = new Text("Shoot");
        Jump.setY(height/2);
        Shoot.setY(height/2+40);

        VBox box = new VBox(20);
        box.getChildren().addAll(jumpBtn, shootBtn);
        box.setTranslateY(height/2-35);
        box.setTranslateX((width-150)/2);

        p.getChildren().addAll(returnBtn,box, Jump, Shoot);
    }

    public void setScene(MenuScene menu, GameScene game){
        this.menu = menu;
        this.game = game;
    }

    public void disableBtn(){
        shootBtn.setDisable(true);
        jumpBtn.setDisable(true);
        returnBtn.setDisable(true);
    }
    public void enableBtn(){
        shootBtn.setDisable(false);
        jumpBtn.setDisable(false);
        returnBtn.setDisable(false);
    }


    public void setJumpKey(){
        setOnKeyPressed( (event)->{
            if(!shootChange && !jumpChange){
                event.consume();
                return;
            } else {
                switch (event.getCode()) {
                    case UNDEFINED:
                        break;
                    case ESCAPE:
                        break;
                    default:
                        if (jumpChange) {
                            String keyName = event.getCode().getName();
                            jumpBtn.setText(keyName);
                            game.changeShootKey(event.getCode());
                            jumpChange = false;
                            enableBtn();
                        } else if (shootChange) {
                            String keyName = event.getCode().getName();
                            shootBtn.setText(keyName);
                            game.changeJumpKey(event.getCode());
                            shootChange = false;
                            enableBtn();
                        }
                }
            }

        });
    }


}
