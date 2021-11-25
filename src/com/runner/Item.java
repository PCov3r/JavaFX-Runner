package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Item{
    private double x, y;
    private ImageView imgview; //Image à afficher

    public Item(double beginx, double beginy, double width, double length, Camera cam, String backgroundpath) {
        Image spriteSheet = new Image(backgroundpath); //Chargement d'une nouvelle image
        this.imgview = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.imgview.setViewport(new Rectangle2D(0,0,width,length)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.imgview.setX(beginx); //Coordonnées de l'endroit où l'image doit être affichée
        this.imgview.setY(beginy);
        this.x = beginx;
        this.y = beginy;
    }

    public Rectangle2D getHitBox(double x, double y, double width, double height){
        return new Rectangle2D(x,y,width,height);
    }

    public double getXcoor() {
        return x;
    }

    public double getYcoor() {
        return y;
    }

    public ImageView getImgview() {
        return imgview;
    }

    public void add(Pane p){
        p.getChildren().add(imgview);
    }

    public void remove(Pane p){
        p.getChildren().remove(imgview);
        imgview.setImage(null);
    }

}
