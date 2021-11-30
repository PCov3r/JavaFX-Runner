package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * A class that extends AnimatedThing.
 * It uses the AnimatedThing methods to create a hero. The latter will move along the y and x axis.
 * The hero also have a life count and a certain amount of ammo.
 */
public class Hero extends AnimatedThing {
    private boolean isInvincible = false;
    private double numberOfLives;
    private ImageView hearts;
    private Integer numberofAmmo = 0;
    private Lighting lighting = new Lighting();
    private boolean hasEffect = false;
    private long prevTime = 0;

    /**
     * The constructor creates an AnimatedThing with the parameters required to create a hero.
     * @param x the x coordinate of the hero's origin
     * @param y the y coordinate of the hero's origin
     */
    public Hero(double x, double y) {
        super(x, y, 0, 0,0, 0,100_000_000,6,85,120,0,0,100,85, ".\\img\\heros.png");
        numberOfLives = 3;

        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(45, 45, Color.RED));

        Image spriteSheet = new Image(".\\img\\hearts.png",114, 30,true,false);
        this.hearts = new ImageView(spriteSheet);
        this.hearts.setViewport(new Rectangle2D(0,0,114,30));
        this.hearts.setX(10);
        this.hearts.setY(10);
    }

    /**
     * Method to reset our hero in order to reload the game.
     * It takes the hero back to the beginning of the run, give it all his lives back and take back all his bonuses.
     */
    public void reset(){
        setYspeed(0);
        setXcoor(400);
        setYcoor(250);
        numberOfLives = 3;
        this.hearts.setVisible(true);
        numberofAmmo = 0;

    }

    /**
     * To prevent our hero to take damage after damage, we add a IsInvincible boolean.
     * When our hero is hit, we set this boolean to true and we prevent the hero from taking damage as long as it stays true.
     * @return wether the hero is invincible or not
     */
    public boolean getIsInvincible(){
        return isInvincible;
    }

    /**
     *
     * @param state the hero's new state of invincibility
     */
    public void setIsInvincible(boolean state){
        isInvincible = state;
    }

    /**
     * Method used to get the lives' ImageView of the hero in order to add it to the scene.
     * @return the hero's lives imageView
     */
    public ImageView getImgHearts() {
        return hearts;
    }

    /**
     * Change the hero's count of life.
     * If the value is negative (ie the hero has been hit), we update the hero visual effect to make it appear red.
     * @param val the value to be added to the hero's count of life
     */
    public void setNumberOfLives(Integer val){
        if(val < 0){
            hasEffect = true;
            getImgview().setEffect(lighting);
        }
        numberOfLives = numberOfLives + val;
        updateLives();
    }

    /**
     *
     * @return the hero's life count
     */
    public double getNumberOfLives(){
        return numberOfLives;
    }

    /**
     * Update the hearts' visual accordingly to the life count of the hero.
     */
    public void updateLives(){
        if(numberOfLives>0) {
            this.hearts.setViewport(new Rectangle2D(0, 0, 38 * numberOfLives, 46));
        } else {
            this.hearts.setVisible(false);
        }
    }

    /**
     * Prevent the hero from shooting indefinitely by giving it a number of ammo.
     * @return the hero's ammo number
     */
    public Integer getAmmo(){
        return numberofAmmo;
    }

    /**
     * Add ammo to the hero (for example when he gets a bonus).
     * @param num the number of ammunition to add
     */
    public void addAmmo(Integer num){
        numberofAmmo += num;
    }

    /**
     * Method to update all properties of the hero.
     * We update its movement and animation using the super method. If the hero has a "hit" effect we check if a certain amount of time has passed before clearing it.
     * Finally, we update the hero life count visual.
     * @param now the current timer timestamp, used to switch between frame
     * @param step the step of the movement along the x-axis
     */
    @Override
    public void update(long now, int step) {
        super.update(now, step);
        if(hasEffect == true) {
            if (now - prevTime > 200_000_000) {

                getImgview().setEffect(null);
                hasEffect = false;
                prevTime = now;
            }
        } else {
            prevTime = now;
        }
        updateLives();
    }
}
