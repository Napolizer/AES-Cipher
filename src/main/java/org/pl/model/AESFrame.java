package org.pl.model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class AESFrame extends Application {

    @FXML
    private TextField keyTextField;
    @FXML
    private TextField textField1;
    @FXML
    public TextField textField2;

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

    @FXML
    private void openKeyFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        Scanner scanner = new Scanner(file);
        keyTextField.setText(scanner.nextLine());
    }

    @FXML
    private void saveKeyToFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println(keyTextField.getText());
        printWriter.close();
    }

    @FXML
    private void loadTextToEncrypt() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        Scanner scanner = new Scanner(file);
        textField1.setText(scanner.nextLine());
    }

    @FXML
    private void loadTextToDecrypt() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        Scanner scanner = new Scanner(file);
        textField1.setText(scanner.nextLine());
    }

    @FXML
    private void saveUnencryptedMessage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println(textField1.getText());
        printWriter.close();
    }

    @FXML
    private void saveEncryptedMessage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println(textField2.getText());
        printWriter.close();
    }
}
