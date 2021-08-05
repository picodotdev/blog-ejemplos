package io.github.picodotdev.blogbitix.holamundoapachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:helloworld").routeId("helloworld").to("stream:out");
    }
}