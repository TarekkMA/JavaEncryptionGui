package com.tarekkma.encryptions;

import com.tarekkma.EncryptionAlgorithm;
import com.tarekkma.Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class DES implements EncryptionAlgorithm {


    private SecretKeySpec getKey(String strKey) {
        MessageDigest sha;
        byte[] key;
        try {
            key = strKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 8);//only the first 64 bit
            return new SecretKeySpec(key, "DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encrypt(String strToEncrypt, String secret) throws Exception {
        SecretKey secretKey = getKey(secret);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    @Override
    public String decrypt(String strToDecrypt, String secret) throws Exception {
        SecretKey secretKey = getKey(secret);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }

    @Override
    public boolean requireKey() {
        return true;
    }

    @Override
    public boolean isValidKey(String key) {
        return !key.isEmpty();
    }

    @Override
    public String generateKey() {
        return Utils.randomString();
    }

    @Override
    public String name() {
        return "DES (Data Encryption Standard)";
    }

    @Override
    public String description() {
        return "The Data Encryption Standard is a symmetric-key algorithm for the encryption of electronic data. Although its short key length is of 56 bits, criticized from the beginning, makes it too insecure for most current applications, it was highly influential in the advancement of modern cryptography.";
    }
}