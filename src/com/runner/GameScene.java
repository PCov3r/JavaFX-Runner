package com.runner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;


/**
 * The GameScene contains the body of the game. The score, amount of ammo are in this class.
 * It has many instances of AnimatedThing : the enemies and the fireballs list, their handler and a hero.
 *
 */
public class GameScene extends Scene {
    private Pane p;
    private Stage primaryStage;
    private LosingScene loseScreen;
    private PauseScreen pauseScreen;
    private int distance = 0;
    private Text score = new Text();
    private Text ammo = new Text();
    private KeyCode jumpKey = KeyCode.SPACE;
    private KeyCode shootKey = KeyCode.ENTER;

    private staticThing backgroundRight;
    private staticThing backgroundLeft;
    private Camera cam;
    private boolean showHitBox = false;
    private Hero myhero;
    private long invincibleDuration = 50;
    private ArrayList<FireBall> projectiles = new ArrayList<FireBall>();
    private FireBall fb;
    private ArrayList<Foe> enemies = new ArrayList<Foe>();
    private Foe f;
    private Integer numberOfFoes = 0;
    private Random rnd = new Random();
    private Item bonus;


    /**
     * The GameScene mainly consists in the AnimatedThings and the layout (score, number of ammo, life count)
     * @param ps the primaryStage associated to the entire game
     * @param p the pane to which the element will be added
     * @param showHitBox a boolean that allows to show hitboxes for debugging purposes
     * @param width the width of the scene
     * @param heigth the height of the scene
     * @param camx the camera initial position along the x axis
     * @param camy the camera initial position along the y axis
     * @param camOffset the offset between the camera and the hero
     */
    public GameScene(Stage ps, Pane p, boolean showHitBox, double width, double heigth, double camx, double camy, double camOffset) {
        super(p, width, heigth);
        this.primaryStage = ps;
        this.p = p;
        this.pauseScreen = new PauseScreen(p, primaryStage, width, heigth, timer);
        this.showHitBox = showHitBox;
        myhero = new Hero(400, 250);
        this.cam = new Camera(camx, camy, camOffset, myhero);

        backgroundRight = new staticThing(0, 0, 800, 400, cam,".\\img\\desert.png");
        backgroundLeft = new staticThing(0, 0, 800, 400, cam, ".\\img\\desert.png");

        ammo.setX(550);
        ammo.setY(30);
        ammo.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        ammo.setText("0");
        score.setX(230);
        score.setY(30);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        score.setText("Distance: 0m");
        Image spriteSheet = new Image(".\\img\\fireball.png",30, 30,true,false);
        ImageView FireballIcon = new ImageView(spriteSheet);
        FireballIcon.setViewport(new Rectangle2D(0,0,30,30));
        FireballIcon.setX(510);
        FireballIcon.setY(5);



        p.getChildren().addAll(backgroundRight.getImgview(),backgroundLeft.getImgview(), myhero.getImgview(), myhero.getImgHearts(),ammo, score, FireballIcon); //On ajoute l'arrière plan statique ie 2 images collées l'une après l'autre
        if(showHitBox) {
            myhero.addHitBox(p, myhero.getXcoor() - cam.getXcoor(), myhero.getYcoor(), 75, 100);
        }
    }

    /**
     * Method to link the GameScene to the ending scene
     * @param lose the LosingScene to show when the player dies
     */
    public void setScene(LosingScene lose){
        this.loseScreen = lose;
    }

    /**
     * Method to reset the entire game when the player wants to play again.
     * It resets all the GameScene assets and remove all the previously created enemies and fireballs.
     */
    public void reset(){
        cam.reset();
        myhero.reset();
        destroyAllProjectiles();
        destroyAllFoes();
        if(bonus != null) {
            removeBonus();
        }
        timer.start();
    }

    /**
     * These two methods allow the player to change the game key settings.
     * @param key the new key associated with the action
     */
    public void changeShootKey(KeyCode key){
        shootKey = key;
    }
    public void changeJumpKey(KeyCode key){
        jumpKey = key;
    }

    /**
     * Method that trigger an event (shooting, jumping, pausing the game) whenever the right key is pressed.
     */
    public void listenKeys(){
        setOnKeyPressed( (event)->{
            if(!pauseScreen.isPaused()) {
                if(event.getCode() == jumpKey) {
                    myhero.jump();
                }
                else if(event.getCode() == shootKey) {
                    if (myhero.getAmmo() > 0) {
                        shoot();
                    }
                }
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                if (pauseScreen.isPaused()) {
                    pauseScreen.hideScreen();
                } else {
                    pauseScreen.showScreen();

                }

            }
        });
    }

    /**
     * Method to add a previously created single enemy to the game.
     * @param f the previously created enemy
     */
    public void addFoe(Foe f){
        p.getChildren().add(f.getImgview());
        enemies.add(f);
        if(showHitBox) {
            f.addHitBox(p, f.getXcoor() - cam.getXcoor(), f.getYcoor(), 60, 100);
        }
    }

    /**
     * Method to create a numberOfFoes amount of enemies.
     * This method check if the enemies list already contains an enemy to avoid creating enemy too close from one another.
     */
    public void createFoe(){
        while(numberOfFoes > 0) {
            if (enemies.size()==0){
                addFoe(new Foe(800 + myhero.getXcoor(), 250));
                numberOfFoes--;
            } else {
                double lastFoeX = enemies.get(enemies.size() - 1).getXcoor();
                Integer randomDistance = rnd.nextInt(400)+300;
                addFoe(new Foe(lastFoeX+randomDistance, 250));
                numberOfFoes--;
            }
        }
    }

    /**
     * Remove a previously created enemy from the game.
     * @param f a previously added enemy
     */
    public void removeFoe(Foe f){
        p.getChildren().remove(f.getImgview());
        f.deleteHitBox(p);
        enemies.remove(f);
    }

    /**
     * Remove all enemies from the scene. Used when player wants to start another game.
     */
    public void destroyAllFoes(){
        for(int i = 0; i<enemies.size(); i++){
            removeFoe(enemies.get(i));
        }
        enemies.clear();
    }

    /**
     * Check for collision between hero and enemy by checking if the 2 hitboxes intersects.
     * If the hitboxes intersect, enemy dies (isAlive condition turns to false) and the hero becomes invincible.
     * @param f the enemy on which to check for collision
     */
    public void checkCollisionHeroFoe(Foe f) {
        if (myhero.getHitBox(myhero.getXcoor()-cam.getXcoor()+cam.getOffset(), myhero.getYcoor(), 75, 70).intersects(f.getHitBox(f.getXcoor()-cam.getXcoor(), f.getYcoor(), 50, 70))) {
            f.die();
            if (myhero.getIsInvincible() == false) {
                myhero.setNumberOfLives(-1);
                myhero.setIsInvincible(true);
            }
        }
    }

    /**
     * Update the enemy list by checking for collisions with the hero or the fireballs in the game.
     * Removing the dead ones or those out of the screen and updating the animation & movement.
     * @param now the current timer timestamp
     */
    public void updateEnnemies(long now){
        for(int i = 0; i<enemies.size(); i++){
            f = enemies.get(i);
            checkCollisionHeroFoe(f);
            for(int j = 0; j < projectiles.size(); j++) {
                checkCollisionFireballFoe(f, projectiles.get(j));
            }
            if(f.isAlive() == false || f.getXcoor()-cam.getXcoor()<-100){
                removeFoe(f);
            }
            f.update(now,-5);
        }
    }

    /**
     * Method to add a previously created single fireball to the game.
     * @param fb the previously created fireball
     */
    public void addProjectile(FireBall fb){
        p.getChildren().add(fb.getImgview());
        projectiles.add(fb);
        if(showHitBox) {
            fb.addHitBox(p, fb.getXcoor() - cam.getXcoor() + cam.getOffset(), fb.getYcoor() + 15, 60, 30);
        }
    }


    /**
     * Add the hero the ability to shoot projectiles.
     * Check for distance between the hero and the fireball to avoid shooting all the fireball at once.
     * Update the hero animation to the shooting one.
     */
    public void shoot(){
        if(projectiles.size()>0) {
            if (projectiles.get(projectiles.size() - 1).getXcoor() - myhero.getXcoor() > 100) {
                addProjectile(new FireBall(myhero.getXcoor()+50, myhero.getYcoor() + 20 ));
                myhero.addAmmo(-1);
            }
        } else {
            addProjectile(new FireBall(myhero.getXcoor() +50, myhero.getYcoor() + 20));
            myhero.addAmmo(-1);
        }
        if (myhero.getAttitude() == 0) {
            myhero.setAttitude(2);
        } else if (myhero.getAttitude() == 1) {
            myhero.setAttitude(3);
        }
    }

    /**
     * Remove a previously created fireball from the game.
     * @param fb the previously added fireball
     */
    public void removeProjectile(FireBall fb){
        projectiles.remove(fb);
        p.getChildren().remove(fb.getImgview());
        fb.deleteHitBox(p);
    }

    /**
     * Remove all projectiles from the scene. Used when player wants to start another game.
     */
    public void destroyAllProjectiles(){
        for(int i = 0; i<projectiles.size(); i++){
            removeProjectile(projectiles.get(i));
        }
        projectiles.clear();
    }

    /**
     * Check for collision between hero and projectile by checking if the 2 hitboxes intersects.
     * If the hitboxes intersect, projectile is removed and the enemy dies (isAlive condition turns to false).
     * @param f the enemy with which to check for collision
     * @param fb the fireball object
     */
    public void checkCollisionFireballFoe(Foe f, FireBall fb) {
        if (fb != null) {
            if (fb.getHitBox(fb.getXcoor()-cam.getXcoor()+cam.getOffset(), fb.getYcoor()+15, 30, 30).intersects(f.getHitBox(f.getXcoor()-cam.getXcoor(), f.getYcoor(), 50, 70))) {
                f.die();
                removeProjectile(fb);
                p.getChildren().remove(fb.getImgview());
                if (projectiles.size() == 0) {
                    if (myhero.getAttitude() == 1 || myhero.getAttitude() == 3) {
                        myhero.setAttitude(1);
                    } else {
                        myhero.setAttitude(0);
                    }
                }
            }
        }
    }

    /**
     * Update all the projectile list by checking for collisions, removing the ones that hit an enemy and the ones that went out of sight.
     * Update the hero attitude back to normal when there's no more projectile.
     */
    public void updateProjectiles(){
        for(int i = 0; i<projectiles.size(); i++){
            fb = projectiles.get(i);
            if(fb.getXcoor()>myhero.getXcoor()+400){
                removeProjectile(fb);
                if(projectiles.size() == 0){
                    if(myhero.getAttitude() == 3 || myhero.getAttitude() == 1) {
                        myhero.setAttitude(1);
                    } else {
                        myhero.setAttitude(0);
                    }
                }
            }
            fb.updateMov(10);
        }
    }

    /**
     * Add a bonus to the screen. A bonus is an item that gives the player more ammo.
     */
    public void addBonus(){
        double x = rnd.nextInt(1000)+400;
        bonus = new Item(x + myhero.getXcoor(),300,0,0,20,20,".\\img\\shootBonus.png");
        p.getChildren().add(bonus.getImgview());
    }

    /**
     * Remove bonus from the screen. Bonus is set to null to avoid more collision with the hero after getting the bonus.
     * Indeed, remove the imageView does not prevent more collision. So in order to give the hero only one bonus (and not 8), bonus is set to null.
     */
    public void removeBonus(){
        p.getChildren().remove(bonus.getImgview());
        bonus = null;
    }

    /**
     * Check for collision between hero and bonus by checking if the 2 hitboxes intersects.
     * If the hitboxes intersect, bonus is removed and the player gets more ammo.
     */
    public void checkCollisionHeroBonus() {
           if (myhero.getHitBox(myhero.getXcoor()-cam.getXcoor()+cam.getOffset(),myhero.getYcoor(),30, 70).intersects(bonus.getHitBox(bonus.getXcoor()-cam.getXcoor(),bonus.getYcoor(),20, 20))) {
               myhero.addAmmo(+5);
               bonus.setVisible(false);
           }
    }

    /**
     * Update the bonus by checking for collision with the hero and removing it when out of sight or consumed.
     */
    public void updateBonus(){
        if(bonus != null) {
            checkCollisionHeroBonus();
            if (bonus.getVisible() == false || bonus.getImgview().getX() < -100) {
                removeBonus();
            }
        }
    }

    /**
     * The graphical method that render all the previously updated objects on the screen.
     * Renders the AnimatedThings element one after one. Set the texts indicating the score and number of ammo.
     */
    public void render(){
        distance = Math.round((float) myhero.getXcoor()/50);
        score.setText("Distance: "+distance+"m");

        ammo.setText(myhero.getAmmo().toString());

        backgroundRight.getImgview().setX(backgroundRight.getWidth() - (cam.getXcoor())%backgroundRight.getWidth());
        backgroundRight.getImgview().setY(- (cam.getYcoor())%backgroundRight.getHeight());
        backgroundLeft.getImgview().setX(- cam.getXcoor()%backgroundLeft.getWidth());
        backgroundLeft.getImgview().setY(- (cam.getYcoor())%backgroundLeft.getHeight());
        myhero.getImgview().setX(myhero.getXcoor()-cam.getXcoor()+cam.getOffset());
        myhero.getImgview().setY(myhero.getYcoor());

        if(bonus != null) {
            bonus.getImgview().setX(bonus.getXcoor()-cam.getXcoor()+50);
        }
        for(int i = 0; i<projectiles.size(); i++){
            fb = projectiles.get(i);
            fb.getImgview().setX(fb.getXcoor()-cam.getXcoor()+cam.getOffset());
        }

        for(int i = 0; i<enemies.size(); i++){
            f = enemies.get(i);
            f.getImgview().setX(f.getXcoor()-cam.getXcoor());
        }

    }

    /**
     * AnimationTimer to handle the update of all the different game elements.
     */
    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0 ;

        /**
         * The handle method is responsible for updating all the game elements.
         * It creates enemies when the list is empty, creates bonus every 300m and updates all the moving elements.
         * When all the elements got updated, it renders them by calling the render method.
         * @param now the timer timestamp
         */
        @Override
        public void handle(long now) {
            if(myhero.getIsInvincible()==true) {
                invincibleDuration--;
                if(invincibleDuration < 0){
                    myhero.setIsInvincible(false);
                    invincibleDuration = 50;
                }
            }

            if(enemies.size() == 0){
                numberOfFoes = rnd.nextInt(3) + 2;
                createFoe();
            }
            if(myhero.getXcoor()>15000 && myhero.getXcoor()%15000 > 1 && myhero.getXcoor()%15000<20 && bonus == null){
                addBonus();;
            }
            if (now - lastUpdate >= 8_000_000) {

                myhero.update(now, 3);
                updateEnnemies(now);
                updateProjectiles();
                updateBonus();
                cam.update();

                if(myhero.getNumberOfLives() == 0){
                    loseScreen.showScore(distance);
                    primaryStage.setScene(loseScreen);
                    timer.stop();
                }

                render();
                lastUpdate = now ;
            }


        }
    };

    /**
     * Method to start the game, by launching the animation timer and listening to keyboard inputs.
     */
    public void Start() {
        listenKeys();
        timer.start();
    }
}
