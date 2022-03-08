package org.pl.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pl.model.AES;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class AESFrame extends Application {

    private AES aes;

    @FXML
    private TextField keyTextField;
    @FXML
    private TextArea textArea1;
    @FXML
    public TextArea textArea2;
    public byte[] textArea2Text;

    public AESFrame() {
        aes = new AES();
    }

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
    private void generateKey() {
        keyTextField.setText(KeyFactory.generateKey(16));
    }

    @FXML
    private void cipher() {
        try {
            textArea2Text = aes.cipher(
                            new ByteArrayInputStream(
                                    textArea1.getText().getBytes()
                            ), keyTextField.getText())
                    .readAllBytes();
            textArea2.setText(new String(textArea2Text));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(textArea2Text.length);
    }

    @FXML
    private void decipher() {
        try {
            textArea1.setText(new String(aes.decipher(
                            new ByteArrayInputStream(
                                    textArea2Text
                            ), keyTextField.getText())
                    .readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openKeyFile() {
        readFileTo(keyTextField);
    }

    @FXML
    private void saveKeyToFile() {
        writeFileFrom(keyTextField);
    }

    @FXML
    private void loadTextToEncrypt() {
        readFileTo(textArea1);
    }

    @FXML
    private void loadTextToDecrypt() {
        readFileTo(textArea2);
    }

    @FXML
    private void saveUnencryptedMessage() {
        writeFileFrom(textArea1);
    }

    @FXML
    private void saveEncryptedMessage() {
        writeFileFrom(textArea2);
    }

    private void writeFileFrom(TextInputControl from) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            if (file.canWrite()) {
                try (PrintWriter printWriter = new PrintWriter(file)) {
                    printWriter.print(from.getText());
                } catch (FileNotFoundException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wybrany plik nie istnieje", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać zapisu do wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
    }

    private void readFileTo(TextInputControl where) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file.canRead()) {
                try (Scanner scanner = new Scanner(file)) {
                    if (scanner.hasNext()) {
                        where.setText(scanner.nextLine());
                    }

                    while (scanner.hasNext()) {
                        where.appendText("\n");
                        where.appendText(scanner.nextLine());
                    }
                } catch (FileNotFoundException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wybrany plik nie istnieje", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać odczytu z wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
    }
}
