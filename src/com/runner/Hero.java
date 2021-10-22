package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hero extends AnimatedThing {
    private Integer numberOfLives;
    private ImageView hearts;

    public Hero(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width) {
        super(x, y, attitude, frameidx, period, maxidx, offset, height, width, "D:\\Documents\\Java projects\\Runner\\src\\heros.png");
        numberOfLives = 3;
        Image spriteSheet = new Image("D:\\Documents\\Java projects\\Runner\\src\\hearts.png",114, 30,true,false); //Chargement d'une nouvelle image
        this.hearts = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.hearts.setViewport(new Rectangle2D(0,0,114,30)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.hearts.setX(10); //Coordonnées de l'endroit où l'image doit être affichée
        this.hearts.setY(10);
    }

    public ImageView getImgHearts() {
        return hearts;
    }

    public Integer getNumberOfLives(){
        return numberOfLives;
    }

    public void updateLives(){
        this.hearts.setViewport(new Rectangle2D(0,0,19*numberOfLives,46));
    }
}
