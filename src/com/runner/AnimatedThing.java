package com.runner;

import com.sun.nio.file.SensitivityWatchEventModifier;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AnimatedThing {
    private double x, y, vitesse;
    private ImageView img;
    private Integer attitude;
    private Integer frameidx, period, maxidx, offset, height, width;

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

    public Rectangle2D boundingBox(double x, double y, double width, double height){
        return new Rectangle2D(x,y,width,height);
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
        if(this.attitude == 0 || this.attitude == 2) {
            this.attitude = 1;
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
    }

    public void updateAnim(){
        if(this.attitude == 0){
            if(frameidx>maxidx) {
                frameidx = 0;
            }
            this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));
            frameidx++;
        }
        if(this.attitude == 1){
            if(this.y>120 && frameidx == 0){
                this.y = this.y - 35;
            }
            else {
                this.frameidx = 1;
                this.y = (9.81/2)*10+this.y;
                this.y = constrain(this.y, 0,250);
                if(this.y>=250){
                    this.attitude = 0;
                    this.frameidx = 0;
                }
            }
            this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));
        }
        if(this.attitude == 2){
            if(frameidx>maxidx) {
                frameidx = 0;
            }
            this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));
            frameidx++;
        }
        if(this.attitude == 3){
            if(this.y>100 && frameidx == 0){
                this.y = this.y - 35;
            }
            else {
                this.frameidx = 1;
                this.y = (9.81/2)*10+this.y;
                this.y = constrain(this.y, 0,250);
                if(this.y>=250){
                    this.attitude = 0;
                    this.frameidx = 0;
                }
            }
            this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*120,width,height));
        }

    };
}
