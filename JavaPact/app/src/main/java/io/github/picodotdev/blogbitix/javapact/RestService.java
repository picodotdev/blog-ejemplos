package io.github.picodotdev.blogbitix.javapact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    private static final Map<String, String> MESSAGES;

    static {
        MESSAGES = new HashMap<>();
        MESSAGES.put("es-ES;default", "¡Hola mundo!");
        MESSAGES.put("es-ES;hello", "¡Hola %s!");
        MESSAGES.put("en-GB;default", "Hello World!");
        MESSAGES.put("en-GB;hello", "Hello %s!");
    }

    @GetMapping(path = { "/message", "/message/{name}" })
    public String message(@RequestHeader(value = "Accept-Language", defaultValue = "en-GB") String locale, @PathVariable(required = false) String name, @RequestParam(name = "random", required = false) String random) {
        System.out.printf("Random: %s%n", random);
        String message = "";
        if (name == null || name.isBlank()) {
            String key = String.format("%s;default", locale);
            message = MESSAGES.getOrDefault(key, MESSAGES.get("en-GB;default"));
        } else {
            String key = String.format("%s;hello", locale);
            String value = MESSAGES.getOrDefault(key, MESSAGES.get("en-GB;default"));
            message = String.format(value, name);
        }
        return message;
    }
}
