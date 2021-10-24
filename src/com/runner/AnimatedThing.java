package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedThing {
    private double x, y;
    private ImageView img;
    private Integer attitude;
    private Integer frameidx, period, maxidx, offset, height, width;

    public AnimatedThing(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width, String thingPath) {
        this.x = x;
        this.y = y;
        this.attitude = attitude;
        this.frameidx = frameidx;
        this.period = period;
        this.maxidx = maxidx;
        this.offset = offset;
        this.height = height;
        this.width = width;
        Image spriteSheet = new Image(thingPath);
        this.img = new ImageView(spriteSheet);
        this.img.setViewport(new Rectangle2D(0,0,width,height));
        this.img.setX(this.x);
        this.img.setY(this.y);
    }

    public ImageView getImgview() {
        return img;
    }

    public void setCoor(double x, double y){
        this.x = x;
        this.y = y;
        this.img.setX(this.x);
        this.img.setY(this.y);
    }

    public double getXcoor() {
        return x;
    }

    public double getYcoor() {
        return y;
    }

    public static double constrain(double val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public void jump(){
        if(this.attitude == 0) {
            this.attitude = 1;
        }
    }

    public void update(){
        if(this.attitude == 0){
            if(frameidx>maxidx) {
                frameidx = 0;
            }
            this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*this.offset,width,height));
            frameidx++;
        }
        if(this.attitude == 1){
            this.img.setViewport(new Rectangle2D(0,165,width,height));
            this.y = this.y - 35;
            if(this.y<100){
                this.attitude = 2;
            }
            setCoor(this.x,this.y);
        }
        if(this.attitude == 2 && this.y<250){
           this.img.setViewport(new Rectangle2D(1*this.offset,165,width,height));
           this.y = (9.81/2)*10+this.y;
           this.y = constrain(this.y, 0,250);
            if(this.y>=250){
                this.attitude = 0;
            }
           setCoor(this.x,this.y);
        }

    };
}
