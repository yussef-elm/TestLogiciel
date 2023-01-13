package com.enseirb.ui;

import com.enseirb.models.AirCraft;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.shape.Ellipse;


import java.util.ArrayList;

public class TCASSimulation extends Application {
    private AirCraft other;
    private AirCraft mainAircraft;
    private Canvas canvas;
    private TextArea messageArea1;
    private TextArea messageArea2;

    private ArrayList<AirCraft> airCrafts;
    Image planeImage = new Image("C:\\Users\\Lenovo\\Desktop\\3A\\TestLogicielProject\\TCAS\\src\\main\\resources\\assets\\aircraft.png");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Aicraft d'entrée
        mainAircraft = new AirCraft("TF234",60, 200, 0.4, 10000,2*Math.PI );
        other = new AirCraft("TF333",60, 350, 0.4, 10090,-Math.PI/8);


        VBox vbox = new VBox();
        vbox.setSpacing(10); // Espacement entre les TextArea
        vbox.setPadding(new Insets(10)); // Marge autour du VBox

        messageArea1 = new TextArea();
        Label title1 = new Label(mainAircraft.getName());
        title1.setStyle("-fx-font-weight: bold");

        messageArea2 = new TextArea();
        Label title2 = new Label(other.getName());
        title2.setStyle("-fx-font-weight: bold;");
        vbox.getChildren().addAll(title1, messageArea1,title2,messageArea2);

        airCrafts = new ArrayList<>();
        airCrafts.add(mainAircraft);
        airCrafts.add(other);
        messageArea1.setPrefSize(445, 5);
        messageArea1.setWrapText(false);
        messageArea1.setStyle("-fx-text-alignment: center;-fx-text-fill: red;" +
                " -fx-font-weight: bold;-fx-border-color: red; -fx-border-width: 3px;");
        messageArea1.setPrefRowCount(1);

        messageArea2.setPrefSize(445, 5);
        messageArea2.setWrapText(false);
        messageArea2.setStyle("-fx-text-alignment: center;-fx-text-fill: red;" +
                " -fx-font-weight: bold;-fx-border-color: red; -fx-border-width: 3px;");
        messageArea2.setPrefRowCount(1);

        canvas = new Canvas(900, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setBottom(vbox);
        primaryStage.setResizable(false);
        primaryStage.setTitle(" Traffic Collision Avoidance System (TCAS)\n");
        primaryStage.getIcons().add(planeImage);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                other.updatePosition();
                mainAircraft.updatePosition();
                mainAircraft.skyControle(other,messageArea1);
                other.skyControle(mainAircraft,messageArea2);
                drawAircrafts(gc);
            }
        }.start();
    }

    private void drawAircrafts(GraphicsContext gc) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (AirCraft aircraft : airCrafts) {
                gc.save();
                gc.translate(aircraft.getX()+80* Math.cos(aircraft.getDirection()), aircraft.getY()+80* Math.sin(aircraft.getDirection()));
                gc.rotate(Math.toDegrees(aircraft.getDirection()));
                gc.setFill(Color.rgb(255, 255, 0, 0.3));
                gc.fillOval(-250, -120, 500, 240);
                gc.restore();
                gc.save();
                gc.translate(aircraft.getX()+50* Math.cos(aircraft.getDirection()), aircraft.getY()+50* Math.sin(aircraft.getDirection()));
                gc.rotate(Math.toDegrees(aircraft.getDirection()));
                gc.setFill(Color.rgb(0, 255, 0, 0.3));
                gc.fillOval(-200, -100, 400, 200);
                gc.restore();
                gc.save();
                gc.translate(aircraft.getX()+20* Math.cos(aircraft.getDirection()), aircraft.getY()+ 20* Math.sin(aircraft.getDirection()));
                gc.rotate(Math.toDegrees(aircraft.getDirection()));
                gc.setFill(Color.rgb(255, 0, 0, 0.3));
                gc.fillOval(-100, -50, 200, 100);
                gc.restore();
            gc.fillOval(aircraft.getX(), aircraft.getY(), 5, 5);
            gc.drawImage(planeImage, aircraft.getX()-30, aircraft.getY()-20,30,30);
            gc.setFont(new Font(10));
            gc.fillText(aircraft.getName(), aircraft.getX()+5, aircraft.getY() - 4);
            gc.setFont(new Font(8));
            gc.fillText(aircraft.getAltitude()+"m", aircraft.getX()-10, aircraft.getY()+15);
        }
    }

}


