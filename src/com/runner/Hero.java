package com.runner;

import javafx.scene.image.ImageView;

public class Hero extends AnimatedThing {

    public Hero(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer length, Integer width) {
        super(x, y, attitude, frameidx, period, maxidx, offset, length, width, "D:\\Documents\\Java projects\\Runner\\src\\heros.png");
    }
}
