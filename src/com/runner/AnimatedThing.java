package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class AnimatedThing {
    private double x, y, vx, vy, ax, ay;
    private ImageView img;
    private Integer attitude;
    private Integer frameidx, period, maxidx, offset, height, width;
    private Rectangle hitBoxImg;
    private double GRAVITY = 0.8;

    public AnimatedThing(double x, double y,double vx, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer viewx, Integer viewy, Integer height, Integer width, String thingPath) {
        this.x = x;
        this.y = y;
        this.vx = vx;
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

    public void addHitBox(boolean show, Pane p, double x, double y, double width, double height){
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
            hitBoxImg.setY(y);
        }
        return(new Rectangle2D(x,y,width,height));
    }

    public void deleteHitBox(Pane p){
        p.getChildren().remove(hitBoxImg);
    }

    public ImageView getImgview() {
        return img;
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

    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    public Integer getAttitude(){
        return this.attitude;
    }

    public void jump(){
        if(this.attitude == 0) {
            this.attitude = 1;
            this.frameidx = 0;
            this.vy = -16;
        }
        if(this.attitude == 2){
            this.attitude = 3;
            this.frameidx = 0;
            this.vy = -16;
        }
    }


    public void updateMov(Integer step) {
        this.x += step;
        if (this.attitude == 1 || this.attitude == 3) {
            this.vy += GRAVITY;
            this.y += this.vy;
            if (this.y > 250) {
                this.y = 250;
                this.vy = 0;
                this.attitude = 0;
            }
        }
    }

    public void update(long now, int step){
        if(this.attitude == 0 || this.attitude == 2){
            frameidx = Math.toIntExact((now/period)%maxidx);
        }
        this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));
        updateMov(step);
    }

}
