package com.runner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private staticThing backgroundRight;
    private staticThing backgroundLeft;
    private Hero myhero;
    private FireBall fb;
    private ArrayList<Foe> ennemies;
    private Foe f;
    private Pane p;
    private Integer numberOfFoes = 0;
    private Random rnd = new Random();
    private long invincibleDuration = 2_500_000_000L;
    private Item bonus;
    private Text score = new Text();
    private Text ammo = new Text();
    private ImageView screenIcon;
    private Stage primaryStage;
    private LosingScene loseScreen;

    public GameScene(Stage ps, LosingScene ls, Pane p, double v, double v1, boolean b, Integer camx, Integer camy) {
        super(p, v, v1, b);
        this.primaryStage = ps;
        this.loseScreen = ls;
        this.p = p;
        myhero = new Hero(400, 250, 0, 0,100,5,85,100,85);
        this.cam = new Camera(camx, camy, myhero);
        backgroundRight = new staticThing(0, 0, 800, 400, cam,"D:\\Documents\\Java projects\\Runner\\src\\desert.png");
        backgroundLeft = new staticThing(0, 0, 800, 400, cam, "D:\\Documents\\Java projects\\Runner\\src\\desert.png");
        this.ennemies = new ArrayList<Foe>();
        ammo.setX(550);
        ammo.setY(30);
        ammo.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        ammo.setText("0");
        score.setX(250);
        score.setY(30);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        score.setText("Distance: 0m");
        Image spriteSheet = new Image("D:\\Documents\\Java projects\\Runner\\src\\fireball.png",30, 30,true,false); //Chargement d'une nouvelle image
        this.screenIcon = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.screenIcon.setViewport(new Rectangle2D(0,0,30,30)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.screenIcon.setX(510); //Coordonnées de l'endroit où l'image doit être affichée
        this.screenIcon.setY(5);
        p.getChildren().addAll(backgroundRight.getImgview(),backgroundLeft.getImgview(), myhero.getImgview(), myhero.getImgHearts(),ammo, score, screenIcon); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
    }

    public void listenKeys(){
        setOnKeyPressed( (event)->{
            switch(event.getCode()) {
                case SPACE -> {
                    myhero.jump();
                }
                case ENTER -> {
                    if(myhero.getAmmo() > 0) {
                        myhero.shoot(p, cam);
                    }
                }
            }
        });
    }



    public void render(){
        Integer distance = Math.round((float) myhero.getXcoor()/50);
        score.setText("Distance: "+distance+"m");
        ammo.setText(myhero.getAmmo().toString());
        backgroundRight.getImgview().setX(backgroundRight.getWidth() - (cam.getXcoor())%backgroundRight.getWidth());
        backgroundRight.getImgview().setY(- (cam.getYcoor())%backgroundRight.getLength());
        backgroundLeft.getImgview().setX(- cam.getXcoor()%backgroundLeft.getWidth());
        backgroundLeft.getImgview().setY(- (cam.getYcoor())%backgroundLeft.getLength());
        myhero.getImgview().setX(myhero.getXcoor()-cam.getXcoor()+100);
        myhero.getImgview().setY(myhero.getYcoor());
        if(bonus != null) {
            bonus.getImgview().setX(bonus.getXcoor()-myhero.getXcoor()+50);
            bonus.getImgview().setY(bonus.getYcoor());
            if(bonus.getImgview().getX()<-100){
                bonus = null;
            }
        }
        for(int i = 0; i<myhero.getProjectiles().size(); i++){
            fb = myhero.getProjectiles().get(i);
            fb.getImgview().setX(fb.getXcoor()-cam.getXcoor()+100);
        }

        for(int i = 0; i<ennemies.size(); i++){
            f = ennemies.get(i);
            f.getImgview().setX(f.getXcoor()-cam.getXcoor());
        }

    }


    public void addFoe(Foe f){
        p.getChildren().add(f.getImgview());
        ennemies.add(f);
    }

    public void removeFoe(Foe f){
        p.getChildren().remove(f.getImgview());
        ennemies.remove(f);
    }

    /*public void createFoe(){
        if(numberOfFoes > 0) {

            if (ennemies.size() > 0) {
                if (ennemies.get(ennemies.size() - 1).getXcoor() - cam.getXcoor() < 200) {
                    addFoe(new Foe(600 + myhero.getXcoor(), 250, 0, 0, 100, 5, 85, 100, 85));
                    numberOfFoes--;
                }
            } else {
                addFoe(new Foe(600 + myhero.getXcoor(), 250, 0, 0, 100, 5, 85, 100, 85));
                numberOfFoes--;
            }
        }
    }*/

    public void createFoe(){
        while(numberOfFoes > 0) {
            if (ennemies.size()==0){
                addFoe(new Foe(600 + myhero.getXcoor(), 250, 0, 0, 100, 5, 85, 100, 85));
                numberOfFoes--;
            } else {
                double lastFoeX = ennemies.get(ennemies.size() - 1).getXcoor();
                Integer randomDistance = rnd.nextInt(500)+200;
                addFoe(new Foe(lastFoeX+randomDistance, 250, 0, 0, 100, 5, 85, 100, 85));
                numberOfFoes--;
            }
        }
    }

    public void updateEnnemies(){
        for(int i = 0; i<ennemies.size(); i++){
            f = ennemies.get(i);
            checkCollisionHeroFoe(myhero, f);
            if(myhero.getProjectiles().size()>0) {
                checkCollisionFireballFoe(f, myhero.getProjectiles().get(0));
            }
            f.updateAnim();
            if(f.getAlive() == false || f.getXcoor()-cam.getXcoor()<-100){
                removeFoe(f);
                System.out.println(ennemies.size());
            }
            f.updateMov(-20);
        }
    }

    public void checkCollisionHeroFoe(Hero h, Foe f) {
        if (h.boundingBox(h.getXcoor() + 110, h.getYcoor(), 50, 70).intersects(f.boundingBox(f.getXcoor(), f.getYcoor(), 50, 70))) {
            f.setAlive(false);
            if (myhero.getIsInvincible() == false) {
                h.setNumberOfLives(-1);
                myhero.setIsInvincible(true);
                invincibleDuration = 25;
            }
        }
    }

    public void checkCollisionFireballFoe(Foe f, FireBall fb) {
        if (fb != null) {
            if (fb.boundingBox(fb.getXcoor() + 110, fb.getYcoor(), 30, 30).intersects(f.boundingBox(f.getXcoor(), f.getYcoor(), 50, 70))) {
                f.setAlive(false);
                myhero.removeProjectile(fb);
                p.getChildren().remove(fb.getImgview());
                if (myhero.getProjectiles().size() == 0) {
                    if (myhero.getAttitude() == 3) { //Si le héros tirait en sautant, on revient au saut normal
                        myhero.setAttitude(1);
                    } else if (myhero.getAttitude() == 1) {
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
           if (h.boundingBox(h.getXcoor()+120,h.getYcoor(),30, 70).intersects(i.boundingBox(i.getXcoor(),i.getYcoor(),20, 20))) {
               h.addAmmo(+5);
               i.remove(p);
               bonus = null;
           }
       }
    }


/*    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;
        private long lastfoeGen = 0;
        @Override
        public void handle(long now) {
            if(myhero.getIsInvincible()==true) {
                invincibleDuration--;
            }
            if(invincibleDuration < 0){
                myhero.setIsInvincible(false);
            }
            if((now - lastfoeGen) >= (rnd.nextInt(4)+6)*1_000_000_000 && ennemies.size() == 0){ //Ajout d'ennemis ttes les 4 à 6 sec
                numberOfFoes = rnd.nextInt(5) + 2;
                lastfoeGen = now;
                createFoe();
            }
            if (now - lastUpdate >= 80_000_000) { //Sera éxécuté toutes les 80_000_000ns
                if(myhero.getXcoor()>5000 && myhero.getXcoor()%5000 > 1 && myhero.getXcoor()%1000<20 && bonus == null){
                    double x = rnd.nextInt(400)+600;
                    bonus = new Item(x + myhero.getXcoor(),300,20,20,cam,"D:\\Documents\\Java projects\\Runner\\src\\shootBonus.png");
                    addItem(p, bonus);
                }
                myhero.updateAnim();
                myhero.updateMov(10);
                cam.update();
                myhero.updateLives();
                if(myhero.getNumberOfLives() == 0){
                    primaryStage.setScene(loseScreen);
                    timer.stop();
                }
                myhero.updateProjectiles(p);
                updateEnnemies();
                render();
                lastUpdate = now ;
            }


        }
    };*/

    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;
        private long lastfoeGen = 0;
        @Override
        public void handle(long now) {
            if(myhero.getIsInvincible()==true) {
                invincibleDuration--;
            }
            if(invincibleDuration < 0){
                myhero.setIsInvincible(false);
            }
            if((now - lastfoeGen) >= (rnd.nextInt(4)+3)*1_000_000_000 && ennemies.size() == 0){ //Ajout d'ennemis ttes les 4 à 7 sec
                numberOfFoes = rnd.nextInt(5) + 2;
                lastfoeGen = now;
                createFoe();
            }
            if (now - lastUpdate >= 80_000_000) { //Sera éxécuté toutes les 80_000_000ns
                if(myhero.getXcoor()>5000 && myhero.getXcoor()%5000 > 1 && myhero.getXcoor()%5000<20 && bonus == null){
                    double x = rnd.nextInt(400)+600;
                    bonus = new Item(x + myhero.getXcoor(),300,20,20,cam,"D:\\Documents\\Java projects\\Runner\\src\\shootBonus.png");
                    bonus.add(p);
                }
                myhero.updateAnim();
                myhero.updateMov(10);
                checkCollisionHeroBonus(myhero,bonus);
                cam.update();
                myhero.updateLives();
                if(myhero.getNumberOfLives() == 0){
                    primaryStage.setScene(loseScreen);
                    timer.stop();
                }
                myhero.updateProjectiles(p);
                updateEnnemies();
                render();
                lastUpdate = now ;
            }


        }
    };

    public AnimationTimer getTimer() {
        return timer;
    }
}
