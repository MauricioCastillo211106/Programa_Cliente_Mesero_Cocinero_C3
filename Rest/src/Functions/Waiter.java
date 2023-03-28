package Functions;

import Model.Restaurant;
import javafx.scene.layout.AnchorPane;

public class Waiter implements Runnable{
    private Restaurant restaurant;
    private AnchorPane padre;
    public Waiter(AnchorPane padre, Restaurant restaurant){
        this.restaurant=restaurant;
        this.padre=padre;
    }
    @Override
    public void run() {

        while (true){
            restaurant.servicioOrden();
        }
    }
}
