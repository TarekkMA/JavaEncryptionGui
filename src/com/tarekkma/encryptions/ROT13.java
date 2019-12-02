package com.tarekkma.encryptions;

public class ROT13 extends Caesar {


    @Override
    public String encrypt(String plaintext, String key) {
        return super.encrypt(plaintext, "13");
    }

    @Override
    public String decrypt(String encrypted, String key) {
        return super.decrypt(encrypted, "13");
    }

    @Override
    public boolean requireKey() {
        return false;
    }

    @Override
    public String name() {
        return "ROT13 Cipher";
    }

    @Override
    public String description() {
        return "The ROT13 cipher is a substitution cipher with a specific key where the letters of the alphabet are offset 13 places. I.e. all 'A's are replaced with 'N's, all 'B's are replaced with 'O's, and so on. It can also be thought of as a Caesar cipher with a shift of 13.\n" +
                "\n" +
                "The ROT13 cipher offers almost no security, and can be broken very easily. Even if an adversary doesn't know a piece of ciphertext has been enciphered with the ROT13 cipher, they can still break it by assuming it is a substitution cipher and determining the key using hill-climbing. The ROT13 cipher is also an Caesar cipher with a key of 13, so breaking it as a Caesar cipher also works.";
    }
}
