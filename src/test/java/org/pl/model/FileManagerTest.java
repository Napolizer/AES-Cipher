package org.pl.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FileManagerTest {
    private FileManager fileManager;
    private File destinationFile;

    @BeforeEach
    private void prepare() {
        fileManager = new FileManager();
        destinationFile = new File("src/test/resources/encoded.tmp");
    }

    @AfterEach
    private void cleanup() {
        if (destinationFile.exists()) {
            destinationFile.delete();
        }
    }

    @Test
    void cipherDecipherUTF8TextFileTest() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/UTF-8.txt");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));

            destinationFile.delete();
        });
    }

    @Test
    void cipherDecipherJpgFile() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/obraz.jpg");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));
        });
    }

    @Test
    void cipherDecipherBmpFile() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/obraz.bmp");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));
        });
    }

    @Test
    void cipherDecipherXcfFile() {
        String key = "a".repeat(16);
        assertDoesNotThrow(() -> {
            File testFile = new File("src/test/resources/obraz.xcf");

            fileManager.encryptFile(testFile, destinationFile, key);
            fileManager.decryptFile(destinationFile, destinationFile, key);

            byte[] file1 = Files.readAllBytes(testFile.toPath());
            byte[] file2 = Files.readAllBytes(destinationFile.toPath());

            assertThat(file1, equalTo(file2));
        });
    }
}