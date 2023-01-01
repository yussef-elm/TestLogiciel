package com.enseirb.tcas;

import com.enseirb.models.AirCraft;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class AirSpace {
    private List<AirCraft> aircrafts;

    public AirSpace() {
        aircrafts = new ArrayList<>();
    }

    public void addAircraft(AirCraft aircraft) {
        aircrafts.add(aircraft);
    }

    public void update(TextArea messageArea) {
        for (AirCraft aircraft : aircrafts) {
            aircraft.update();
        }
        detectCollisions(messageArea);
    }

    private void detectCollisions(TextArea messageArea) {
        for (int i = 0; i < aircrafts.size(); i++) {
            for (int j = i + 1; j < aircrafts.size(); j++) {
                double altitudeDifference = Math.abs(aircrafts.get(i).getAltitude() - aircrafts.get(j).getAltitude());
                if (isCollision(aircrafts.get(i), aircrafts.get(j)) && altitudeDifference <100) {
                    aircrafts.get(i).sendWarning(aircrafts.get(j),messageArea);
                    aircrafts.get(j).sendWarning(aircrafts.get(i),messageArea);
                    messageArea.appendText("Possible collision detected between aircraft "
                            +aircrafts.get(j).getName()+ " and " + aircrafts.get(i).getName()+"\n");
                    System.out.println("Possible collision detected between aircraft "
                            +aircrafts.get(j).getName()+ " and " + aircrafts.get(i).getName());

                }
            }
        }
    }


    private boolean isCollision(AirCraft a1, AirCraft a2) {
        double dx = a1.getX() - a2.getX();
        double dy = a1.getY() - a2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 20;
    }

    public List<AirCraft> getAircrafts() {
        return aircrafts;
    }

    public String getAvoidanceAction(AirCraft aircraft, AirCraft other) {
        int altDifference = aircraft.getAltitude() - other.getAltitude();
        if (altDifference > 0) {
            aircraft.setAltitude();
            return "DESCEND";
        } else if (altDifference < 0) {
            return "CLIMB";
        } else {
            return "TURN";
        }
    }
}
