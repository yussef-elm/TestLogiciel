package com.enseirb.models;

import com.enseirb.tcas.TCAS;
import com.enseirb.tcas.TCASAlert;
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

}
