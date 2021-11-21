package com.runner;

import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public abstract class AnimatedThing {
    private double x, y, vitesse;
    private ImageView img;
    private Integer attitude;
    private Integer frameidx, period, maxidx, offset, height, width;
    private Rectangle hitBoxImg;
    private double vel = 75;

    public AnimatedThing(double x, double y,double vitesse, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer viewx, Integer viewy, Integer height, Integer width, String thingPath) {
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        this.attitude = attitude;
        this.frameidx = frameidx;
        this.period = period;
        this.maxidx = maxidx;
        this.offset = offset;
        this.height = height;
        this.width = width;
        Image spriteSheet = new Image(thingPath);
        this.img = new ImageView(spriteSheet);
        this.img.setViewport(new Rectangle2D(viewx,viewy,width,height));
        this.img.setX(this.x);
        this.img.setY(this.y);
    }

    public void setHitBox(boolean show, Pane p, double x, double y, double width, double height){
        hitBoxImg = new Rectangle(x,y,width,height);
        hitBoxImg.setStrokeWidth(2.0);
        hitBoxImg.setStroke(Color.RED);
        hitBoxImg.setFill(Color.TRANSPARENT);
        if(show) {
            p.getChildren().add(hitBoxImg);
        }
    }

    public Rectangle2D getHitBox(double x, double y, double width, double height){
        if(this.hitBoxImg != null) {
            hitBoxImg.setX(x);
            hitBoxImg.setY(this.y);
        }
        return(new Rectangle2D(x,y,width,height));
    }

    public void deleteHitBox(Pane p){
        p.getChildren().remove(hitBoxImg);
    }

    public ImageView getImgview() {
        return img;
    }

    public void setVitesse(double v){
        this.vitesse = v;
    }

    public double getXcoor() {
        return x;
    }

    public void setXcoor(double coor) {
        this.x = coor;
    }

    public double getYcoor() {
        return y;
    }

    public double getVitesse() { return vitesse; }

    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    public Integer getAttitude(){
        return this.attitude;
    }

    public static double constrain(double val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public void jump(){
        if(this.attitude == 0) {
            this.attitude = 1;
            this.frameidx = 0;
        }
        if(this.attitude == 2){
            this.attitude = 3;
            this.frameidx = 0;
        }
    }

    public void calculateV(double dt){
        this.vitesse = -10*Math.exp(-(1/2)*0.15)+10;
    }


    public void updateMov(Integer step){
        calculateV(1);
        //setVitesse(getVitesse()+0.01);
        x += step;
        if(this.attitude == 1 || this.attitude == 3) {

            if (this.y > 50 && frameidx == 0) {
                this.vel = this.vel - 10*0.75;
                if(this.vel < 5){
                    this.vel = 5;
                }
                if (this.y - this.vel * 0.75 < 50) {
                    this.frameidx = 1;
                    this.vel = 0;
                } else {
                    this.y = this.y - this.vel * 0.75;
                }
            } else {
                this.vel = this.vel + 9.8 * 0.25;
                if (this.vel > 75) {
                    this.vel = 75;
                }
                if (this.y + this.vel * 0.25 > 250) {
                    this.y = 250;
                    this.vel = 75;
                    this.frameidx = 0;
                    this.attitude = 0;
                } else {
                    this.y = this.y + this.vel * 0.25;
                }
            }
        }
    }

    public void updateAnim(long now){
        if(this.attitude == 0 || this.attitude == 2){
            frameidx = Math.toIntExact((now/period)%maxidx);
        }
        this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));

    }

}
