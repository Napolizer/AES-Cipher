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
import java.nio.file.Files;
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
        File file = readFileTo(textArea2);
        textArea2Text = readFileToBuffer(file);
    }

    @FXML
    private void saveUnencryptedMessage() {
        writeFileFrom(textArea1);
    }

    @FXML
    private void saveEncryptedMessage() {
        writeFileFromBuffer(textArea2Text);
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

    private File readFileTo(TextInputControl where) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file.canRead()) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    where.setText(new String(inputStream.readAllBytes()));
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił problem podczas wczytywania pliku", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać odczytu z wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
        return file;
    }

    private byte[] readFileToBuffer() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        return readFileToBuffer(file);
    }

    private byte[] readFileToBuffer(File file) {
        if (file != null) {
            if (file.canRead()) {
                try {
                    return Files.readAllBytes(file.toPath());
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił problem z odczytem pliku", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dokonać odczytu z wybranego pliku", ButtonType.OK);
                alert.show();
            }
        }
        return null;
    }

    private File writeFileFromBuffer(byte[] buffer) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);

        writeFileFromBuffer(buffer, file);
        return file;
    }

    private void writeFileFromBuffer(byte[] buffer, File file) {
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
