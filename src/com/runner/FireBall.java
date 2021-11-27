package com.runner;

/**
 * An extension of the AnimatedThing class.
 * Fireball if the class associated to the projectiles list in GameScene. It simply loads the fireball imageView of the hero spritesheet into the game.
 */
public class FireBall extends AnimatedThing{
    private double x;
    private double y;

    /**
     * Method to add a fireball in the associated scene.
     * @param x the x coordinate of the fireball
     * @param y the y coordinate of the fireball
     */
    public FireBall(double x, double y) {
        super(x, y, 0,0, 0, 100, 0, 0, 0, 510, 265, 100, 200, ".\\img\\heros.png");
        this.x = x;
        this.y = y;
    }


}
