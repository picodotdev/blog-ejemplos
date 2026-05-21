package io.github.picodotdev.blogbitix.microprofile;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import java.time.format.DateTimeFormatter;

public class QuoteAdapter implements JsonbAdapter<Quote, JsonObject> {

    @Override
    public JsonObject adaptToJson(Quote quote) throws Exception {
        return Json.createObjectBuilder()
            .add("uuid", quote.getUuid().toString())
            .add("date", quote.getDate().format(DateTimeFormatter.ISO_DATE_TIME))
            .add("text", quote.getText())
            .build();
    }

    @Override
    public Quote adaptFromJson(JsonObject adapted) throws Exception {
        return new Quote(adapted.getString("text"));
    }
}