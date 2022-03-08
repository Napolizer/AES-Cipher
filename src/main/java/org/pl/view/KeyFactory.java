package org.pl.view;

import org.apache.commons.lang3.RandomStringUtils;

public class KeyFactory {
    public static String generateKey(int size) throws IllegalArgumentException {
        validateSize(size);
        return RandomStringUtils.randomAlphanumeric(size);
    }

    public static void validateSize(int size) throws IllegalArgumentException {
        boolean ok = switch (size) {
            case 16, 24, 32 -> true;
            default -> false;
        };

        if (!ok) {
            throw new IllegalArgumentException("Rozmiar klucza: " + size + " nie jest wspierany");
        }
    }
}
