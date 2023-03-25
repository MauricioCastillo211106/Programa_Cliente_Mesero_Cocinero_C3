module restaurant.restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens restaurant.restaurant to javafx.fxml;
    exports restaurant.restaurant;
    exports restaurant.restaurant.View;
    opens restaurant.restaurant.View to javafx.fxml;
}