package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.execution.batched.Batched;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookResolver implements GraphQLResolver<Book> {

    private LibraryRepository libraryRespository;

    public BookResolver(LibraryRepository libraryRespository) {
        this.libraryRespository = libraryRespository;
    }

    public String getIsbn(Book book) throws InterruptedException {
        return UUID.randomUUID().toString();
    }

    @Batched
    public List<String> getBatchedIsbn(List<Book> books) throws InterruptedException {
        return books.stream().map(b -> UUID.randomUUID().toString()).collect(Collectors.toList());
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

    @Batched
    public List<CommentsConnection> getBatchedComments(List<Book> books, List<String> after, List<Long> limit) {
        List<CommentsConnection> ccs = new ArrayList<>();
        for (int i = 0; i < books.size(); ++i) {
            CommentsConnection cc = getComments(books.get(i), null, null);
            ccs.add(cc);
        }
        return ccs;
    }
}
