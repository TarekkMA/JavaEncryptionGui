package com.tarekkma;

import com.tarekkma.encryptions.*;
import com.tarekkma.ui.AppView;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<EncryptionAlgorithm> algorithms = List.of(
                new AES(),
                new DES(),
                new IDEA(),
                new Blowfish(),
                new Base64(),
                new Caesar(),
                new Atbash(),
                new ROT13(),
                new XOR()
        );

        AppView view = new AppView(algorithms);
        view.setVisible(true);
    }
}
