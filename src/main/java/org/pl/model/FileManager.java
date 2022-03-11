package org.pl.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FileManager {
    private static final AES aes = new AES();

    public static void encryptFile(File file, File destination, String key) throws IOException {
        createIfNotExists(destination);
        var fileStream = new ByteArrayInputStream(readFromFile(file));

        InputStream encryptedData = aes.cipher(fileStream, key);
        writeToFile(destination, encryptedData);
    }

    public static void writeToFile(File destination, InputStream stream) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(destination.getAbsolutePath())) {
            createIfNotExists(destination);
            outputStream.write(stream.readAllBytes());
        }
    }

    public static byte[] readFromFile(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file.getAbsolutePath())) {
            return inputStream.readAllBytes();
        }
    }

    public static void createIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Nie udało się utworzyć pliku " + file.getName());
            }
        }
    }

    public static void decryptFile(File file, File destination, String key) throws IOException {
        var inputStream = new ByteArrayInputStream(readFromFile(file));
        InputStream encryptedData = aes.decipher(inputStream, key);

        writeToFile(destination, encryptedData);
    }
}
