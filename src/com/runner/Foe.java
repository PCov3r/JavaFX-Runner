package com.runner;

/**
 * A class that extends AnimatedThing.
 * It uses the AnimatedThing methods to create enemies. The latter will run towards the hero.
 */
public class Foe extends AnimatedThing{
    private boolean isAlive ;

    /**
     * The constructor creates an AnimatedThing but it already has the parameters required to create an enemy.
     * @param x the x coordinate origin of our hero
     * @param y the y coordinate origin of our hero
     * @param attitude the attitude of our hero
     * @param frameidx the current frame index
     * @param period the time period between two frame, in ns
     * @param maxidx the maximum index of an animation
     * @param offsetx the offset between two frames along the x axis
     * @param offsety the offset between two frames along the y axis
     * @param height the height of a frame
     * @param width the width of a frame
     */
    public Foe(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offsetx, Integer offsety, Integer height, Integer width) {
        super(x, y, 0,attitude, frameidx, period, maxidx, offsetx, offsety, 0,0,height, width, ".\\img\\enemy.png");
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
