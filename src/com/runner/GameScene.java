package com.runner;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

//GameScene étend la classe Scene en lui rajoutant l'arrière-plan et les éléments mobiles tels que le héros ou les ennemis
public class GameScene extends Scene {
    private Camera cam;
    private Integer numberOfLives ;
    private staticThing backgroundRight;
    private staticThing backgroundLeft;
    private Hero myhero;

    public GameScene(Parent parent, double v, double v1, boolean b, Integer camx, Integer camy) {
        super(parent, v, v1, b);
        numberOfLives = 3;
        try {
            this.cam = new Camera(camx, camy);
            backgroundRight = new staticThing(0, 0, 800, 400, +800 - (cam.getXcoor())%800, 0 + cam.getYcoor(),"D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            backgroundLeft = new staticThing(0, 0, 800, 400, 0 - (cam.getXcoor())%800, 0 + cam.getYcoor(), "D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            myhero = new Hero(200, 250, 0, 0,0,0,0,100,90);
            timer.start();
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

    public Hero getMyhero() {
        return myhero;
    }

    public Camera getCam(){
        return cam;
    }

    public static void update(long time){

    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long time) {
            myhero.update(time);
            cam.update(time);
            GameScene.update(time);

        }
    };

}
