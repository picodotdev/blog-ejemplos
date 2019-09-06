package io.github.picodotdev.blogbitix.graphql.dataloader;

import io.github.picodotdev.blogbitix.graphql.type.Book;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IsbnDataLoader implements MappedBatchLoaderWithContext<Book, String> {

   public IsbnDataLoader() {
   }

   @Override
   public CompletionStage<Map<Book, String>> load(Set<Book> books, BatchLoaderEnvironment environment) {
       System.out.printf("Getting ISBN %s...%n", books.stream().map(Book::getId).collect(Collectors.toList()));
       Map<Book, String> isbns = books.stream().collect(Collectors.toMap(
           Function.identity(),
           Book::getIsbn
       ));
       return CompletableFuture.supplyAsync(() -> isbns);
   }
}
