package org.pl.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Program {
    public static void main(String[] args) throws IOException {
        AES aes = new AES();
        String key = "a".repeat(16);

        InputStream stream = aes.cipher(
                new ByteArrayInputStream(
                        "Źdźbło trawy!!".getBytes(StandardCharsets.UTF_8) // Kodowanie kurwa!!!
                ), key);

        byte[] encryption = stream.readAllBytes();
        String encryptedText = new String(encryption);

        stream = aes.decipher(
                new ByteArrayInputStream(
                        encryption
                ), key
        );

        byte[] decryption = stream.readAllBytes();
        String decryptedText = new String(decryption);

        System.out.println(encryptedText);
        System.out.println(decryptedText);
    }
}
