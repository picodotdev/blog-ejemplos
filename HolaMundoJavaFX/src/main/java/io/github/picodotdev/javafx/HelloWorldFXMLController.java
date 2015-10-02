package io.github.picodotdev.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HelloWorldFXMLController implements Initializable {

    @FXML
    private Button button;

    @FXML
    private Button controllerButton;

    @FXML
    void onAction(ActionEvent event) {
        System.out.println("Hello World! (controllerButton)");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World! (button)");
            }
        });
    }
}