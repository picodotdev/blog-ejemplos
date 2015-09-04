package io.github.picodotdev.serviceloader;

import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main1 {
    public static void main(String[] args) {
        ServiceLoader<Saludador> loader = ServiceLoader.load(Saludador.class);
        Iterable<Saludador> iterable = () -> loader.iterator();
        Stream<Saludador> stream = StreamSupport.stream(iterable.spliterator(), false);
        List<Locale> locales = stream.map(i -> i.getLocale()).collect(Collectors.toList());
    
        System.out.printf("Idiomas soportados:%s\n", locales);
    }
}