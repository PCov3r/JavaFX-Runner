package com.runner;

public class Camera {
    private double xcoor, ycoor, camOffset;
    private double vx, vy;
    private Hero h;

    public Camera(double xcoor, double ycoor, double camoff, Hero h){
            this.xcoor = xcoor;
            this.ycoor = ycoor;
            this.vx = 0;
            this.vy = 0;
            this.h = h;
            this.camOffset = camoff;
    }

    public void reset(){
        this.xcoor = 0;
    }

    public double calculateV(double vinit,double coor, double coorhero, double dt, double k, double m, double f){
        double ax = (k/m)*(coorhero-coor)-(f/m)*vinit;
        double vxnext = ax*dt + vinit;
        return(vxnext);
    }
    public double calculateCoor(double vx, double coor, double dt){
        double next = vx*dt + coor;
        return(next);
    }

    public double getXcoor() {
        return xcoor;
    }

    public double getYcoor() {
        return ycoor;
    }

    public double getOffset() {
        return camOffset;
    }

    public void update(){
        this.vy = calculateV(this.vy,this.ycoor,h.getYcoor(),0.5,1,1,0.8);
        this.vx = calculateV(this.vx,this.xcoor,h.getXcoor(),0.1,1,1,0.8);
        //this.ycoor = calculateCoor(this.vy, this.ycoor, 0.5);
        this.xcoor = calculateCoor(this.vx, this.xcoor, 0.1);

    }

    @Override
    public String toString(){
        return "Coordinates{" +
                "x='" + xcoor + '\'' +
                ", y='" + ycoor + '\'' +
                '}';
    }

}
