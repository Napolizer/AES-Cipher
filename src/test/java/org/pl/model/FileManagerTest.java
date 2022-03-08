package org.pl.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FileManagerTest {
    private FileManager fileManager;

    public FileManagerTest() {
        fileManager = new FileManager();
    }

    @Test
    void cipherDecipherUTF8TextFileTest() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/UTF-8.txt"); // Do poprawki
            File destinationFile = new File("src/test/resources/encoded.txt");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));

            destinationFile.delete();
        });
    }

    @Test
    void cipherDecipherPdfFileTest() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/E01_instrukcja.pdf"); // Do poprawki
            File destinationFile = new File("src/test/resources/encoded.pdf");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));

            destinationFile.delete();
        });
    }
}