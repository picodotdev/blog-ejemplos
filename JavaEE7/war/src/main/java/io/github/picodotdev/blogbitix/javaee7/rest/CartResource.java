package io.github.picodotdev.blogbitix.javaee7.rest;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Cart;
import io.github.picodotdev.blogbitix.javaee.jpa.NoStockException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("cart")
@RequestScoped
public class CartResource {

    @EJB
    private SupermarketLocal supermarket;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Cart get() {
        HttpSession session = request.getSession(false);
        return (session != null) ? (Cart) session.getAttribute("cart") : null;
    }

    @POST
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void post(Cart cart) throws NoStockException {
        HttpSession session = request.getSession();
        session.setAttribute("cart", cart);
    }
}