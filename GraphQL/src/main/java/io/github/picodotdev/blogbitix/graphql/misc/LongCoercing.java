package io.github.picodotdev.blogbitix.graphql.misc;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LongCoercing implements Coercing<Long, String> {

    public LongCoercing() {
    }

    @Override
    public String serialize(Object dataFetcherResult) {
        try {
            Long object = (Long) dataFetcherResult;
            if (object == null) {
                return null;
            }
            return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CoercingSerializeException(e);
        }
    }

    @Override
    public Long parseValue(Object input) {
        return parse(input);
    }

    @Override
    public Long parseLiteral(Object input) {
        return parse(input);
    }

    private Long parse(Object input) {
        try {
            String string = null;
            if (input instanceof String) {
                string = (String) input;
            } else if (input instanceof StringValue) {
                string = ((StringValue) input).getValue();
            }
            if (string == null) {
                return null;
            }
            return Long.parseLong(string);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CoercingParseValueException(e);
        }
    }
}
