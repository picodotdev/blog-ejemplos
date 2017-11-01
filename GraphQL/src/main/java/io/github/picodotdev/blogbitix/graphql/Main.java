package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import org.apache.commons.io.IOUtils;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@ServletComponentScan
public class Main {

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

        GraphQLContextBuilder contextBuilder = new GraphQLContextBuilder() {
            @Override
            public GraphQLContext build(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
                String user = request.get().getHeader("User");
                return new AuthContext(user, request, response);
            }
        };

        return new ServletRegistrationBean(new SimpleGraphQLServlet(schema, new DefaultExecutionStrategyProvider(), null, null, null, null, contextBuilder, null), "/library");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
