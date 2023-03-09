package io.github.picdodotdev.blogbitix.springrestclients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class DefaultController {

    @GetMapping(value = {"", "/", "/{name}"})
    public String message(@PathVariable(value = "name", required = false) String name) {
        return (name == null || name.isBlank()) ? "Hello World!" : String.format("Hello %s!", name);
    }
}
