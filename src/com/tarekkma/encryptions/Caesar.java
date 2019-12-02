package com.tarekkma.encryptions;

import com.tarekkma.EncryptionAlgorithm;

public class Caesar implements EncryptionAlgorithm {


    @Override
    public String encrypt(String plaintext, String key) {
        int shiftValue = Integer.parseInt(key);
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            encryptedMessage.append((char)(c + shiftValue));
        }
        return encryptedMessage.toString();
    }

    @Override
    public String decrypt(String encrypted, String key) {
        int shiftValue = Integer.parseInt(key);
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encrypted.toCharArray()) {
            decryptedMessage.append((char)(c - shiftValue));
        }
        return decryptedMessage.toString();
    }

    @Override
    public boolean requireKey() {
        return true;
    }

    @Override
    public boolean isValidKey(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String name() {
        return "Caesar Cipher";
    }

    @Override
    public String description() {
        return "The Caesar cipher is one of the earliest known and simplest ciphers. It is a type of substitution cipher in which each letter in the plaintext is 'shifted' a certain number of places down the alphabet. For example, with a shift of 1, A would be replaced by B, B would become C, and so on. The method is named after Julius Caesar, who apparently used it to communicate with his generals.\n" +
                "\n" +
                "More complex encryption schemes such as the Vigenère cipher employ the Caesar cipher as one element of the encryption process. The widely known ROT13 'encryption' is simply a Caesar cipher with an offset of 13. The Caesar cipher offers essentially no communication security, and it will be shown that it can be easily broken even by hand.";
    }
}
