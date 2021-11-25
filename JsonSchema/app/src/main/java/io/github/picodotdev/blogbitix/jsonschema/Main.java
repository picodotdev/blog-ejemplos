package io.github.picodotdev.blogbitix.jsonschema;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, String> urlMappings = Map.of("https://picodotdev.github.io/blog-bitix/misc/json/product.schema.json", "resource:/product.schema.json",
                "https://picodotdev.github.io/blog-bitix/misc/json/geographical-location.schema.json", "resource:/geographical-location.schema.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaFactory factory = JsonSchemaFactory.builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909))
                .objectMapper(mapper)
                .addUriMappings(urlMappings)
                .build();

        JsonSchema schema = factory.getSchema(URI.create("resource:/product.schema.json"));

        {
            JsonNode json = mapper.readTree(Main.class.getResourceAsStream("/product.json"));
            Set<ValidationMessage> errors = schema.validate(json);
            System.out.printf("Valid JSON errors: %d5n", errors.size());
        }

        {
            JsonNode json = mapper.readTree(Main.class.getResourceAsStream("/product-invalid.json"));
            Set<ValidationMessage> errors = schema.validate(json);
            System.out.printf("Valid JSON errors: %d%n", errors.size());
            errors.stream().forEach(it -> {
                System.out.printf("Type: %s%n", it.getType());
                System.out.printf("Message: %s%n", it.getMessage());
            });
        }
    }
}
