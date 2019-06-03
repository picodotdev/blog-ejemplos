package io.github.picodotdev.blogbitix.graphql.type;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.execution.batched.Batched;
import io.github.picodotdev.blogbitix.graphql.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.type.Magazine;

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
