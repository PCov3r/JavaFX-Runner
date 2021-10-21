package com.runner;

public class Camera {
    private double xcoor;
    private double ycoor;

    public Camera(Integer xcoor, Integer ycoor) throws Exception{
//        if( xcoor<0 || xcoor>600 || ycoor<0 || ycoor>400) {
//            throw new Exception("Coordonnées non valides");
//        }
//        else{
            this.xcoor = xcoor;
            this.ycoor = ycoor;
//        }
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

    public void update(long time){

    }
}
