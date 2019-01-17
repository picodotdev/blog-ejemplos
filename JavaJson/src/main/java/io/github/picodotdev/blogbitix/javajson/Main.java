package io.github.picodotdev.blogbitix.javajson;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.Jsonb;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        Comprador comprador = buildComprador();

        // JSON-B
        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(comprador);
        comprador = jsonb.fromJson(json, Comprador.class);
        System.out.printf("JSON-B: %s%n", json);
        System.out.printf("JSON-B (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getEdad(), comprador.getDirecciones().size());

        // Gson
        Gson gson = new Gson();
        json = gson.toJson(comprador);
        comprador = gson.fromJson(json, Comprador.class);
        System.out.printf("Gson: %s%n", json);
        System.out.printf("Gson (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getEdad(), comprador.getDirecciones().size());

        // Jackson
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(comprador);
        comprador = mapper.readValue(json, Comprador.class);
        System.out.printf("Jackson: %s%n", json);
        System.out.printf("Jackson (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getEdad(), comprador.getDirecciones().size());
    }

    private static Comprador buildComprador() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan");
        comprador.setEdad(30);
        comprador.getDirecciones().add(buildDireccion());
        comprador.getDirecciones().add(buildDireccion());
        return comprador;
    }

    private static Direccion buildDireccion() {
        return new Direccion("calle", "ciudad", "codigoPostal", "pais");
    }
}
