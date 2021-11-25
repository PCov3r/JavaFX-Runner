package com.runner;

public class Foe extends AnimatedThing{
    private boolean isAlive ;

    public Foe(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width) {
        super(x, y, 0,attitude, frameidx, period, maxidx, offset, 0,0,height, width, ".\\img\\enemy.png");
        getImgview().setScaleX(-1);
        isAlive = true;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void die(){
        isAlive = false;
    }
}
