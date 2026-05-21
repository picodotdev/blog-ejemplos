package io.github.picodotdev.blogbitix.dockerswarm;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/message")
public class MessageResource {

    @GET
    @Produces("application/json")
    public Message message() {
        return new Message("Hola mundo!");
    }
}
