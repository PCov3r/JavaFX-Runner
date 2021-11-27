package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * AnimatedThing is the class that holds the methods used by moving elements : our hero, the enemies, the projectiles.
 * It implements all the movement equations, as well as animation and hitbox.
 * It also gives the possibility to show the hitbox, for debugging purposes.
 */
public class AnimatedThing {
    private double x, y, vx, vy;
    private ImageView img;
    private Integer attitude, frameidx, period, maxidx, offsetx, offsety, height, width;
    private Rectangle hitBoxImg;
    private double GRAVITY = 0.8;

    /**
     * The constructor of an AnimatedThing has many parameters to manage the animation.
     * We give it the frame index, the maximum index, the offset between two frames (along x axis) and offset between two attitudes (along y axis).
     * The first arguments are used for the movement of our hero.
     * @param x the x coordinate origin of our hero
     * @param y the y coordinate origin of our hero
     * @param vx the initial speed of our hero
     * @param attitude the attitude of our hero
     * @param frameidx the current frame index
     * @param period the time period between two frame, in ns
     * @param maxidx the maximum index of an animation
     * @param offsetx the offset between two frames along the x axis
     * @param offsety the offset between two frames along the y axis
     * @param viewx the x coordinate of the first frame
     * @param viewy the y coordinate of the first frame
     * @param height the height of a frame
     * @param width the width of a frame
     * @param thingPath the spritesheet's path
     */
    public AnimatedThing(double x, double y,double vx, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offsetx, Integer offsety, Integer viewx, Integer viewy, Integer height, Integer width, String thingPath) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.attitude = attitude;
        this.frameidx = frameidx;
        this.period = period;
        this.maxidx = maxidx;
        this.offsetx = offsetx;
        this.offsety = offsety;
        this.height = height;
        this.width = width;

        Image spriteSheet = new Image(thingPath);
        this.img = new ImageView(spriteSheet);
        this.img.setViewport(new Rectangle2D(viewx,viewy,width,height));
        this.img.setX(this.x);
        this.img.setY(this.y);
    }

    /**
     * Method to make the hitbox visible on the screen for debugging purposes
     * @param p the pane in which the hitbox will be added
     * @param x the x coordinate of the hitbox's origin
     * @param y the y coordinate of the hitbox's origin
     * @param width the width of the hitbox
     * @param height the height of the hitbox
     */
    public void addHitBox(Pane p, double x, double y, double width, double height){
        hitBoxImg = new Rectangle(x,y,width,height);
        hitBoxImg.setStrokeWidth(2.0);
        hitBoxImg.setStroke(Color.RED);
        hitBoxImg.setFill(Color.TRANSPARENT);
        p.getChildren().add(hitBoxImg);
    }

    /**
     * A method to get the hitbox to be used with the intersect() method. Allow the user to update the hitbox image position as well.
     * @param x the x coordinate of the hitbox
     * @param y the y coordinate of the hitbox
     * @param width the width of the hitbox
     * @param height the length of the hitbox
     * @return the hitbox in the form of a Rectangle2D object
     */
    public Rectangle2D getHitBox(double x, double y, double width, double height){
        if(this.hitBoxImg != null) {
            hitBoxImg.setX(x);
            hitBoxImg.setY(y);
        }
        return(new Rectangle2D(x,y,width,height));
    }

    /**
     * Allow the user to delete the hitbox image.
     * @param p the pane from which the hitbox should be removed.
     */
    public void deleteHitBox(Pane p){
        p.getChildren().remove(hitBoxImg);
    }

    /**
     *
     * @return the imageView of our AnimatedThing
     */
    public ImageView getImgview() {
        return img;
    }

    /**
     * Move the AnimatedThing along the x axis.
     * @param coor the new x coordinate of our object
     */
    public void setXcoor(double coor) {
        this.x = coor;
    }

    /**
     * Move the AnimatedThing along the y axis.
     * @param coor the new y coordinate of our object
     */
    public void setYcoor(double coor) {
        this.y = coor;
    }

    /**
     *
     * @return the x coordinate of our object
     */
    public double getXcoor() {
        return x;
    }

    /**
     *
     * @return the y coordinate of our object
     */
    public double getYcoor() {
        return y;
    }

    /**
     * Method to change our object attitude, ie set the offset multiplicator along the y axis on our spritesheet.
     * @param attitude the number associated with our object attitude along the y axis. Start from 0
     */
    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    /**
     *
     * @return the object's attitude
     */
    public Integer getAttitude(){
        return this.attitude;
    }

    /**
     * Simple method that allow us to update the movement equations to make our object jump.
     * Change the attitude as well to make our object animation evolve with the movement.
     */
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


    /**
     * Method which implements gravity in our game and the movement along the x-axis of our object.
     * @param step the step along the x-axis
     */
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
            if(this.vy > 0){
                this.frameidx = 1;
            }
        }
    }

    /**
     * Method that update our object by making it move and updating its animation.
     * @param now the current timer timestamp, used to switch between frame
     * @param step the step of the movement along the x-axis
     */
    public void update(long now, int step){
        if(this.attitude == 0 || this.attitude == 2){
            frameidx = Math.toIntExact((now/period)%maxidx);
        }
        this.img.setViewport(new Rectangle2D(this.offsetx*this.frameidx,this.offsety*this.attitude,width,height));
        updateMov(step);
    }

}
