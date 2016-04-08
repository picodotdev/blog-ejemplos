package io.github.picodotdev.blogbitix.javaee7.rest;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Cart;
import io.github.picodotdev.blogbitix.javaee.jpa.NoStockException;
import io.github.picodotdev.blogbitix.javaee.jpa.Purchase;
import io.github.picodotdev.blogbitix.javaee.jpa.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("purchases")
@RequestScoped
public class PurchasesResource {

    @EJB
    private SupermarketLocal supermarket;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @RolesAllowed({ "buyer" })
    public List<Purchase> list() {
        return supermarket.findPurchases();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    @RolesAllowed({ "buyer" })
    public Purchase get(@PathParam("id") Long id) {
        return supermarket.findPurchase(id);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @RolesAllowed({ "buyer" })
    public Purchase post(Cart cart) throws NoStockException {
        User user = supermarket.findUser(securityContext.getUserPrincipal().getName());
        return supermarket.buy(cart, user);
    }
}