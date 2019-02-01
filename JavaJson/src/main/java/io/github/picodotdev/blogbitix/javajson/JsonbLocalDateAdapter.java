package io.github.picodotdev.blogbitix.javajson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonbLocalDateAdapter implements JsonbAdapter<LocalDate, JsonString> {

    @Override
    public JsonString adaptToJson(LocalDate obj) throws Exception {
        return Json.createValue(obj.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Override
    public LocalDate adaptFromJson(JsonString obj) throws Exception {
        return LocalDate.parse(obj.getString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
