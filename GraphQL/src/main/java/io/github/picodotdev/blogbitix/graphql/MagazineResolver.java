package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.execution.batched.Batched;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MagazineResolver implements GraphQLResolver<Magazine> {

    private LibraryRepository libraryRespository;

    public MagazineResolver(LibraryRepository libraryRespository) {
        this.libraryRespository = libraryRespository;
    }
}
