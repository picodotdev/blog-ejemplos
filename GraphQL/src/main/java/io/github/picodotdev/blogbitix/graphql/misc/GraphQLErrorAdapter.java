package io.github.picodotdev.blogbitix.graphql.misc;

import graphql.ErrorClassification;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphQLErrorAdapter implements GraphQLError {

    private GraphQLError error;

    public GraphQLErrorAdapter(GraphQLError error) {
        this.error = error;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> extensions = new HashMap<>();
        if (error.getExtensions() != null) {
            extensions.putAll(error.getExtensions());
        }
        if (error instanceof ExceptionWhileDataFetching) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            Throwable exception = ((ExceptionWhileDataFetching) error).getException();
            exception.printStackTrace(pw);
            String stacktrace = sw.toString();
            extensions.put("stacktrace", stacktrace);
            extensions.put("exception", exception.getClass().getName());
        }
        return (extensions.isEmpty()) ? null : extensions;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return error.getLocations();
    }

    @Override
    public ErrorClassification getErrorType() {
        return error.getErrorType();
    }

    @Override
    public List<Object> getPath() {
        return error.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return error.toSpecification();
    }

    @Override
    public String getMessage() {
        return (error instanceof ExceptionWhileDataFetching) ? ((ExceptionWhileDataFetching) error).getException().getMessage() : error.getMessage();
    }
}
