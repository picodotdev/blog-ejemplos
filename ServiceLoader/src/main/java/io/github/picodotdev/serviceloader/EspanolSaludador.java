package io.github.picodotdev.serviceloader;

import java.util.Locale;

public class EspanolSaludador implements Saludador {
    public Locale getLocale(){
        return new Locale("es", "ES");
    }

    public void saluda(String nombre) {
        System.out.printf("¡Hola %s!\n", nombre);
    }
}