package com.runner;

public class Camera {
    private double xcoor;
    private double ycoor;
    private double vx;
    private Hero h;

    public Camera(Integer xcoor, Integer ycoor, Hero h) throws Exception{
//        if( xcoor<0 || xcoor>600 || ycoor<0 || ycoor>400) {
//            throw new Exception("Coordonnées non valides");
//        }
//        else{
            this.xcoor = xcoor;
            this.ycoor = ycoor;
            this.vx = 0;
            this.h = h;
//        }
    }

    public double calculateV(double vinit,double xcoor, double xhero, double dt, double k, double m, double f){
        double ax = (k/m)*(xhero-xcoor)-(f/m)*vinit;
        double vxnext = ax*dt + vinit;
        return(vxnext);
    }
    public double calculateX(double vx, double xcoor, double dt){
        double xnext = vx*dt + xcoor;
        return(xnext);
    }

    @Override
    public String toString(){
        return "Coordinates{" +
                "x='" + xcoor + '\'' +
                ", y='" + ycoor + '\'' +
                '}';
    }

    public double getXcoor() {
        return xcoor;
    }

    public double getYcoor() {
        return ycoor;
    }

    public void update(){
        this.vx = calculateV(this.vx,this.xcoor,h.getXcoor(),0.15,1,1,0.8);
        this.xcoor = calculateX(this.vx, this.xcoor, 0.15);
    }

}
