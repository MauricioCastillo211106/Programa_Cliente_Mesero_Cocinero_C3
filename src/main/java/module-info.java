module com.example.sn {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sn to javafx.fxml;
    exports com.example.sn;
    exports com.example.sn.Controllers;
    opens com.example.sn.Controllers to javafx.fxml;
}