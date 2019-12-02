package com.tarekkma.encryptions;

import com.tarekkma.EncryptionAlgorithm;

public class Test implements EncryptionAlgorithm {

    @Override
    public String encrypt(String plaintext, String key) throws Exception {
        return plaintext.replaceAll("a","z");
    }

    @Override
    public String decrypt(String encrypted, String key) throws Exception {
        return encrypted.replaceAll("z","a");
    }

    @Override
    public boolean requireKey() {
        return true;
    }

    @Override
    public boolean isValidKey(String key) {
        return key.length() == 10;
    }

    @Override
    public String name() {
        return "Test Alog";
    }

    @Override
    public String description() {
        return "Just for testing";
    }
}
