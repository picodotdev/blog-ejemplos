package io.github.picodotdev.blogbitix.javaee7.rest;

import io.github.picodotdev.blogbitix.javaee.jpa.NoStockException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoStockExceptionHandler implements ExceptionMapper<NoStockException> {

    @Override
    public Response toResponse(NoStockException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
    }
}