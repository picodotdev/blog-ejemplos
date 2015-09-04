package io.github.picodotdev.serviceloader;

import java.util.Locale;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main2 {
    public static void main(String[] args) {
        Locale locale = new Locale("eu", "ES");
        String nombre = "picodotdev";
    
        ServiceLoader<Saludador> loader = ServiceLoader.load(Saludador.class);
        Iterable<Saludador> iterable = () -> loader.iterator();
        Stream<Saludador> stream = StreamSupport.stream(iterable.spliterator(), false);
        Saludador saludador = stream.filter(i -> i.getLocale().equals(locale)).findFirst().get();
    
        System.out.printf("Saludo en %s: ", locale);
        saludador.saluda(nombre);
    }
}