package io.github.picodotdev.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {        
        Button btn = new Button();
        btn.setText("¡Hola mundo!");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("¡Hola mundo!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        stage.setTitle("¡Hola mundo!");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}