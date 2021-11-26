package com.runner;

/**
 * We attach a camera element to our hero. It is a very important piece as it will be used to calculate the background's movement according to the hero position.
 */
public class Camera {
    private double xcoor, ycoor, camOffset;
    private double vx, vy;
    private Hero h;

    /**
     * A camera is attached to the hero and will follow it. We can give it an offset, so that the hero appears further from the beginning of the screen.
     * @param xcoor the x origin of the camera
     * @param ycoor the y origin of the camera
     * @param camoff the offset between the camera and the hero
     * @param h the hero associated to the camera
     */
    public Camera(double xcoor, double ycoor, double camoff, Hero h){
            this.xcoor = xcoor;
            this.ycoor = ycoor;
            this.vx = 0;
            this.vy = 0;
            this.h = h;
            this.camOffset = camoff;
    }

    /**
     * A simple method that reset the camera position. No action on the y axis is needed as it doesn't move from its origin
     */
    public void reset(){
        this.xcoor = 0;
    }

    /**
     * A method that calculate the speed on the x axis
     * @param vlast the last speed of the camera
     * @param coor the initial x coordinate of the camera
     * @param coorhero the initial x coordinate of the hero
     * @param dt the time interval resulting from the temporal discretization of the equations
     * @param k the spring coefficient
     * @param m the mass of the system
     * @param f the coefficient of friction
     * @return the next speed value along the x axis
     */
    public double calculateV(double vlast,double coor, double coorhero, double dt, double k, double m, double f){
        double ax = (k/m)*(coorhero-coor)-(f/m)*vlast;
        double vxnext = ax*dt + vlast;
        return(vxnext);
    }

    /**
     * A method to calculate the next x coordinate of the camera to make it match the hero's one
     * @param vx the speed along the x axis
     * @param coor the x coordinate of the camera
     * @param dt the time interval resulting from the temporal discretization of the equations
     * @return the next x coordinate of the camera
     */
    public double calculateCoor(double vx, double coor, double dt){
        double next = vx*dt + coor;
        return(next);
    }

    /**
     *
     * @return the x coordinate of the camera
     */
    public double getXcoor() {
        return xcoor;
    }

    /**
     *
     * @return the y coordinate of the camera
     */
    public double getYcoor() {
        return ycoor;
    }

    /**
     *
     * @return the camera-hero offset
     */
    public double getOffset() {
        return camOffset;
    }

    /**
     * Method that update the position of the camera to make it follow the hero using a spring-mass effect
     */
    public void update(){
        this.vx = calculateV(this.vx,this.xcoor,h.getXcoor(),0.1,1,1,0.8);
        this.xcoor = calculateCoor(this.vx, this.xcoor, 0.1);

    }

    /**
     * A simple method to get the camera position for debugging purpose
     * @return a string containing the camera position
     */
    @Override
    public String toString(){
        return "Coordinates{" +
                "x='" + xcoor + '\'' +
                ", y='" + ycoor + '\'' +
                '}';
    }

}
