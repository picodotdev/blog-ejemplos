package io.github.picodotdev.serviceloader;

import java.util.Locale;

public class EuskeraSaludador implements Saludador {
    public Locale getLocale(){
        return new Locale("eu", "ES");
    }

    public void saluda(String nombre) {
        System.out.printf("Kaixo %s!\n", nombre);
    }
}