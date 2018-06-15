package io.github.picodotdev.blogbitix.microprofile;

import java.util.List;

import javax.ws.rs.*;

@Path("/")
public interface QuotesService {

    @GET
    @Path("/quotes")
    List<Quote> getQuotes();
}