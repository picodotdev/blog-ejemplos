package io.github.picodotdev.serviceloader;

import java.util.Locale;

interface Saludador {
    /**
     * Retorna el idioma en el que este servicio saluda.
     */
    Locale getLocale();

    /**
     * Imprime en la consola un mensaje de saludo.
     */
    void saluda(String nombre);
}