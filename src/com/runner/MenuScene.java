
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.File;
import javafx.util.Duration;

/**
 * MenuScene extends the Scene class by adding all the elements required to get a (almost) beautiful menu : buttons to switch between scenes and our running hero.
 */
public class MenuScene extends Scene {
    private GameScene game;
    private OptionScene options;
    private Pane p;
    private MusicPlayer player;

    private AnimatedThing myhero;
    private VBox box;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";

    /**
     * As the other Scene extensions, the MenuScene does not need much parameters. The main body of the constructor consists of elements style.
     * @param primaryStage the primaryStage used to make a link with the other Scenes (GameScene and OptionScene) when the player wants to play or change the key settings
     * @param p the pane element associated with the scene
     * @param width the scene's width in pixels
     * @param height the scene's height in pixels
     */
    public MenuScene(Stage primaryStage, Pane p, double width, double height) {
        super(p, width, height);
        this.p = p;

        player = new MusicPlayer("src\\music\\zelda_theme.mp3",2,171);
        player.startMusic();

        Image back = new Image(".\\img\\menuback.jpg",width, height,false,true);
        ImageView background = new ImageView(back);

        myhero = new AnimatedThing(300,100,0, 0, 0, 150_000_000, 23, 50, 0, 0, 0, 100, 50, ".\\img\\ocarina.png");
        myhero.getImgview().setPreserveRatio(true);
        myhero.getImgview().setFitHeight(350);

        Text title = new Text(50,90,"JavaFX Runner");
        title.setFill(Color.SNOW);
        title.setStroke(Color.BLACK);
        title.setFont(Font.font ("Impact", 60));

        Rectangle backBox1 = new Rectangle(150, 50);
        backBox1.setFill(Color.WHITESMOKE);
        backBox1.setOpacity(0.5);
        backBox1.setX(60);
        backBox1.setY(160);

        Button playBtn = new Button("Jouer");
        playBtn.setMinWidth(150);
        playBtn.setStyle(IDLE_BUTTON_STYLE);
        playBtn.setOnMouseEntered(e -> playBtn.setStyle(HOVERED_BUTTON_STYLE));
        playBtn.setOnMouseExited(e -> playBtn.setStyle(IDLE_BUTTON_STYLE));
        playBtn.setOnAction(e -> {
            primaryStage.setScene(game);
            game.Start();
            timerAnim.stop();
            player.stopMusic();
        });

        Rectangle backBox2 = new Rectangle(150, 50);
        backBox2.setFill(Color.WHITESMOKE);
        backBox2.setOpacity(0.5);
        backBox2.setX(60);
        backBox2.setY(225);

        Button optionsBtn = new Button("Options");
        optionsBtn.setMinWidth(150);
        optionsBtn.setStyle(IDLE_BUTTON_STYLE);
        optionsBtn.setOnMouseEntered(e -> optionsBtn.setStyle(HOVERED_BUTTON_STYLE));
        optionsBtn.setOnMouseExited(e -> optionsBtn.setStyle(IDLE_BUTTON_STYLE));
        optionsBtn.setOnAction(e -> {
            primaryStage.setScene(options);
        });

        Rectangle backBox3 = new Rectangle(150, 50);
        backBox3.setFill(Color.WHITESMOKE);
        backBox3.setOpacity(0.5);
        backBox3.setX(60);
        backBox3.setY(290);

        Button quitBtn = new Button("Quitter");
        quitBtn.setMinWidth(150);
        quitBtn.setStyle(IDLE_BUTTON_STYLE);
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

        box = new VBox(20);
        box.setTranslateX(70);
        box.setTranslateY(170);
        box.getChildren().addAll(playBtn,optionsBtn,quitBtn);

        p.getChildren().addAll( background,myhero.getImgview(),title, backBox1, backBox2, backBox3,box);
        timerAnim.start();
    }

    /**
     * Method used to make the link between the MenuScene and the other Scenes.
     * @param game the GameScene used to show the game
     * @param options the OptionScene used to change key settings
     */
    public void setScene(GameScene game, OptionScene options){
        this.game = game;
        this.options = options;
    }

    /**
     * To make our menu more attractive, we added our hero. This method render the hero animation in our menu.
     */
    public void render() {
        myhero.getImgview().setX(myhero.getXcoor());
        myhero.getImgview().setY(myhero.getYcoor());
    }


    /**
     * The timer is used to animate our running hero and to render it in our menu.
     */
    private AnimationTimer timerAnim = new AnimationTimer() {

        @Override
        public void handle(long now) {
                myhero.update(now, 0);
                render();
            }
    };
}

