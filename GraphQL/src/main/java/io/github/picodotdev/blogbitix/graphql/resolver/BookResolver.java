package io.github.picodotdev.blogbitix.graphql.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import io.github.picodotdev.blogbitix.graphql.dataloader.IsbnDataLoader;
import io.github.picodotdev.blogbitix.graphql.misc.DefaultGraphqlContext;
import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.type.Book;
import io.github.picodotdev.blogbitix.graphql.type.Comment;
import io.github.picodotdev.blogbitix.graphql.type.CommentEdge;
import io.github.picodotdev.blogbitix.graphql.type.CommentsConnection;
import io.github.picodotdev.blogbitix.graphql.type.PageInfo;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookResolver implements GraphQLResolver<Book> {

    private LibraryRepository libraryRespository;

    public BookResolver(LibraryRepository libraryRespository) {
        this.libraryRespository = libraryRespository;
    }

    public String getIsbn(Book book) {
        System.out.printf("Getting ISBN %d...%n", book.getId());
        //try { Thread.sleep(3000); } catch (Exception e) {}
        return book.getIsbn();
    }

    public String getBatchedIsbn(Book book, DataFetchingEnvironment environment) throws InterruptedException {
        DefaultGraphqlContext context = environment.getContext();
        Map<Long, String> isbns = (Map<Long, String>) context.getData().get("batchedIsbn");
        return isbns.get(book.getId());
    }

    public CompletableFuture<String> getDataLoaderIsbn(Book book, DataFetchingEnvironment environment) throws InterruptedException {
        DataLoader<Book, String> dataLoader = environment.getDataLoader(IsbnDataLoader.class.getSimpleName());
        return dataLoader.load(book);

    }

    public CommentsConnection getComments(Book book, String after, Long limit) {
        Long idAfter = null;
        Long limitPlusOne = null;

        if (after != null) {
            idAfter = CommentEdge.fromGlobalId(after);
        }
        if (limit != null && limit < Long.MAX_VALUE) {
            limitPlusOne = limit + 1;
        }

        List<Comment> commentsPlusOne = libraryRespository.findComments(book.getId(), idAfter, limitPlusOne);
        Stream<Comment> stream = commentsPlusOne.stream();
        if (limit != null) {
            stream = stream.limit(limit);
        }
        List<Comment> comments = stream.collect(Collectors.toList());

        Comment firstComment = (!comments.isEmpty()) ? comments.get(0) : null;
        Comment lastComment = (!comments.isEmpty()) ? comments.get(comments.size() - 1) : null;

        String startCursor = (firstComment != null) ? CommentEdge.toGlobalId(firstComment) : null;
        String endCursor = (lastComment != null) ? CommentEdge.toGlobalId(lastComment) : null;

        boolean hasNextPage = commentsPlusOne.size() > comments.size();

        return new CommentsConnection(comments, new PageInfo(startCursor, endCursor, hasNextPage));
    }

    public CommentsConnection getBatchedComments(Book book, String after, Long limit, DataFetchingEnvironment environment) {
        DefaultGraphqlContext context = environment.getContext();
        Map<Long, CommentsConnection> batchedComments = (Map<Long, CommentsConnection>) context.getData().get("batchedComments");
        return batchedComments.get(book.getId());
    }
}
