package io.github.picdodotdev.blogbitix.springrestclients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface MessageService {

    @GetExchange("/message/")
    String message();

    @GetExchange("/message/{name}")
    String message(@PathVariable("name") String name);
}
