package io.github.picodotdev.blogbitix.springbootjaxrsoauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageResource {

    @Autowired
    private MessageService messageService;

    @GetMapping(produces = "application/json")
    public Message message(@RequestParam("string") String string) {
        return messageService.create(string);
    }

}