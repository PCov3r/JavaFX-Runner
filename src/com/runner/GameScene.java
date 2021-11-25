package com.runner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

//GameScene étend la classe Scene en lui rajoutant l'arrière-plan et les éléments mobiles tels que le héros ou les ennemis
public class GameScene extends Scene {
    private Camera cam;
    private boolean paused;
    private int distance = 0;
    private boolean showHitBox = false;
    private Pane p;
    private staticThing backgroundRight;
    private staticThing backgroundLeft;
    private Hero myhero;
    private ArrayList<FireBall> projectiles = new ArrayList<FireBall>(); //Projectiles du heros
    private FireBall fb;
    private ArrayList<Foe> ennemies;
    private Foe f;
    private Integer numberOfFoes = 0;
    private Random rnd = new Random();
    private long invincibleDuration = 50;
    private Item bonus;
    private Rectangle Pause;
    private Text score = new Text();
    private Text ammo = new Text();
    private ImageView FireballIcon;
    private Stage primaryStage;
    private LosingScene loseScreen;
    private VBox box;
    final String IDLE_BUTTON_STYLE = " -fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ";
    final String HOVERED_BUTTON_STYLE = "-fx-font-size:20px; -fx-background-color: #ffffffff; -fx-border-color: #000000; -fx-text-fill: #000000";


    public GameScene(Stage ps, LosingScene ls, Pane p, boolean showHitBox, double v, double v1, boolean b, double camx, double camy, double camOffset) {
        super(p, v, v1, b);
        this.primaryStage = ps;
        this.loseScreen = ls;
        this.p = p;
        this.showHitBox = showHitBox;
        myhero = new Hero(400, 250, 0, 0,100_000_000,6,85,100,85);
        this.cam = new Camera(camx, camy, camOffset, myhero);
        backgroundRight = new staticThing(0, 0, 800, 400, cam,".\\desert.png");
        backgroundLeft = new staticThing(0, 0, 800, 400, cam, ".\\desert.png");
        this.ennemies = new ArrayList<Foe>();
        Pause = new Rectangle(0,0,v,v1);
        Pause.setFill(Color.GRAY);
        Pause.setOpacity(0.75);
        Pause.setVisible(false);
        ammo.setX(550);
        ammo.setY(30);
        ammo.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        ammo.setText("0");
        score.setX(230);
        score.setY(30);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        score.setText("Distance: 0m");
        Image spriteSheet = new Image(".\\fireball.png",30, 30,true,false); //Chargement d'une nouvelle image
        this.FireballIcon = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.FireballIcon.setViewport(new Rectangle2D(0,0,30,30)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.FireballIcon.setX(510); //Coordonnées de l'endroit où l'image doit être affichée
        this.FireballIcon.setY(5);

        Button playBtn = new Button("Reprendre");
        playBtn.setMinWidth(150);
        playBtn.setMinHeight(30);
        playBtn.setStyle("-fx-font-size:20px; -fx-background-color: #525252; -fx-border-color: #000000; -fx-text-fill: #ffffff ");
        playBtn.setOnMouseEntered(e -> playBtn.setStyle(HOVERED_BUTTON_STYLE));
        playBtn.setOnMouseExited(e -> playBtn.setStyle(IDLE_BUTTON_STYLE));
        playBtn.setOnAction(e -> {
            paused = false;
            box.setVisible(false);
            timer.start();
            Pause.setVisible(false);
        });

        Button quitBtn = new Button("Quitter");
        quitBtn.setMinWidth(150);
        playBtn.setMinHeight(30);
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

        box = new VBox(10);
        box.setVisible(false);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(playBtn,quitBtn);

        p.getChildren().addAll(backgroundRight.getImgview(),backgroundLeft.getImgview(), myhero.getImgview(), myhero.getImgHearts(),ammo, score, FireballIcon, Pause, box); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
        box.setTranslateY(v1/2-35);
        box.setTranslateX((v-150)/2);
        myhero.addHitBox(showHitBox, p, myhero.getXcoor()-cam.getXcoor(),myhero.getYcoor(),75,100);
    }

    public void listenKeys(){
        setOnKeyPressed( (event)->{
            switch(event.getCode()) {
                case SPACE -> {
                    if(!paused) myhero.jump();
                }
                case ENTER -> {
                    if(!paused) {
                        if (myhero.getAmmo() > 0) {
                            shoot(p);
                        }
                    }
                }
                case ESCAPE -> {
                    if(paused){
                        paused = false;
                        box.setVisible(false);
                        Pause.setVisible(false);
                        timer.start();
                    } else {
                        paused = true;
                        timer.stop();
                        Pause.toFront();
                        Pause.setVisible(true);
                        box.setVisible(true);
                        box.toFront();

                    }

                }
            }
        });
    }

    public void reset(){
        cam.reset();
        myhero.reset(p);
        destroyAllProjectiles();
        destroyAllFoes();
        timer.start();
    }



    public void addFoe(Foe f){
        p.getChildren().add(f.getImgview());
        ennemies.add(f);
        f.addHitBox(showHitBox, p, f.getXcoor()-cam.getXcoor(),f.getYcoor(),60,100);
    }

    public void removeFoe(Foe f){
        p.getChildren().remove(f.getImgview());
        f.deleteHitBox(p);
        ennemies.remove(f);
    }

    public void destroyAllFoes(){
        for(int i = 0; i<ennemies.size(); i++){ //Et aucun projectile n'est sur scène
            removeFoe(ennemies.get(i));
        }
        ennemies.clear();
    }

    public void createFoe(){
        while(numberOfFoes > 0) {
            if (ennemies.size()==0){
                addFoe(new Foe(600 + myhero.getXcoor(), 250, 0, 0, 100_000_000, 6, 85, 100, 85));
                numberOfFoes--;
            } else {
                double lastFoeX = ennemies.get(ennemies.size() - 1).getXcoor();
                Integer randomDistance = rnd.nextInt(400)+300;
                addFoe(new Foe(lastFoeX+randomDistance, 250, 0, 0, 100_000_000, 6, 85, 100, 85));
                numberOfFoes--;
            }
        }
    }

    public void updateEnnemies(long now){
        for(int i = 0; i<ennemies.size(); i++){
            f = ennemies.get(i);
            checkCollisionHeroFoe(f);
            if(projectiles.size()>0) {
                checkCollisionFireballFoe(f, projectiles.get(0));
            }
            if(f.isAlive() == false || f.getXcoor()-cam.getXcoor()<-100){
                removeFoe(f);
            }
            f.update(now,-5);
        }
    }

    public void checkCollisionHeroFoe(Foe f) {
        if (myhero.getHitBox(myhero.getXcoor()-cam.getXcoor()+cam.getOffset(), myhero.getYcoor(), 75, 70).intersects(f.getHitBox(f.getXcoor()-cam.getXcoor(), f.getYcoor(), 50, 70))) {
            f.die();
            if (myhero.getIsInvincible() == false) {
                myhero.getImgview().setEffect(myhero.addEffect());
                myhero.setNumberOfLives(-1);
                myhero.setIsInvincible(true);
            }
        }
    }


    public void shoot(Pane p){
        if(projectiles.size()>0) { //Si ça n'est pas le 1e projectile...
            if (projectiles.get(projectiles.size() - 1).getXcoor() - myhero.getXcoor() > 100) { //...afin d'éviter le spam de projectile on atteint que le précédent ait atteint un certain x
                addProjectile(new FireBall(myhero.getXcoor()+50, myhero.getYcoor() + 20, 0, 0, 100, 0, 0, 100, 200));
                myhero.addAmmo(-1); //On met à jour le nb de munitions
            }
        } else { //Si c'est le premier projectile qu'on tire, on l'ajoute directement à la scène
            addProjectile(new FireBall(myhero.getXcoor() +50, myhero.getYcoor() + 20, 0, 0, 100, 0, 0, 100, 200));
            myhero.addAmmo(-1);
        }
        if (myhero.getAttitude() == 0) { //On met le heros en attitude : tirer
            myhero.setAttitude(2);
        } else if (myhero.getAttitude() == 1) { //Si il était en train de sauter, on le met en attitude : tirer et sauter
            myhero.setAttitude(3);
        }

    }


    public void updateProjectiles(){
        for(int i = 0; i<projectiles.size(); i++){
            fb = projectiles.get(i);
            if(fb.getXcoor()>myhero.getXcoor()+400){
                removeProjectile(fb);
                if(projectiles.size() == 0){
                    if(myhero.getAttitude() == 3) { //Si le héros tirait en sautant, on revient au saut normal
                        myhero.setAttitude(1);
                    }else if(myhero.getAttitude() == 1){
                        myhero.setAttitude(1);
                    } else { //Sinon on revient à l'animation du héros qui court
                        myhero.setAttitude(0);
                    }
                }
            }
            fb.updateMov(10);
        }
    }

    public void addProjectile(FireBall fb){ //Ajout d'un projectile à notre liste et notre scène
        p.getChildren().add(fb.getImgview());
        projectiles.add(fb);
        fb.addHitBox(showHitBox, p, fb.getXcoor()-cam.getXcoor()+cam.getOffset(),fb.getYcoor()+15,60,30);
    }

    public void removeProjectile(FireBall fb){ //Supression du projectile
        projectiles.remove(fb);
        p.getChildren().remove(fb.getImgview());
        fb.deleteHitBox(p);
    }

    public void destroyAllProjectiles(){
        for(int i = 0; i<projectiles.size(); i++){ //Et aucun projectile n'est sur scène
            removeProjectile(projectiles.get(i));
        }
        projectiles.clear();
    }

    public void checkCollisionFireballFoe(Foe f, FireBall fb) {
        if (fb != null) {
            if (fb.getHitBox(fb.getXcoor()-cam.getXcoor()+cam.getOffset(), fb.getYcoor()+15, 30, 30).intersects(f.getHitBox(f.getXcoor()-cam.getXcoor(), f.getYcoor(), 50, 70))) {
                f.die();
                removeProjectile(fb);
                p.getChildren().remove(fb.getImgview());
                if (projectiles.size() == 0) {
                    if (myhero.getAttitude() == 1 || myhero.getAttitude() == 3) { //Si le héros tirait en sautant, on revient au saut normal
                        myhero.setAttitude(1);
                    } else { //Sinon on revient à l'animation du héros qui court
                        myhero.setAttitude(0);
                    }
                }
            }
        }
    }

    public void checkCollisionHeroBonus(Hero h, Item i) {
       if(i != null){
           if (h.getHitBox(myhero.getXcoor()-cam.getXcoor()+cam.getOffset(),h.getYcoor(),30, 70).intersects(i.getHitBox(i.getXcoor()-cam.getXcoor(),i.getYcoor(),20, 20))) {
               h.addAmmo(+5);
               i.remove(p);
               bonus = null;
           }
       }
    }

    public void render(){
        distance = Math.round((float) myhero.getXcoor()/50);
        score.setText("Distance: "+distance+"m");
        ammo.setText(myhero.getAmmo().toString());
        backgroundRight.getImgview().setX(backgroundRight.getWidth() - (cam.getXcoor())%backgroundRight.getWidth());
        backgroundRight.getImgview().setY(- (cam.getYcoor())%backgroundRight.getLength());
        backgroundLeft.getImgview().setX(- cam.getXcoor()%backgroundLeft.getWidth());
        backgroundLeft.getImgview().setY(- (cam.getYcoor())%backgroundLeft.getLength());
        myhero.getImgview().setX(myhero.getXcoor()-cam.getXcoor()+cam.getOffset());
        myhero.getImgview().setY(myhero.getYcoor());
        if(bonus != null) {
            bonus.getImgview().setX(bonus.getXcoor()-myhero.getXcoor()+50);
            bonus.getImgview().setY(bonus.getYcoor());
            if(bonus.getImgview().getX()<-100){
                bonus = null;
            }
        }
        for(int i = 0; i<projectiles.size(); i++){
            fb = projectiles.get(i);
            fb.getImgview().setX(fb.getXcoor()-cam.getXcoor()+cam.getOffset());
        }

        for(int i = 0; i<ennemies.size(); i++){
            f = ennemies.get(i);
            f.getImgview().setX(f.getXcoor()-cam.getXcoor());
        }

    }

    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;
        private long lastfoeGen = 0;

        @Override
        public void handle(long now) {
            if(myhero.getIsInvincible()==true) {
                invincibleDuration--;
                if(invincibleDuration < 0){
                    myhero.setIsInvincible(false);
                    invincibleDuration = 50;
                }
            }

            if((now - lastfoeGen) >= (rnd.nextInt(4)+3)*1_000_000_000 && ennemies.size() == 0){ //Ajout d'ennemis ttes les 4 à 7 sec
                numberOfFoes = rnd.nextInt(5) + 2;
                lastfoeGen = now;
                createFoe();
            }
            if (now - lastUpdate >= 8_000_000) { //Sera éxécuté toutes les 8_000_000ns
                if(myhero.getXcoor()>5000 && myhero.getXcoor()%5000 > 1 && myhero.getXcoor()%5000<20 && bonus == null){
                    double x = rnd.nextInt(400)+600;
                    bonus = new Item(x + myhero.getXcoor(),300,20,20,cam,"D:\\Documents\\Java projects\\Runner\\src\\shootBonus.png");
                    bonus.add(p);
                }

                myhero.update(now, 3);
                updateEnnemies(now);
                updateProjectiles();
                checkCollisionHeroBonus(myhero,bonus);
                cam.update();

                if(myhero.getNumberOfLives() == 0){
                    loseScreen.showScore(distance);
                    primaryStage.setScene(loseScreen);
                    timer.stop();
                }

                render();
                lastUpdate = now ;
            }


        }
    };

    public void Start() {
        paused = false;
        timer.start();
    }
}
