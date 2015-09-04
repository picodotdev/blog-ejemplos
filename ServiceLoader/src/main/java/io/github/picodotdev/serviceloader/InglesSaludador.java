package io.github.picodotdev.serviceloader;

import java.util.Locale;

public class InglesSaludador implements Saludador {
    public Locale getLocale(){
        return new Locale("en", "GB");
    }

    public void saluda(String nombre) {
        System.out.printf("Hello %s!\n", nombre);
    }
}