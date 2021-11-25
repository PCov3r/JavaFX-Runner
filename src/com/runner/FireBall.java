package com.runner;

public class FireBall extends AnimatedThing{
    private double x;
    private double y;

    public FireBall(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width) {
        super(x, y, 0,attitude, frameidx, period, maxidx, offset, 510, 265, height, width, ".\\img\\heros.png");
        this.x = x;
        this.y = y;
    }


}
