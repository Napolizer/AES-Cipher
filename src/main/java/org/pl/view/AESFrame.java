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
import org.pl.model.FileManager;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

public class AESFrame extends Application {

    private final AES aes;

    @FXML
    private TextField keyTextField;
    @FXML
    private TextArea textArea1;
    @FXML
    public TextArea textArea2;
    private byte[] textArea2Text;

    @FXML
    private RadioButton rb16;
    @FXML
    private RadioButton rb24;
    @FXML
    private RadioButton rb32;

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
        int size = 0;
        if (rb16.isSelected()) {
            size = 16;
        } else if (rb24.isSelected()){
            size = 24;
        } else if (rb32.isSelected()) {
            size = 32;
        }
        keyTextField.setText(KeyFactory.generateKey(size));
    }

    @FXML
    private void cipher() {
        validateKey();
        try {
            textArea2Text = aes.cipher(
                            new ByteArrayInputStream(
                                    textArea1.getText().getBytes()
                            ), keyTextField.getText())
                    .readAllBytes();
            textArea2.setText(new String(textArea2Text));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Szyfrowanie nie powiodło się:\n" + e.getLocalizedMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void decipher() {
        validateKey();
        try {
            textArea1.setText(new String(aes.decipher(
                            new ByteArrayInputStream(
                                    textArea2Text
                            ), keyTextField.getText())
                    .readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Deszyfrowanie nie powiodło się:\n" + e.getLocalizedMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void cipherFile() {
        try {
            FileManager.encryptFile(
                    selectFileToRead(),
                    selectFileToWrite(),
                    keyTextField.getText()
            );
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił błąd podczas szyfrowania pliku: " + e.getLocalizedMessage(), ButtonType.OK);
            alert.show();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Szyfrowanie zakończone pomyślnie", ButtonType.OK);
        alert.show();
    }

    @FXML
    private void decipherFile() {
        try {
            FileManager.decryptFile(
                    selectFileToRead(),
                    selectFileToWrite(),
                    keyTextField.getText()
            );
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił błąd podczas deszyfrowania pliku: " + e.getLocalizedMessage(), ButtonType.OK);
            alert.show();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deszyfrowanie zakończone pomyślnie", ButtonType.OK);
        alert.show();
    }

    @FXML
    private void clear1() {
        textArea1.clear();
    }

    @FXML
    private void clear2() {
        textArea2Text = new byte[0];
        textArea2.clear();
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
        textArea2Text = readFileTo(textArea2);
    }

    @FXML
    private void saveUnencryptedMessage() {
        writeFileFrom(textArea1);
    }

    @FXML
    private void saveEncryptedMessage() {
        writeFileFrom(textArea2Text);
    }

    private void writeFileFrom(TextInputControl from) {
        writeFileFrom(from.getText().getBytes());
    }

    private void writeFileFrom(byte[] buffer) {
        File file = selectFileToWrite();

        if (file != null) {
            if (file.canWrite()) {
                try (var fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(buffer);
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił problem z zapisem pliku", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać zapisu do wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
    }

    private File selectFileToRead() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(null);
    }

    private File selectFileToWrite() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(null);
    }

    private byte[] readFileTo(TextInputControl where) {
        File file = selectFileToRead();

        if (file != null) {
            if (file.canRead()) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = inputStream.readAllBytes();
                    if (where != null) {
                        where.setText(new String(buffer));
                    }
                    return buffer;
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił problem podczas wczytywania pliku", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać odczytu z wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
        return null;
    }

    private void validateKey() {
        try {
            KeyFactory.validateSize(keyTextField.getLength());
        } catch(IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Podany klucz ma nieprawidłowy rozmiar", ButtonType.OK);
            alert.show();
            throw(e);
        }
    }
}
