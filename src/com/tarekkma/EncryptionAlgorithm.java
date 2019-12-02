package com.tarekkma;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public interface EncryptionAlgorithm {
    String encrypt(String plaintext,String key) throws Exception;
    String decrypt(String encrypted,String key) throws Exception;
    boolean requireKey();
    boolean isValidKey(String key);

    String name();
    String description();
}
