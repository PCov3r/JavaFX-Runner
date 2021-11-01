package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public class Item extends staticThing{
    double x, y;
    public Item(double beginx, double beginy, double width, double length, Camera cam, String backgroundpath) {
        super(beginx, beginy, width, length, cam, backgroundpath);
        this.x = beginx;
        this.y = beginy;
    }

    public void addItem(Pane p, Item i){
        p.getChildren().add(i.getImgview());
    }

    public Rectangle2D boundingBox(double x, double y, double width, double height){
        return new Rectangle2D(x,y,width,height);
    }

    public double getXcoor() {
        return x;
    }

    public double getYcoor() {
        return y;
    }
}
