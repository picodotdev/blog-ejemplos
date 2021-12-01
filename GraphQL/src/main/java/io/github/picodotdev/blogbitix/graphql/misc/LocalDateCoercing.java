package io.github.picodotdev.blogbitix.graphql.misc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class LocalDateCoercing implements Coercing<LocalDate, String> {

    private DateTimeFormatter formatter;

    public LocalDateCoercing() {
        this(DateTimeFormatter.ISO_DATE);
    }

    public LocalDateCoercing(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String serialize(Object dataFetcherResult) {
        try {
            LocalDate object = (LocalDate) dataFetcherResult;
            if (object == null) {
                return null;
            }
            return object.format(formatter);
        } catch (Exception e) {
            throw new CoercingSerializeException(e);
        }
    }

    @Override
    public LocalDate parseValue(Object input) {
        return parse(input);
    }

    @Override
    public LocalDate parseLiteral(Object input) {
        return parse(input);
    }

    private LocalDate parse(Object input) {
        try {
            String string = (String) input;
            if (string == null) {
                return null;
            }
            return LocalDate.parse(string, formatter);
        } catch (Exception e) {
            throw new CoercingParseValueException(e);
        }
    }
}
