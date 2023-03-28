package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Client implements Runnable{
    private final AnchorPane anchor;
    private Restaurant restaurant;
    private static String[] positions;
    public Client(AnchorPane anchor, Restaurant restaurant){
        this.anchor = anchor;
        this.restaurant=restaurant;
        positions = new String[26];
        positions[0] = "507 56";
        positions[1] = "602 56";
        positions[2] = "695 56";
        positions[3] = "799 56";

        positions[4] = "507 130";
        positions[5] = "602 130";
        positions[6] = "695 130";
        positions[7] = "799 130";

        positions[8] = "508 414";
        positions[9] = "602 414";
        positions[10] = "695 414";
        positions[11] = "799 414";

        positions[12] = "508 495";
        positions[13] = "602 495";
        positions[14] = "695 495";
        positions[15] = "799 495";

        positions[16] = "508 576";
        positions[17] = "602 576";
        positions[18] = "695 576";
        positions[19] = "799 576";


    }
    @Override
    public void run() {
        Image client = new Image(getClass().getResource("/principal/Resource/img/the-simpsons-homer-simpson.gif").toExternalForm());
        ImageView imageView = new ImageView(client);
        imageView.setFitWidth(50); // Establecer la anchura
        imageView.setFitHeight(50); // Establecer la altura
        Platform.runLater(() -> {
            imageView.setLayoutX(24);
            imageView.setLayoutY(340);
            anchor.getChildren().add(imageView);
        });
        //Avanzar
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> imageView.setLayoutX(imageView.getLayoutX() + 50));
        }
        boolean reservation = restaurant.reserved(Thread.currentThread().getName());
        Image clientImage;
        if (reservation) {
            clientImage = new Image("file:principal/Resource/img/chef.png");
        } else {
            clientImage = new Image("file:principal/Resource/img/chef.png");
        }

        ImageView clientView = new ImageView(clientImage);
        clientView.setFitWidth(30);
        clientView.setFitHeight(30);

        Platform.runLater(() -> {
            clientView.setLayoutX(24);
            clientView.setLayoutY(340);
            anchor.getChildren().add(clientView);
        });

        //Entrar
        int numMesa = restaurant.entry(Thread.currentThread().getName());
        String[] layout = positions[numMesa].split(" ");
        Platform.runLater(() -> {
            imageView.setLayoutX(Integer.parseInt(layout[0]));
            imageView.setLayoutY(Integer.parseInt(layout[1]) + 50);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Ordenar

        restaurant.ordenar();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Comer

        try {
            restaurant.comer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Salir

            Platform.runLater(() -> {
                anchor.getChildren().remove(imageView);
            });
        restaurant.salir(numMesa);
        System.out.println("Salio");
    }
    }




