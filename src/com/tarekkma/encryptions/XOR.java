package com.tarekkma.encryptions;

import com.tarekkma.EncryptionAlgorithm;

public class XOR implements EncryptionAlgorithm {
    @Override
    public String encrypt(String plaintext, String key) throws Exception {
        // Define XOR key
        // Any character value will work
        char xorKey = 'P';

        // Define String to store encrypted/decrypted String
        StringBuilder outputString = new StringBuilder();

        // calculate length of input string
        int len = plaintext.length();

        // perform XOR operation of key
        // with every caracter in string
        for (int i = 0; i < len; i++)
        {
            outputString.append(Character.toString((char) (plaintext.charAt(i) ^ xorKey)));
        }

        return outputString.toString();

    }

    @Override
    public String decrypt(String encrypted, String key) throws Exception {
        //enc is same as dec
        return encrypt(encrypted,key);
    }

    @Override
    public boolean requireKey() {
        return false;
    }

    @Override
    public boolean isValidKey(String key) {
        return false;
    }

    @Override
    public String name() {
        return "XOR";
    }

    @Override
    public String description() {
        return "The simple XOR cipher was quite popular in early times of computers, in operating systems MS-DOS and Macintosh.";
    }
}
