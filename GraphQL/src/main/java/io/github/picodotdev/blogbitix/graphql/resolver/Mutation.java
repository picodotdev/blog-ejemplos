package io.github.picodotdev.blogbitix.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import io.github.picodotdev.blogbitix.graphql.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.misc.AuthContext;
import io.github.picodotdev.blogbitix.graphql.type.Book;

public class Mutation implements GraphQLMutationResolver {

    private LibraryRepository libraryRepository;

    public Mutation(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Book addBook(String title, Long author, DataFetchingEnvironment env) throws Exception {
        //String user = request.getHeader("User");
        return libraryRepository.addBook(title, author, env.<AuthContext>getContext());
    }
}