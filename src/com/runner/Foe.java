package com.runner;

/**
 * A class that extends AnimatedThing.
 * It uses the AnimatedThing methods to create enemies. The latter will run towards the hero.
 */
public class Foe extends AnimatedThing{
    private boolean isAlive ;

    /**
     * The constructor creates an AnimatedThing with the parameters required to create an enemy.
     * @param x the x coordinate of our enemy's origin
     * @param y the y coordinate of our enemy's origin
     */
    public Foe(double x, double y) {
        super(x, y, 0, 0,0, 0, 100_000_000, 6, 85, 120, 0,0,100, 85, ".\\img\\darklink.png");
        getImgview().setScaleX(-1);
        isAlive = true;
    }

    /**
     * To make our enemy disappear when they hit the hero, we added a alive status to it.
     * @return wether the enemy is alive or not
     */
    public boolean isAlive(){
        return isAlive;
    }

    /**
     * To make our enemy die we need to update its alive property first. The latter will then be checked in our GameScene for further actions.
     */
    public void die(){
        isAlive = false;
    }
}
