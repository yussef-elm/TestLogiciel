package com.enseirb.ui;

import com.enseirb.models.AirCraft;
import com.enseirb.tcas.AirSpace;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TCASSimulation extends Application {
    private AirSpace airspace;
    private Canvas canvas;
    private TextArea messageArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        messageArea = new TextArea();
        airspace = new AirSpace();
        airspace.addAircraft(new AirCraft("TF234",50, 400, 0.2, 9000,-Math.PI / 8));
        airspace.addAircraft(new AirCraft("TF234",290, 200, 0.2, 4000,Math.PI / 4));

        airspace.addAircraft(new AirCraft("TF333",250, 50, 0.2, 10000,Math.PI / 4));
        airspace.addAircraft(new AirCraft("TF223",250, 100, 0.2, 10000,-Math.PI / 8));
        airspace.addAircraft(new AirCraft("TF003",750, 400, 0.2, 8000,-Math.PI ));


        messageArea.setPrefSize(800, 100);
        messageArea.setFont(new Font(12));
        messageArea.setStyle("-fx-text-fill: rgb(255, 0, 0);");


        canvas = new Canvas(800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setBottom(messageArea);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                airspace.update(messageArea);
                drawAircrafts(gc);
            }
        }.start();
    }

    private void drawAircrafts(GraphicsContext gc) {
        Image planeImage = new Image("C:\\Users\\Lenovo\\Desktop\\3A\\TestLogicielProject\\TCAS\\src\\main\\resources\\assets\\aircraft.png");
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (AirCraft aircraft : airspace.getAircrafts()) {
            gc.drawImage(planeImage, aircraft.getX(), aircraft.getY(),30,30);
            gc.setFont(new Font(10));
            gc.fillText(aircraft.getName(), aircraft.getX()+5, aircraft.getY() - 4);
            gc.setFont(new Font(8));
            gc.fillText(aircraft.getAltitude()+"m", aircraft.getX(), aircraft.getY()+30);
        }
    }

}



