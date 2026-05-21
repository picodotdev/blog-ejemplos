package io.github.picodotdev.blogbitix.javaprocess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        // Iniciar el proceso
        ProcessBuilder builder = new ProcessBuilder().command("cat", "/proc/uptime");
        Process process = builder.start();

        // Alternativa a ProcessBuilder
        //Process process = Runtime.getRuntime().exec(new String[] { "cat", "/proc/uptime" });

        // Esperar a que termine el proceso y obtener su valor de salida
        process.waitFor(10, TimeUnit.SECONDS);
        int value = process.exitValue();
        if (value != 0) {
            throw new Exception(MessageFormat.format("Código de salida con error (%d)", value));
        }

        // Obtener la salida del proceso
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String result = br.lines().collect(Collectors.joining("\n"));
        br.close();
        
        // Obtener el tiempo desde el inicio del sistema 
        String seconds = result.split(" ")[0];
        System.out.printf("Segundos desde el inicio del sistema: %.2f", new BigDecimal(seconds));
    }
}
