package io.github.picodotdev.blogbitix.quarkus;

import io.vertx.reactivex.ext.web.RoutingContext;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class Resource {

    @Inject
    private Beans beans;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
}