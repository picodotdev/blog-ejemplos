package io.github.picodotdev.blogbitix.vertx.helloworld;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.TemplateEngine;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

public class Web extends AbstractVerticle {

	public static void main(String[] args) {
		Verticle verticle = new Web();
		Vertx.vertx().deployVerticle(verticle);
	}

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		TemplateEngine engine = ThymeleafTemplateEngine.create();

		router.route().handler(ctx -> {
			String name = ctx.request().getParam("name");
			ctx.put("name", name);
			ctx.put("welcome", (name == null) ? "¡Hola mundo!" : String.format("¡Hola %s!", name));

			ctx.next();
		});

		router.route().handler(ctx -> {
			engine.render(ctx, "templates/index.html", res -> {
				if (res.succeeded()) {
					ctx.response().putHeader("Content-Type", "text/html; charset=utf-8");
					ctx.response().end(res.result());
				} else {
					ctx.fail(res.cause());
				}
			});
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}
}
