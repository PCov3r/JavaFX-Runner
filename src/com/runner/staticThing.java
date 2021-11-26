package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * StaticThing is the class that holds the methods used by static elements : background for example
 */
public class staticThing {
    /**
     * A staticThing is defined by a width, a height and an imageView that we will use to make it evolve
     */
    private double width = 0;
    private double height = 0;
    private ImageView imgview;

    /**
     * Sole constructor
     * @param viewx the x coordinate of the image part to show
     * @param viewy the y coordinate of the image part to show
     * @param width the width of the image part to show
     * @param height the length of the image part to show
     * @param cam a Camera object associated with the hero, to move the static thing accordingly
     * @param backgroundpath the path of the image to show
     */
    public staticThing(double viewx, double viewy, double width, double height, Camera cam, String backgroundpath) {
        this.width = width;
        this.height = height;
        Image spriteSheet = new Image(backgroundpath);
        this.imgview = new ImageView(spriteSheet);
        this.imgview.setViewport(new Rectangle2D(viewx,viewy,width,height));
        this.imgview.setX(width-cam.getXcoor()%cam.getXcoor());
        this.imgview.setY(-cam.getYcoor()%cam.getYcoor());
    }

    /**
     * This method is used to manipulate the imageView element, in order to move it on the screen for example.
     * @return the staticThing imageView to be used in the Scene
     */
    public ImageView getImgview() {
        return imgview;
    }

    /**
     *
     * @return the staticThing width
     */
    public double getWidth() {
        return width;
    }

    /**
     *
     * @return the staticThing height
     */
    public double getHeight() {
        return height;
    }
}

