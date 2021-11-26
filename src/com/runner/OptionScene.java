package com.runner;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A Scene extension that provides the possibility for the player to change its ingame keyboard settings
 */
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

    /**
     * As the other Scene extensions, the OptionScene does not need much parameters. The main body of the constructor consists of elements style and buttons settings.
     * @param primaryStage the primaryStage used to make a link with the other Scene (MenuScene) when the player wants to go back to the menu
     * @param p the pane element associated with the scene
     * @param width the scene's width in pixels
     * @param height the scene's height in pixels
     */
    public OptionScene(Stage primaryStage, Pane p, double width, double height) {
        super(p, width, height);
        this.p = p;

        Image back = new Image(".\\img\\menuback.jpg",width, height,false,true);
        ImageView background = new ImageView(back);

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

        shootBtn = new Button("ENTER");
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
        Jump.setFont(Font.font ("Impact", 30));
        Shoot.setFont(Font.font ("Impact", 30));
        Jump.setFill(Color.WHITESMOKE);
        Shoot.setFill(Color.WHITESMOKE);
        Jump.setStroke(Color.BLACK);
        Shoot.setStroke(Color.BLACK);


        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(Jump,jumpBtn, Shoot,shootBtn);
        box.setTranslateY(height/3);
        box.setTranslateX((width-150)/2);

        p.getChildren().addAll(background, returnBtn,box);
    }

    /**
     * Method used to make the link between the MenuScene and the OptionScene.
     * @param menu the MenuScene in which the player will get back to
     * @param game the GameScene to which the controls will be linked
     */
    public void setScene(MenuScene menu, GameScene game){
        this.menu = menu;
        this.game = game;
    }

    /**
     * Method used to disable all the other buttons when the player has not assigned a key to the selected action.
     * This prevent the player to escape the OptionScene without having changed his controls.
     */
    public void disableBtn(){
        shootBtn.setDisable(true);
        jumpBtn.setDisable(true);
        returnBtn.setDisable(true);
    }

    /**
     * Method used to renable all the buttons after the player's choice.
     */
    public void enableBtn(){
        shootBtn.setDisable(false);
        jumpBtn.setDisable(false);
        returnBtn.setDisable(false);
    }

    /**
     * Method used to update the key settings.
     * The button associated with the action needs to be clicked to listen to the keyboard. When a key is pressed, the settings are updated in the game.
     * The button text is updated to show the current setting to the player.
     */
    public void setKeys(){
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
                            game.changeJumpKey(event.getCode());
                            jumpChange = false;
                            enableBtn();
                        } else if (shootChange) {
                            String keyName = event.getCode().getName();
                            shootBtn.setText(keyName);
                            game.changeShootKey(event.getCode());
                            shootChange = false;
                            enableBtn();
                        }
                }
            }

        });
    }


}
