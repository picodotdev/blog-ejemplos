package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLContextBuilder;
import graphql.servlet.GraphQLErrorHandler;
import io.github.picodotdev.blogbitix.graphql.misc.DefaultGraphQLContext;
import io.github.picodotdev.blogbitix.graphql.misc.GraphQLErrorAdapter;
import io.github.picodotdev.blogbitix.graphql.misc.LocalDateCoercing;
import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.resolver.BookResolver;
import io.github.picodotdev.blogbitix.graphql.resolver.Mutation;
import io.github.picodotdev.blogbitix.graphql.resolver.Query;
import io.github.picodotdev.blogbitix.graphql.type.Magazine;
import io.github.picodotdev.blogbitix.graphql.type.MagazineResolver;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Bean
    public LibraryRepository buildLibraryRepository() {
        return new LibraryRepository();
    }

    @Bean
    public GraphQLSchema graphQLSchema(LibraryRepository libraryRepository) throws IOException {
        return SchemaParser.newParser()
                .schemaString(IOUtils.resourceToString("/library.graphqls", Charset.forName("UTF-8")))
                .resolvers(new Query(libraryRepository), new Mutation(libraryRepository), new BookResolver(libraryRepository), new MagazineResolver(libraryRepository))
                .scalars(GraphQLScalarType.newScalar().name("LocalDate").description("LocalDate scalar").coercing(new LocalDateCoercing()).build())
                .dictionary(Magazine.class)
                .build()
                .makeExecutableSchema();
    }

//    @Bean
//    public ExecutionStrategy batchedExecutionStrategy() {
//        return new BatchedExecutionStrategy();
//    }

    @Bean
    public GraphQLErrorHandler graphQLErrorHandler() {
        return new GraphQLErrorHandler() {
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
                    logger.error("Error executing query ({}): {}", error.getClass().getSimpleName(), error.getMessage());
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
    }

    @Bean
    public GraphQLContextBuilder contextBuilder() {
        return new GraphQLContextBuilder() {
            @Override
            public GraphQLContext build(HttpServletRequest request, HttpServletResponse response) {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                GraphQLContext context = new DefaultGraphQLContext(data, request, response);
                return context;
            }

            @Override
            public GraphQLContext build(Session session, HandshakeRequest request) {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                GraphQLContext context = new DefaultGraphQLContext(data, session, request);
                return context;
            }

            @Override
            public GraphQLContext build() {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                GraphQLContext context = new DefaultGraphQLContext(data);
                return context;
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
