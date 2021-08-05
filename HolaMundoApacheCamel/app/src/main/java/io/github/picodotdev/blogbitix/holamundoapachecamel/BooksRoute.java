package io.github.picodotdev.blogbitix.holamundoapachecamel;

import java.math.BigDecimal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

@Component
public class BooksRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:misc/").routeId("books-file")
                .unmarshal().bindy(BindyType.Csv, Book.class)
                .split(body())
                .choice()
                .when(simple("${body.price} < 30")).to("direct:books-cheap")
                .otherwise().to("direct:books-expensive");

        from("direct:books-cheap").routeId("books-cheap").setHeader("type", constant("cheap")).to("direct:books-stream-out");
        from("direct:books-expensive").routeId("books-expensive").setHeader("type", constant("expensive")).to("direct:books-stream-out");

        from("direct:books-stream-out").routeId("books-stream-out")
                .filter(header("type").isEqualTo("cheap"))
                .process(new VatProcessor())
                .transform(simple("${body.title} at only ${body.price} €"))
                .to("stream:out");
    }

    private class VatProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            Book book = (Book) exchange.getMessage().getBody();
            BigDecimal priceWithVat = book.getPrice().multiply(new BigDecimal("1.04"));
            book.setPrice(priceWithVat);
        }
    }
}