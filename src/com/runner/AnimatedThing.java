package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedThing {
    private double x, y;
    private ImageView img;
    private Integer attitude;
    private Integer frameidx, period, maxidx, offset, length, width;

    public AnimatedThing(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer length, Integer width, String thingPath) {
        this.x = x;
        this.y = y;
        this.attitude = attitude;
        this.frameidx = frameidx;
        this.period = period;
        this.maxidx = maxidx;
        this.offset = offset;
        this.length = length;
        this.width = width;
        Image spriteSheet = new Image(thingPath);
        this.img = new ImageView(spriteSheet);
        this.img.setViewport(new Rectangle2D(0,0,width,length));
        this.img.setX(this.x);
        this.img.setY(this.y);
    }

    public ImageView getImgview() {
        return img;
    }

    public void update(long time){

    };
}
