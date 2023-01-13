package com.enseirb.tcas;

import com.enseirb.models.AirCraft;

import java.util.Locale;


public class TCAS {

    public static double i = 250; // rayon horizontal du volume ND
    public static double j = 120; // rayon vertical du volume ND
    public static double a = 200; // rayon horizontal du volume TA
    public static double b = 100; // rayon vertical du volume TA
    public static double c = 100; // rayon horizontal du volume RA
    public static double d = 50; // rayon vertical du volume RA
    public static double dND = 80; // distance entre centre et foyer RA
    public static double dTA = 50; // distance entre centre et foyer RA
    public static double dRA = 20; // distance entre centre et foyer RA
    public static double dALTITUDE = 300;//difference d'altitude


    private AirCraft airCraft;
    public TCAS(AirCraft airCraft) {
        this.airCraft = airCraft;
    }



    public boolean isInND(AirCraft other) {
            if (other != null) {
                double dx = airCraft.getOtherRotatedX(other) -(airCraft.getX()+dND);
                double dy = airCraft.getOtherRotatedY(other)-airCraft.getY();
                double dz = Math.abs(airCraft.getAltitude() - other.getAltitude());
                return ((dx * dx) / (i * i) + (dy * dy) / (j * j) <= 1) && dz < dALTITUDE;
            }else{
            throw new IllegalArgumentException("Exception Null Objet en entrée");
            }
    }


    public boolean isInTA(AirCraft other) {
        if(other!=null) {
            double dx = airCraft.getOtherRotatedX(other) - (airCraft.getX()+dTA);
            double dy = airCraft.getOtherRotatedY(other) -  airCraft.getY();
            double dz = Math.abs(airCraft.getAltitude() - other.getAltitude());
            return ((dx * dx) / (a * a) + (dy * dy) / (b * b) <= 1) && dz < dALTITUDE;
        }else{
            throw new IllegalArgumentException("Exception Null Objet en entrée");
        }
    }

    public boolean isInRA(AirCraft other) {
        if (other!=null) {
            double dx = airCraft.getOtherRotatedX(other) -(airCraft.getX()+dRA);
            double dy = airCraft.getOtherRotatedY(other) -airCraft.getY();
            double dz = Math.abs(airCraft.getAltitude() - other.getAltitude());
            return ((dx * dx) / (c * c) + (dy * dy) / (d * d) <= 1) && dz < dALTITUDE;
        }else{
            throw new IllegalArgumentException("Exception Null Objet en entrée");
        }
    }

    public boolean isInCLEAR(AirCraft other) {
        if (other!=null) {
            double dx = airCraft.getOtherRotatedX(other) -(airCraft.getX()+dND);
            double dy = airCraft.getOtherRotatedY(other) -airCraft.getY();
            double dz = Math.abs(airCraft.getAltitude() - other.getAltitude());
            return !(((dx * dx) / (i * i) + (dy * dy) / (j * j) <= 1) && dz < dALTITUDE);
        }else{
            throw new IllegalArgumentException("Exception Null Objet en entrée");
        }
    }

    public String trafficDetector(AirCraft other ) {
        String message="" ;
        if(other!=null) {
            if (airCraft.tcas.isInCLEAR(other) || (airCraft.tcas.isInND(other)
                    && !airCraft.tcas.isInTA(other) && !airCraft.tcas.isInRA(other))) {
                    message="";
            } else if (airCraft.tcas.isInND(other) && airCraft.tcas.isInTA(other) && !airCraft.tcas.isInRA(other)) {
                    message = "ALERT : Traffic, Traffic!".toUpperCase(Locale.ROOT)+"\n";
            } else if (airCraft.tcas.isInND(other) && airCraft.tcas.isInTA(other) && airCraft.tcas.isInRA(other)) {
                if (airCraft.getAltitude() >= other.getAltitude()) {
                    message="Required Action : Climb, climb now!".toUpperCase(Locale.ROOT)+"\n";
                } else {
                    message="Required Action : Descend, descend now!".toUpperCase(Locale.ROOT)+"\n";
                }
            } else{
                message="";
            }
        }else {
            throw new IllegalArgumentException("Exception Null Objet en entrée");
        }
        return message;
    }




}
