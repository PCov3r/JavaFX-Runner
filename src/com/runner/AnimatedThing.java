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

    public double getXcoor() {
        return x;
    }

    public void update(){
        if(frameidx>maxidx) {
            frameidx = 0;
        }
        this.img.setViewport(new Rectangle2D(this.offset*this.frameidx,this.attitude*this.offset,width,height));
        frameidx++;
    };
}
