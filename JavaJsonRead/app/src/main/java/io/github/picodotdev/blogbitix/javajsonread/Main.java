package io.github.picodotdev.blogbitix.javajsonread;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {
        {
            System.out.println("# Jackson");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(Main.class.getResourceAsStream("/data.json"));

            String city1 = root.get("city").asString();
            String city2 = root.at("/city").asString();
            System.out.println("City 1: " + city1);
            System.out.println("City 2: " + city2);

            root.propertyStream().filter(p -> p.getValue().isValueNode()).forEach(p -> System.out.println(p.getKey() + " = " + p.getValue().asString()));

            System.out.println();

            root.at("/other").properties().forEach(p -> System.out.println(p.getKey() + " = " + p.getValue().asString()));
        }

        System.out.println();

        {
            System.out.println("# Jackson (map)");
            ObjectMapper mapper = new ObjectMapper();
            InputStream stream = Main.class.getResourceAsStream("/data.json");
            Map<String, Object> map = mapper.readValue(stream, new TypeReference<>() {});

            System.out.println(map);

            System.out.println();

            System.out.println("City 1: " + map.get("city"));

            map.entrySet().stream().filter(e -> !(e.getValue() instanceof Map)).forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

            System.out.println();

            ((Map<String, Object>) map.get("other")).forEach((key, value) -> System.out.println(key + " = " + value));
        }

        System.out.println();

        {
            System.out.println("# Jakarta JSON");
            JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/data.json"));
            JsonStructure root = reader.read();

            JsonValue city1 = root.asJsonObject().get("city");
            JsonValue city2 = Json.createPointer("/city").getValue(root);
            System.out.println("City: " + city1);
            System.out.println("City: " + city2);

            root.asJsonObject().entrySet().stream()
                .filter(e -> List.of(ValueType.NULL, ValueType.STRING, ValueType.NUMBER, ValueType.TRUE, ValueType.FALSE).contains(e.getValue().getValueType()))
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue().toString()));

            System.out.println();

            Json.createPointer("/other").getValue(root).asJsonObject().forEach((key, value) -> System.out.println(key + " = " + value.toString()));
        }
    }
}
