package io.github.picodotdev.blogbitix.graphql.misc;

import java.util.Map;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.language.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class ExceptionHandlers {

    public enum DefaultErrorClassification implements ErrorClassification {
        ServerError
    }

    @ExceptionHandler(RuntimeException.class)
    public GraphQLError exceptionHandler(RuntimeException exception) {
        Throwable e = exception.getCause();
        if (e instanceof PermissionException) {
            return exceptionHandler((PermissionException) e);
        } else if (e instanceof ValidationException) {
            return exceptionHandler((ValidationException) e);
        } else {
            return GraphqlErrorBuilder.newError().message("Internal Server Error(s) while executing query").build();
        }
    }

    @ExceptionHandler(PermissionException.class)
    public GraphQLError exceptionHandler(PermissionException exception) {
        return GraphqlErrorBuilder.newError().message(exception.getMessage()).errorType(DefaultErrorClassification.ServerError).extensions(Map.of("source", toSourceLocation(exception), "foo", "bar", "fizz", "whizz")).build();
    }

    @ExceptionHandler(ValidationException.class)
    public GraphQLError exceptionHandler(ValidationException exception) {
        return GraphqlErrorBuilder.newError().message(exception.getMessage()).errorType(DefaultErrorClassification.ServerError).extensions(Map.of("source", toSourceLocation(exception))).build();
    }

    private SourceLocation toSourceLocation(Throwable t) {
        if (t.getStackTrace().length == 0) {
            return null;
        }
        StackTraceElement st = t.getStackTrace()[0];
        return new SourceLocation(st.getLineNumber(), -1, st.toString());
    }
}
