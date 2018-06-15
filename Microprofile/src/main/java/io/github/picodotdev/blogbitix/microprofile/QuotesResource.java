package io.github.picodotdev.blogbitix.microprofile;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("quotes")
public class QuotesResource {

    private static final String QUOTES_KEY = "quotes";

    @Inject
    @Metric(absolute = true)
    Counter counter;

    @Context
    private HttpServletRequest request;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        counter.inc();
        if (counter.getCount() % 20 > 10) {
            return Response.serverError().build();
        }
        List<Quote> quotes = getQuotes();
        return Response.ok(toJson(quotes)).build();
    }

    @POST
    public void post(@FormParam("text") @DefaultValue("Hello World!") String text) {
        counter.inc();

        Quote quote = new Quote(text);

        List<Quote> quotes = getQuotes();
        quotes.add(quote);
        setQuotes(quotes);
    }

   @SuppressWarnings("unchecked")
    private List<Quote> getQuotes() {
        List<Quote> quotes = (List<Quote>) request.getSession().getAttribute(QUOTES_KEY);
        if (quotes == null) {
            quotes = new ArrayList<>();
            quotes.add(getDefaultQuote());
        }
        return quotes;
    }

    private void setQuotes(List<Quote> quotes) {
        request.getSession().setAttribute(QUOTES_KEY, quotes);
    }

    private String toJson(List<Quote> quotes) {
        JsonbConfig config = new JsonbConfig().withAdapters(new QuoteAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(quotes, new ArrayList<Quote>(){}.getClass().getGenericSuperclass());
    }

    private Quote getDefaultQuote() {
        String text = ConfigProvider.getConfig().getOptionalValue("defaultQuote", String.class).orElse("Hello World!");
        return new Quote(text);
    }
}