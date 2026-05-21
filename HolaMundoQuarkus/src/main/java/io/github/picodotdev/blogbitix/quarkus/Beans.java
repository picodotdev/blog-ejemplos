package io.github.picodotdev.blogbitix.quarkus;

import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Beans {

    private ClassLoaderTemplateResolver templateResolver;
    private ThymeleafTemplateEngine templateEngine;
    private TemplateHandler templateHandler;

    @Inject
    public Beans(Vertx vertx) {
        this.templateResolver = new ClassLoaderTemplateResolver();
        this.templateResolver.setSuffix(".html");
        this.templateResolver.setTemplateMode("html");

        this.templateEngine = ThymeleafTemplateEngine.create(vertx);
        this.templateEngine.getThymeleafTemplateEngine().setTemplateResolver(this.templateResolver);

        this.templateHandler = new TemplateHandlerImpl(templateEngine, "/templates/", "text/html");
    }

    public ThymeleafTemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public TemplateHandler getTemplateHandler() {
        return templateHandler;
    }
}
