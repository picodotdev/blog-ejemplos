package io.github.picodotdev.blogbitix.graphql.type;

import graphql.kickstart.tools.GraphQLResolver;

import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;

public class MagazineResolver implements GraphQLResolver<Magazine> {

    private LibraryRepository libraryRespository;

    public MagazineResolver(LibraryRepository libraryRespository) {
        this.libraryRespository = libraryRespository;
    }
}
