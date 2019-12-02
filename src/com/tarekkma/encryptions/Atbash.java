package com.tarekkma.encryptions;

public class Atbash extends Caesar {


    @Override
    public String encrypt(String plaintext, String key) {
        return super.encrypt(plaintext, "25");
    }

    @Override
    public String decrypt(String encrypted, String key) {
        return super.decrypt(encrypted, "25");
    }

    @Override
    public boolean requireKey() {
        return false;
    }

    @Override
    public String name() {
        return "Atbash Cipher";
    }

    @Override
    public String description() {
        return "The Atbash cipher is a substitution cipher with a specific key where the letters of the alphabet are reversed. I.e. all 'A's are replaced with 'Z's, all 'B's are replaced with 'Y's, and so on. It was originally used for the Hebrew alphabet, but can be used for any alphabet.\n" +
                "\n" +
                "The Atbash cipher offers almost no security, and can be broken very easily. Even if an adversary doesn't know a piece of ciphertext has been enciphered with the Atbash cipher, they can still break it by assuming it is a substitution cipher and determining the key using hill-climbing. The Atbash cipher is also an Affine cipher with a=25 and b = 25, so breaking it as an affine cipher also works.";
    }
}
