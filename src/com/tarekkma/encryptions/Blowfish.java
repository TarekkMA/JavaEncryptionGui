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

public class Blowfish implements EncryptionAlgorithm {


    private SecretKeySpec getKey(String strKey) {
        MessageDigest sha;
        byte[] key;
        try {
            key = strKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);//only the first 128 bit
            return new SecretKeySpec(key, "Blowfish");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encrypt(String strToEncrypt, String secret) throws Exception {
        SecretKey secretKey = getKey(secret);
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    @Override
    public String decrypt(String strToDecrypt, String secret) throws Exception {
        SecretKey secretKey = getKey(secret);
        Cipher cipher = Cipher.getInstance("Blowfish");
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
        return "Blowfish";
    }

    @Override
    public String description() {
        return "Blowfish is a symmetric-key block cipher, designed in 1993 by Bruce Schneier and included in many cipher suites and encryption products. Blowfish provides a good encryption rate in software and no effective cryptanalysis of it has been found to date.";
    }
}