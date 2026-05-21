package io.github.picodotdev.blogbitix.quarkus;

import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Routes {

    @Inject
    private Beans beans;

    @Route(path = "/index", methods = HttpMethod.GET)
    public void index(RoutingContext context) {
        String name = context.request().getParam("name");
        if (name == null) {
            name = "world";
        }
        beans.getTemplateHandler().handle(context);
    }
}
