package com.enseirb.models;

import javafx.scene.control.TextArea;

public class AirCraft {
    private String name;
    private double x;
    private double y;
    private double speed;
    private double direction;
    private int altitude;

    public AirCraft(String name ,double x, double y, double speed,int altitude, double direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.altitude= altitude;
    }

    public void update() {
        x += speed * Math.cos(direction);
        y += speed * Math.sin(direction);
    }

    public void sendWarning(AirCraft other, TextArea messageArea) {
        String message = "Collision warning with aircraft "+other.name+" at (" + other.x + ", " + other.y + ")" ;
        messageArea.appendText(message+"\n");
        System.out.println(message);
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

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }
}
