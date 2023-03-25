package com.example.sn.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

public class HelloController implements Observer {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("hola");

    }
    @Override
    public void update(Observable o, Object arg) {

    }
}