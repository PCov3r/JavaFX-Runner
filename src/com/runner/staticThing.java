package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.CancellationException;

public class staticThing {
    private double width = 0; //largeur du background
    private double length = 0; //longueur du background
    private ImageView imgview; //Image à afficher

    //Le constructeur prend en paramètre beginx, beginy : les coordonnées à partir desquels le viewport commence, width et length, la taille du viewport, placex et placey l'origine de l'image, ie l'endroit où elle sera affichée dans la scène. Enfin, backgroundpath, le chemin d'accès à l'image.
    public staticThing(double beginx, double beginy, double width, double length, Camera cam, String backgroundpath) {
        this.width = width;
        this.length = length;
        Image spriteSheet = new Image(backgroundpath); //Chargement d'une nouvelle image
        this.imgview = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.imgview.setViewport(new Rectangle2D(beginx,beginy,width,length)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.imgview.setX(width-cam.getXcoor()%cam.getXcoor()); //Coordonnées de l'endroit où l'image doit être affichée
        this.imgview.setY(-cam.getYcoor()%cam.getYcoor());
    }

    public ImageView getImgview() {
        return imgview;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }
}

