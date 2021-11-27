package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class implements collectible item for our hero (projectiles bonus).
 * It simply consists in an imageView to which is associated a hitbox.
 */
public class Item{
    private double x, y;
    private ImageView imgview;

    /**
     * The constructor allow to change the bonus design (as long as the hitbox is set accordingly).
     * @param xcoor the x coordinates of the bonus position
     * @param ycoor the y coordinates of the bonus position
     * @param viewx the x coordinate of the image part to show
     * @param viewy the y coordinate of the image part to show
     * @param width the width of the image part to show
     * @param height the length of the image part to show
     * @param backgroundpath the path of the bonus spritesheet
     */
    public Item(double xcoor, double ycoor, double viewx, double viewy, double width, double height, String backgroundpath) {
        Image spriteSheet = new Image(backgroundpath);
        this.imgview = new ImageView(spriteSheet);
        this.imgview.setViewport(new Rectangle2D(viewx,viewy,width,height));
        this.imgview.setX(xcoor);
        this.imgview.setY(ycoor);
        this.x = xcoor;
        this.y = ycoor;
    }

    /**
     * Method used to get the bonus hitbox and thereafter check for collision between the hero and the bonus.
     * @param x the x coordinate of the bonus hitbox
     * @param y the y coordinate of the bonus hitbox
     * @param width the width of the hitbox
     * @param height the height of the hitbox
     * @return a Rectangle2D object that can be used with the intersects() method to check for collision
     */
    public Rectangle2D getHitBox(double x, double y, double width, double height){
        return new Rectangle2D(x,y,width,height);
    }

    /**
     *
     * @return the x coordinates of the bonus
     */
    public double getXcoor() {
        return x;
    }

    /**
     *
     * @return the y coordinates of the bonus
     */
    public double getYcoor() {
        return y;
    }

    /**
     *
     * @return the imageView of the bonus to apply visual changes on the latter
     */
    public ImageView getImgview() {
        return imgview;
    }

}
