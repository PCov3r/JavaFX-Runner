package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class Hero extends AnimatedThing {
    private double numberOfLives;
    private ImageView hearts;
    private ArrayList<FireBall> projectiles = new ArrayList<FireBall>();;
    private FireBall tempball;
    private boolean isInvincible = false;
    private Integer numberofAmmo = 100;

    public Hero(double x, double y, Integer attitude, Integer frameidx, Integer period, Integer maxidx, Integer offset, Integer height, Integer width) {
        super(x, y, 0,attitude, frameidx, period, maxidx, offset, 0,0,height, width, "D:\\Documents\\Java projects\\Runner\\src\\heros.png");
        numberOfLives = 3;
        Image spriteSheet = new Image("D:\\Documents\\Java projects\\Runner\\src\\hearts.png",114, 30,true,false); //Chargement d'une nouvelle image, ici la vie du héros
        this.hearts = new ImageView(spriteSheet); //Que l'on associe à un objet ImageView pour pouvoir l'afficher dans notre fenêtre
        this.hearts.setViewport(new Rectangle2D(0,0,114,30)); //Définition du viewport, c'est à dire de la zone à afficher issue de notre image
        this.hearts.setX(10); //Coordonnées de l'endroit où l'image doit être affichée
        this.hearts.setY(10);
    }

    public ImageView getImgHearts() {
        return hearts;
    }

    public double getNumberOfLives(){
        return numberOfLives;
    }

    public void shoot(Pane p, Camera cam){
        if(projectiles.size()>0) {
            if (projectiles.get(projectiles.size() - 1).getXcoor() - getXcoor() > 100) {
                addProjectile(new FireBall(getXcoor()+50, getYcoor() , 0, 0, 100, 0, 0, 100, 200), p);
                addAmmo(-1);
            }
        } else {
            addProjectile(new FireBall(getXcoor() +50, getYcoor() , 0, 0, 100, 0, 0, 100, 200), p);
            addAmmo(-1);
        }
        if (getAttitude() == 0) {
            setAttitude(2);
        } else if (getAttitude() == 1) {
            setAttitude(3);
        }

    }

    public void updateProjectiles(Pane p){
        for(int i = 0; i<projectiles.size(); i++){
            tempball = projectiles.get(i);
            if(tempball.getXcoor()>getXcoor()+400){
                removeProjectile(tempball);
                p.getChildren().remove(tempball.getImgview());
                if(projectiles.size() == 0){
                    if(getAttitude() == 3) { //Si le héros tirait en sautant, on revient au saut normal
                        setAttitude(1);
                    }else if(getAttitude() == 1){
                        setAttitude(1);
                    } else { //Sinon on revient à l'animation du héros qui court
                        setAttitude(0);
                    }
                }
            }
            tempball.updateMov(30);
        }
    }

    public void addProjectile(FireBall b, Pane p){
        p.getChildren().add(b.getImgview());
        projectiles.add(b);
    }

    public void removeProjectile(FireBall b){
        projectiles.remove(b);
    }

    public ArrayList<FireBall> getProjectiles() {
        return projectiles;
    }

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

    public void updateLives(){
        if(numberOfLives>0) {
            this.hearts.setViewport(new Rectangle2D(0, 0, 38 * numberOfLives, 46));
        } else {
            this.hearts.setVisible(false);
        }
    }
}
