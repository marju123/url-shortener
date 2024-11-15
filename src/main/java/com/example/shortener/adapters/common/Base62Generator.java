package com.example.shortener.adapters.common;

import java.math.BigInteger;

public class Base62Generator {
    private static final String BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = BASE62_ALPHABET.length();
    private static final int DEFAULT_ALIAS_LENGTH = 8;
    public static String encodeFromString(String value) {
        BigInteger bigInt = new BigInteger(1, value.getBytes());
        String base62Encoded = encode(bigInt.longValue());
        return padToFixedLength(base62Encoded, DEFAULT_ALIAS_LENGTH);
    }

    private static String encode(long value) {
        if (value == 0) {
            return String.valueOf(BASE62_ALPHABET.charAt(0));
        }
        StringBuilder encoded = new StringBuilder();
        while (value > 0) {
            encoded.insert(0, BASE62_ALPHABET.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return encoded.toString();
    }

    private static String padToFixedLength(String input, int length) {
        if (input.length() >= length) {
            return input.substring(0, length);
        }
        StringBuilder padded = new StringBuilder();
        while (padded.length() + input.length() < length) {
            padded.append(BASE62_ALPHABET.charAt(0));
        }
        padded.append(input);
        return padded.toString();
    }
}
