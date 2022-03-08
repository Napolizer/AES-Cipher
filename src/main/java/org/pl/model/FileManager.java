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
    public void encryptFile(File file, File destination, String key) throws IOException {
        if (!destination.exists()) {
            if (!destination.createNewFile()) {
                throw new IOException("Nie udało się utworzyć pliku " + destination.getName());
            }
        }

        // Encoding

        try (FileInputStream inputStream = new FileInputStream(file.getAbsolutePath())) {
            AES aes = new AES();
            InputStream encryptedData = aes.cipher(inputStream, key);
            try (FileOutputStream outputStream = new FileOutputStream(destination.getAbsolutePath())) {
                outputStream.write(encryptedData.readAllBytes());
            }
        }
    }

    public void decryptFile(File file, File destination, String key) throws IOException {
        if (!destination.exists()) {
            if (!destination.createNewFile()) {
                throw new IOException("Nie udało się utworzyć pliku " + destination.getName());
            }
        }

        // Encoding

        try (FileInputStream inputStream = new FileInputStream(file.getAbsolutePath())) {
            AES aes = new AES();
            InputStream encryptedData = aes.decipher(inputStream, key);
            try (FileOutputStream outputStream = new FileOutputStream(destination.getAbsolutePath())) {
                outputStream.write(encryptedData.readAllBytes());
            }
        }
    }
}
