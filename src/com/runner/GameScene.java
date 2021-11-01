package com.runner;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

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
    private long startInvincible;
    private Item bonus;

    public GameScene(Pane p, double v, double v1, boolean b, Integer camx, Integer camy) {
        super(p, v, v1, b);
        this.p = p;
        try {
            myhero = new Hero(400, 250, 0, 0,100,5,85,100,85);
            this.cam = new Camera(camx, camy, myhero);
            backgroundRight = new staticThing(0, 0, 800, 400, cam,"D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            backgroundLeft = new staticThing(0, 0, 800, 400, cam, "D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            this.ennemies = new ArrayList<Foe>();
            p.getChildren().addAll(getBackground1().getImgview(),getBackground2().getImgview(), myhero.getImgview(), myhero.getImgHearts()); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public staticThing getBackground1() {
        return backgroundRight;
    }

    public staticThing getBackground2() {
        return backgroundLeft;
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
                        myhero.addAmmo(-1);
                    }
                }
            }
        });
    }



    public void render(){
        backgroundRight.getImgview().setX(backgroundRight.getWidth() - (cam.getXcoor())%backgroundRight.getWidth());
        backgroundRight.getImgview().setY(- (cam.getYcoor())%backgroundRight.getLength());
        backgroundLeft.getImgview().setX(- cam.getXcoor()%backgroundLeft.getWidth());
        backgroundLeft.getImgview().setY(- (cam.getYcoor())%backgroundLeft.getLength());
        myhero.getImgview().setX(myhero.getXcoor()-cam.getXcoor()+100);
        myhero.getImgview().setY(myhero.getYcoor());

        for(int i = 0; i<myhero.getProjectiles().size(); i++){
            fb = myhero.getProjectiles().get(i);
            System.out.println(fb.getXcoor());
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
        ennemies.remove(f);
        p.getChildren().remove(f.getImgview());
    }

    public void createFoe(){
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
    }

    public void updateEnnemies(){
        for(int i = 0; i<ennemies.size(); i++){
            f = ennemies.get(i);
            f.updateAnim();
            checkCollision(myhero,f,fb,bonus);
            if(f.getAlive() == false){
                removeFoe(f);
            }
            if(f.getXcoor()<-100){
                removeFoe(f);
            }
            f.updateMov(-20);
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void checkCollision(Hero h, Foe f, FireBall fb, Item i){
       if (h.boundingBox(h.getXcoor()+110,h.getYcoor(),50,70).intersects(f.boundingBox(f.getXcoor(),f.getYcoor(),50,70))) {
           if(myhero.getIsInvincible() == false) {
               h.setNumberOfLives(-1);
               f.setAlive(false);
               System.out.println("collision");
               myhero.setIsInvincible(true);
               startInvincible = System.currentTimeMillis();
           }
       }
       if(fb != null) {
           if (fb.boundingBox(fb.getXcoor()+110,fb.getYcoor(),30, 30).intersects(f.boundingBox(f.getXcoor(),f.getYcoor(),50, 70))) {
               f.setAlive(false);
               myhero.removeProjectile(fb);
               p.getChildren().remove(fb.getImgview());
               if(myhero.getProjectiles().size() == 0){
                   myhero.setAttitude(0);
               }
           }
       }
       if(i != null){
           if (i.boundingBox(h.getXcoor(),h.getYcoor(),30, 30).intersects(f.boundingBox(i.getXcoor(),i.getYcoor(),20, 20))) {
               h.addAmmo(+5);
           }
       }
    }


    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;
        private long lastfoeGen = 0;
        private long lastbonusGen = 0;
        @Override
        public void handle(long now) {
            long elapsedMillis = System.currentTimeMillis() - startInvincible;
            if(elapsedMillis > 2000 && myhero.getIsInvincible()==true){
                myhero.setIsInvincible(false);
            }

            if((now - lastfoeGen)/100 >= 80_000_000){
                lastbonusGen = now;
            }
            if((now - lastfoeGen)/100 >= (rnd.nextInt(4)+6)*10_000_000 && numberOfFoes == 0){
                System.out.println("generating ennemies");
                numberOfFoes = rnd.nextInt(10) + 1;
                lastfoeGen = now;
            }
            if (now - lastUpdate >= 80_000_000) {
                createFoe();
                myhero.updateAnim();
                myhero.updateMov(10);
                cam.update();
                myhero.updateLives();
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
