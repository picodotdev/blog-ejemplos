package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@ServletComponentScan
public class Main {

    public static final Logger log = LoggerFactory.getLogger(DefaultGraphQLErrorHandler.class);

    @Bean
    public LibraryRepository buildLibraryRepository() {
        return new LibraryRepository();
    }

    @Bean
    public ServletRegistrationBean graphQLServletRegistrationBean(LibraryRepository libraryRepository) throws Exception {
        GraphQLSchema schema = SchemaParser.newParser()
                .schemaString(IOUtils.resourceToString("/library.graphqls", Charset.forName("UTF-8")))
                .resolvers(new Query(libraryRepository), new Mutation(libraryRepository))
                .build()
                .makeExecutableSchema();

        GraphQLErrorHandler errorHandler = new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                serverErrors.forEach(error -> {
                    log.error("Error executing query ({}): {}", error.getClass().getSimpleName(), error.getMessage());
                });

                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
                return errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };

        GraphQLContextBuilder contextBuilder = new GraphQLContextBuilder() {
            @Override
            public GraphQLContext build(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
                String user = request.get().getHeader("User");
                return new AuthContext(user, request, response);
            }
        };

        return new ServletRegistrationBean(new SimpleGraphQLServlet(schema, new DefaultExecutionStrategyProvider(), null, null, null, errorHandler, contextBuilder, null), "/library");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
