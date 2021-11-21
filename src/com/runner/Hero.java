package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class Hero extends AnimatedThing {
    private double numberOfLives; //Variable contenant le nombre de vie
    private ImageView hearts; //Image associée au nombre de vie
    private boolean isInvincible = false; //Variable d'invincibilité
    private Integer numberofAmmo = 5; //Nombre de munitions du heros

    public Hero(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width) {
        super(x, y, 0,attitude, frameidx, period, maxidx, offset, 0,0,height, width, ".\\heros.png"); //Appel du super constructeur pour afficher notre heros
        numberOfLives = 3;
        Image spriteSheet = new Image(".\\hearts.png",114, 30,true,false); //Chargement d'une nouvelle image, ici la vie du héros
        this.hearts = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.hearts.setViewport(new Rectangle2D(0,0,114,30)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.hearts.setX(10); //Coordonnées de l'endroit où l'image doit être affichée
        this.hearts.setY(10);
    }

    public void reset(Pane p){ //Reset de toutes les propriétés pour redémarrer le jeu
        setXcoor(0); //Le héros est en 0
        numberOfLives = 3; //Avec 3 vies
        this.hearts.setVisible(true);
        numberofAmmo = 0; //0 munitions

    }

    public ImageView getImgHearts() {
        return hearts;
    } //Retourne l'imageview des coeurs afin de mettre à jour leur nombre en utilisant render() de GameScene

    public double getNumberOfLives(){
        return numberOfLives;
    } //Nombre de vies



    public Integer getAmmo(){
        return numberofAmmo;
    }

    public void addAmmo(Integer num){
        numberofAmmo += num;
    }

    public void setNumberOfLives(Integer val){
        numberOfLives = numberOfLives + val;
    }

    public boolean getIsInvincible(){
        return isInvincible;
    }

    public void setIsInvincible(boolean state){
        isInvincible = state;
    }

    public void updateLives(){ //Update du visuel de la vie du heros
        if(numberOfLives>0) {
            this.hearts.setViewport(new Rectangle2D(0, 0, 38 * numberOfLives, 46));
        } else {
            this.hearts.setVisible(false);
        }
    }
}
