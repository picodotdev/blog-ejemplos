package io.github.picodotdev.blogbitix.javajson;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Comprador comprador = buildComprador();
        List<Comprador> compradores = List.of(buildComprador(), buildComprador());

        String json = "";
        String arrayJson = "";

        // JSON-P
        JsonObject jsonp = Json.createObjectBuilder()
            .add("nombre", comprador.getNombre())
            .add("fechaNacimiento", comprador.getFechaNacimiento().toString())
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
        JsonArray arrayJsonp = Json.createArrayBuilder().add(jsonp).add(jsonp).build();

        json = jsonp.toString();
        arrayJson = arrayJsonp.toString();
        jsonp = Json.createReader(new StringReader(json)).readObject();
        arrayJsonp = Json.createReader(new StringReader(arrayJson)).readArray();

        System.out.printf("JSON-P: %s%n", json);
        System.out.printf("JSON-P (JsonObject): %s%n", jsonp.toString());
        System.out.printf("JSON-P (JsonArray): %s%n", arrayJson.toString());

        JsonPatch jsonPatch = Json.createPatchBuilder().add("/telefono", "111111111").remove("/direcciones/0").build();
        jsonp = jsonPatch.apply(jsonp);
        System.out.printf("JSON-P (JsonPatch): %s%n", jsonPatch.toString());
        System.out.printf("JSON-P (JsonObject): %s%n", jsonp.toString());

        // JSON-B
        JsonbConfig config = new JsonbConfig().withAdapters(new JsonbLocalDateAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);

        json = jsonb.toJson(comprador);
        comprador = jsonb.fromJson(json, Comprador.class);
        compradores = jsonb.fromJson(arrayJson, new ArrayList<Comprador>(){}.getClass().getGenericSuperclass());
        System.out.printf("JSON-B: %s%n", json);
        System.out.printf("JSON-B (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getFechaNacimiento(), comprador.getDirecciones().size());

        // Gson
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new GsonLocalDateTypeAdapter());
        Gson gson = builder.create();;

        json = gson.toJson(comprador);
        comprador = gson.fromJson(json, Comprador.class);
        compradores = gson.fromJson(arrayJson, new TypeToken<List<Comprador>>(){}.getType());
        System.out.printf("Gson: %s%n", json);
        System.out.printf("Gson (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getFechaNacimiento(), comprador.getDirecciones().size());

        // Jackson
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new JacksonLocalDateSerializer());
        module.addDeserializer(LocalDate.class, new JacksonLocalDateDeserializer());
        mapper.registerModule(module);

        json = mapper.writeValueAsString(comprador);
        comprador = mapper.readValue(json, Comprador.class);
        compradores = mapper.readValue(arrayJson, new TypeReference<List<Comprador>>(){});
        System.out.printf("Jackson: %s%n", json);
        System.out.printf("Jackson (comprador): %s, %s, %d%n", comprador.getNombre(), comprador.getFechaNacimiento(), comprador.getDirecciones().size());

        // JsonPath
        BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/store.json")));
        String storeJson = br.lines().collect(Collectors.joining());
        br.close();

        ReadContext readContext = JsonPath.parse(storeJson);

        Map<String, String> expressions = new LinkedHashMap<>();
        expressions.put("authors", "$.store.book[*].author");
        expressions.put("books", "$.store.book[*]");
        expressions.put("cheap-books", "$.store.book[?(@.price < 10)]");
        expressions.put("isbn-books", "$.store.book[?(@.isbn)]");
        expressions.put("first-books", "$.store.book[:2]");
        expressions.put("prices", "$..price");

        expressions.forEach((key, expression) -> {
            Object value = readContext.read(expression);
            System.out.printf("%s: %s%n", key, value);
        });
    }

    private static Comprador buildComprador() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan");
        comprador.setFechaNacimiento(LocalDate.now());
        comprador.getDirecciones().add(buildDireccion());
        comprador.getDirecciones().add(buildDireccion());
        return comprador;
    }

    private static Direccion buildDireccion() {
        return new Direccion("calle", "ciudad", "codigoPostal", "pais");
    }
}
