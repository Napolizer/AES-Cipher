package org.pl.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AESFrame extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent start = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("AESFrame.fxml")));
        stage.setTitle("AES");
        stage.setScene(new Scene(start, 727, 497));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
