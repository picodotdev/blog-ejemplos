package io.github.picodotdev.blogbitix.microprofile;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.Random;

@Path("randomQuote")
public class RandomQuoteResource {

    private QuotesService service;

    @Inject
    @Metric(absolute = true)
    Counter counter;

    public RandomQuoteResource() {
        try {
            service = RestClientBuilder.newBuilder()
                .baseUrl(new URL("https://localhost:8443/Microprofile"))
                .build(QuotesService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        counter.inc();
        List<Quote> quotes = getQuotes();
        if (quotes.isEmpty()) {
            return null;
        }
        return Response.ok(toJson(quotes)).build();
    }

    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio=0.75, delay = 5000, successThreshold = 2)
    private List<Quote> getQuotes() {
        return service.getQuotes();
    }

    private String toJson(List<Quote> quotes) {
        JsonbConfig config = new JsonbConfig().withAdapters(new QuoteAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(quotes);
    }
}