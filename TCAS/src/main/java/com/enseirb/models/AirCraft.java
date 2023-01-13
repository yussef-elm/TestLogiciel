package com.enseirb.models;

import com.enseirb.tcas.TCAS;
import com.enseirb.ui.TCASAlert;
import javafx.scene.control.TextArea;

public class AirCraft {

    private String name;
    private double x;
    private double y;
    private double speed;
    private double direction;
    private int altitude;
    public TCAS tcas;

    public AirCraft(String name ,double x, double y, double speed,int altitude, double direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.altitude= altitude;
        this.tcas= new TCAS(this);
    }

    public void updatePosition() {
        x += speed * Math.cos(direction);
        y += speed * Math.sin(direction);
    }

    public void skyControle(AirCraft other, TextArea messageArea){
        new TCASAlert(messageArea).sendWarning(tcas.trafficDetector(other));
    }

    public double getOtherRotatedX(AirCraft other){
        return this.getX() + (other.getX()-this.getX())*Math.cos(-this.getDirection())
                - (other.getY()-this.getY())*Math.sin(-this.getDirection()) ;
    }

    public double getOtherRotatedY(AirCraft other){
        return this.getY() +(other.getX()-this.getX())*Math.sin(-this.getDirection())
                + (other.getY()-this.getY())*Math.cos(-this.getDirection());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getAltitude() {
        return altitude;
    }

    public double getDirection() {
        return direction;
    }

}
