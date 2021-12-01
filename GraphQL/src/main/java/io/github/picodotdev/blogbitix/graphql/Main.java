package io.github.picodotdev.blogbitix.graphql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import org.apache.commons.io.IOUtils;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.dataloader.MappedBatchLoaderWithContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.picodotdev.blogbitix.graphql.misc.DefaultGraphqlContext;
import io.github.picodotdev.blogbitix.graphql.misc.LocalDateCoercing;
import io.github.picodotdev.blogbitix.graphql.misc.LongCoercing;
import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.resolver.BookResolver;
import io.github.picodotdev.blogbitix.graphql.resolver.Mutation;
import io.github.picodotdev.blogbitix.graphql.resolver.Query;
import io.github.picodotdev.blogbitix.graphql.type.Magazine;
import io.github.picodotdev.blogbitix.graphql.type.MagazineResolver;

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
                .scalars(GraphQLScalarType.newScalar().name("Long").description("Long scalar").coercing(new LongCoercing()).build(), GraphQLScalarType.newScalar().name("LocalDate").description("LocalDate scalar").coercing(new LocalDateCoercing()).build())
                .dictionary(Magazine.class)
                .build()
                .makeExecutableSchema();
    }

    @Bean
    public GraphQLServletContextBuilder contextBuilder(List<MappedBatchLoaderWithContext<?, ?>> mappedBatchLoaders) {
        return new GraphQLServletContextBuilder() {
            @Override
            public GraphQLContext build(HttpServletRequest request, HttpServletResponse response) {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                DefaultGraphqlContext context = new DefaultGraphqlContext(data, request, response);
                context.setDataLoaderRegistry(buildDataLoaderRegistry(mappedBatchLoaders, context));
                return context;
            }

            @Override
            public GraphQLContext build(Session session, HandshakeRequest request) {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                DefaultGraphqlContext context = new DefaultGraphqlContext(data, session, request);
                context.setDataLoaderRegistry(buildDataLoaderRegistry(mappedBatchLoaders, context));
                return context;
            }

            @Override
            public GraphQLContext build() {
                graphql.GraphQLContext data = graphql.GraphQLContext.newContext().build();
                DefaultGraphqlContext context = new DefaultGraphqlContext(data);
                context.setDataLoaderRegistry(buildDataLoaderRegistry(mappedBatchLoaders, context));
                return context;
            }
        };
    }

    private DataLoaderRegistry buildDataLoaderRegistry(List<MappedBatchLoaderWithContext<?, ?>> mappedBatchLoaders, GraphQLContext context) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        for (MappedBatchLoaderWithContext<?, ?> loader : mappedBatchLoaders) {
            registry.register(loader.getClass().getSimpleName(),
                DataLoaderFactory.newMappedDataLoader(
                    loader,
                    DataLoaderOptions.newOptions().setBatchLoaderContextProvider(() -> context)
                )
            );
        }
        return registry;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
