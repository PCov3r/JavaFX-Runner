package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class staticThing {
    private double x = 0; //Position selon x
    private double y = 0; //Position selon y
    private ImageView imgview; //Image à afficher

    //Le constructeur prend en paramètre beginx, beginy : les coordonnées à partir desquels le viewport commence, width et length, la taille du viewport, placex et placey l'origine de l'image, ie l'endroit où elle sera affichée dans la scène. Enfin, backgroundpath, le chemin d'accès à l'image.
    public staticThing(double beginx, double beginy, double width, double length, double placex, double placey, String backgroundpath) {
        this.x = placex;
        this.y = placey;
        Image spriteSheet = new Image(backgroundpath); //Chargement d'une nouvelle image
        this.imgview = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.imgview.setViewport(new Rectangle2D(beginx,beginy,width,length)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.imgview.setX(this.x); //Coordonnées de l'endroit où l'image doit être affichée
        this.imgview.setY(this.y);
    }

    public ImageView getImgview() {
        return imgview;
    }
}

