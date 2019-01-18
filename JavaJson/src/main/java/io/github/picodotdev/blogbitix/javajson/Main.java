package io.github.picodotdev.blogbitix.javajson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.Jsonb;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.StringReader;

public class Main {

    public static void main(String[] args) throws Exception {
        Comprador comprador = buildComprador();
        String json = "";

        // JSON-P
        JsonObject jsonp = Json.createObjectBuilder()
            .add("name", comprador.getNombre())
            .add("edad", comprador.getEdad())
            .add("direcciones", Json.createArrayBuilder().add(
                Json.createObjectBuilder()
                    .add("calle", comprador.getDirecciones().get(0).getCalle())
                    .add("ciudad", comprador.getDirecciones().get(0).getCiudad())
                    .add("codigoPostal", comprador.getDirecciones().get(0).getCodigoPostal())
                    .add("pais", comprador.getDirecciones().get(0).getPais())
                    .build())
                .add(Json.createObjectBuilder()
                    .add("calle", comprador.getDirecciones().get(1).getCalle())
                    .add("ciudad", comprador.getDirecciones().get(1).getCiudad())
                    .add("codigoPostal", comprador.getDirecciones().get(1).getCodigoPostal())
                    .add("pais", comprador.getDirecciones().get(1).getPais()))
                    .build()
            ).build();
        json = jsonp.toString();
        jsonp = Json.createReader(new StringReader(json)).readObject();
        System.out.printf("JSON-P: %s%n", json);
        System.out.printf("JSON-P (JsonObject): %s%n", jsonp.toString());

        JsonPatch jsonPatch = Json.createPatchBuilder().add("/telefono", "111111111").remove("/direcciones/0").build();
        jsonp = jsonPatch.apply(jsonp);
        System.out.printf("JSON-P (JsonPatch): %s%n", jsonPatch.toString());
        System.out.printf("JSON-P (JsonObject): %s%n", jsonp.toString());

        // JSON-B
        Jsonb jsonb = JsonbBuilder.create();
        json = jsonb.toJson(comprador);
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
