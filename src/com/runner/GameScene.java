package com.runner;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

//GameScene étend la classe Scene en lui rajoutant l'arrière-plan et les éléments mobiles tels que le héros ou les ennemis
public class GameScene extends Scene {
    private Camera cam;
    private staticThing backgroundRight;
    private staticThing backgroundLeft;
    private Hero myhero;
    Integer i = 0;

    public GameScene(Parent parent, double v, double v1, boolean b, Integer camx, Integer camy) {
        super(parent, v, v1, b);
        try {
            this.cam = new Camera(camx, camy);
            backgroundRight = new staticThing(0, 0, 800, 400, -400 - (cam.getXcoor())%800, 0 + (cam.getYcoor())%400,"D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            backgroundLeft = new staticThing(0, 0, 800, 400, 400 - (cam.getXcoor())%800, 0 + (cam.getYcoor())%400, "D:\\Documents\\Java projects\\Runner\\src\\desert.png");
            myhero = new Hero(300, 250, 0, 0,100,5,84,100,90);
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

    public void listenKeys(){
        setOnKeyPressed( (event)->{
            switch(event.getCode()) {
                case SPACE -> {
                    System.out.println("Jump");
                    getMyhero().jump();
                }
            }
        });
    }

    public void render(){
        backgroundRight.getImgview().setX(+800 - (cam.getXcoor())%800);
        backgroundRight.getImgview().setY(0 + (cam.getYcoor())%400);
        backgroundLeft.getImgview().setX(-cam.getXcoor()%800);
        backgroundLeft.getImgview().setY(0 + (cam.getYcoor())%400);
        //myhero.setCoor(myhero.getXcoor(),myhero.getImgview().getY());
        }


    AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= 80_000_000) {
                myhero.update();
                cam.update(cam.getXcoor(),myhero.getXcoor());
                myhero.updateLives();
                render();
                lastUpdate = now ;
            }


        }
    };

    public AnimationTimer getTimer() {
        return timer;
    }
}
