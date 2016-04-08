package io.github.picodotdev.blogbitix.javaee7.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;

@Path("products")
@RequestScoped
public class ProductsResource {

    @EJB
    private SupermarketLocal supermarket;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Product> list() {
        return supermarket.findProducts();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON  })
    public Product get(@PathParam("id") Long id) {
        return supermarket.findProduct(id);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON  })
    public void post(Product product) {
        supermarket.persistsProduct(product);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON  })
    public void put(Product product) {
        supermarket.persistsProduct(product);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        Product product = supermarket.findProduct(id);
        supermarket.deleteProduct(product);
    }
}