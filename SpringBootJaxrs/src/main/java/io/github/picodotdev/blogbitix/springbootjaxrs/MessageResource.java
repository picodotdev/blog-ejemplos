package io.github.picodotdev.blogbitix.springbootjaxrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Component
@Path("/message")
public class MessageResource {

    @Autowired
    private MessageService messageService;

    @GET
    @Produces("application/json")
    public Message message(@QueryParam("message") String message) {
        return messageService.create(message);
    }

}