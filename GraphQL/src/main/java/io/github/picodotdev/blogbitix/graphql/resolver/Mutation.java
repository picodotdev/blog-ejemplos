package io.github.picodotdev.blogbitix.graphql.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.context.GraphQLServletContext;
import io.github.picodotdev.blogbitix.graphql.misc.DefaultGraphQLContext;
import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.type.Book;

public class Mutation implements GraphQLMutationResolver {

    private LibraryRepository libraryRepository;

    public Mutation(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Book addBook(String title, Long author, DataFetchingEnvironment env) throws Exception {
        DefaultGraphQLContext context = (DefaultGraphQLContext) env.getContext();
        return libraryRepository.addBook(title, author, context.getHttpServletRequest().getHeader("User"));
    }
}