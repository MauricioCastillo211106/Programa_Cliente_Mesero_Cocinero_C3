package Model;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Client implements Runnable{
    private AnchorPane anchor;
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
        Circle client = new Circle(15, Color.rgb(180, 140, 140));
        Platform.runLater(() -> {
            client.setLayoutX(24);
            client.setLayoutY(340);
            anchor.getChildren().add(client);
        });
        //Avanzar
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> client.setLayoutX(client.getLayoutX()+50));
        }
        boolean reservation = restaurant.reserved(Thread.currentThread().getName());
        if(reservation) {
            Platform.runLater(()-> client.setFill(Color.rgb(180, 140, 179)));
        }
        else {
            Platform.runLater(()-> client.setFill(Color.rgb(180, 140, 140)));
        }

        //Entrar
        int numMesa = restaurant.entry(Thread.currentThread().getName());
        String[] layout = positions[numMesa].split(" ");
        Platform.runLater(()-> {
            client.setLayoutX(Integer.parseInt(layout[0]));
            client.setLayoutY(Integer.parseInt(layout[1])+50);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Ordenar
        Platform.runLater(()-> client.setFill(Color.BLUE));
        restaurant.ordenar();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Comer
        Platform.runLater(()-> client.setFill(Color.YELLOW));
        try {
            restaurant.comer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Salir
        Platform.runLater(()-> client.setFill(Color.rgb(180, 140, 140)));
        restaurant.salir(numMesa);

    }
}
