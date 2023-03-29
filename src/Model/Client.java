package Model;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Arrays;

public class Client implements Runnable{
    private final AnchorPane anchor;
    private Restaurant restaurant;
    private static String[] positions;
    public Client(AnchorPane anchor, Restaurant restaurant) {
        this.anchor = anchor;
        this.restaurant = restaurant;
        positions = new String[] {
                "256 56", "347 56", "441 56", "540 56",
                "256 130", "347 130", "441 130", "540 130",
                "256 414", "347 414", "441 414", "540 414",
                "256 495", "347 495", "441 495", "540 495",
                "256 576", "347 576", "441 576", "540 576"
        };



    }
    @Override
    public void run() {
        Image client = new Image(getClass().getResource("/principal/Resource/img/the-simpsons-homer-simpson.gif").toExternalForm());
        ImageView imageView = new ImageView(client);
        Image clientEating = new Image(getClass().getResource("/principal/Resource/img/breakfast-homer-simpson.gif").toExternalForm());

        imageView.setFitWidth(50); // Establecer la anchura
        imageView.setFitHeight(50); // Establecer la altura
        Platform.runLater(() -> {
            imageView.setLayoutX(24);
            imageView.setLayoutY(340);
            anchor.getChildren().add(imageView);
        });
        //Avanzar

        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), imageView);
        transition2.setOnFinished(event -> {
            imageView.setOpacity(1);
            transition2.setCycleCount(1);
        });

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                transition2.setToX(imageView.getLayoutX() + 200);
                transition2.play();
                transition2.setOnFinished(event -> imageView.setOpacity(1));
            });
        }


        //Entrar

        int numMesa = restaurant.entry(Thread.currentThread().getName());
        String[] layout = positions[numMesa].split(" ");
        Platform.runLater(() -> {
            imageView.setLayoutX(Integer.parseInt(layout[0]));
            imageView.setLayoutY(Integer.parseInt(layout[1]) + 20);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




            restaurant.ordenar();


        //Comer
        imageView.setImage(clientEating);
        try {
            TranslateTransition transition4 = new TranslateTransition(Duration.seconds(1), imageView);
            Platform.runLater(() -> {
                transition4.play();
                transition4.setOnFinished(event -> imageView.setOpacity(1));
            });

            restaurant.comer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Salir
        imageView.setImage(client);
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(1), imageView);
        transition2.setOnFinished(event -> {
            imageView.setOpacity(1);
            transition3.setCycleCount(1);
        });


       restaurant.salir(numMesa);
        Platform.runLater(() -> {
            anchor.getChildren().remove(imageView);
        });


    }
    }




