package com.tarekkma.encryptions;

import com.tarekkma.EncryptionAlgorithm;

public class Vigenere implements EncryptionAlgorithm {


    private String generateKey(String str, String key) {
        int x = str.length();

        StringBuilder keyBuilder = new StringBuilder(key);
        for (int i = 0; ; i++) {
            if (x == i)
                i = 0;
            if (keyBuilder.length() == str.length())
                break;
            keyBuilder.append(keyBuilder.charAt(i));
        }
        key = keyBuilder.toString();
        return key;
    }

    @Override
    public String encrypt(String plaintext, String key) throws Exception {
        key = generateKey(plaintext,key);
        StringBuilder cipher_text= new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++)
        {
            // converting in range 0-25
            int x = (plaintext.charAt(i) + key.charAt(i)) %26;

            // convert into alphabets(ASCII)
            x += 'A';

            cipher_text.append((char) (x));
        }
        return cipher_text.toString() + "\n\n\n===NEW KEY===\n"+key;
    }

    @Override
    public String decrypt(String encrypted, String key) throws Exception {
        StringBuilder orig_text= new StringBuilder();

        for (int i = 0 ; i < encrypted.length() &&
                i < key.length(); i++)
        {
            // converting in range 0-25
            int x = (encrypted.charAt(i) -
                    key.charAt(i) + 26) %26;

            // convert into alphabets(ASCII)
            x += 'A';
            orig_text.append((char) (x));
        }
        return orig_text.toString();
    }

    @Override
    public boolean requireKey() {
        return true;
    }

    @Override
    public boolean isValidKey(String key) {
        return true;
    }

    @Override
    public String name() {
        return "Vigenère Cipher";
    }

    @Override
    public String description() {
        return "The cipher was invented by Italian Giovan Battista Bellaso, who described it in 1553 in his book \"La cifra del. Sig. Giovan Battista Bellaso\". However it is named, due to the wrong widespread belief in the nineteenth century, after the French diplomat and alchemist Blaise de Vigenère, who lived in the sixteenth century.";
    }
}
