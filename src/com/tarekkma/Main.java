package com.tarekkma;

import com.tarekkma.encryptions.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Application application = new Application(List.of(
                new Base64(),
                new Caesar(),
                new Atbash(),
                new ROT13(),
                new XOR()
        ));
    }
}
