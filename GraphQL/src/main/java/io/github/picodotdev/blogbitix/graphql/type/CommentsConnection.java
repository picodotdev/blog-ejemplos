package io.github.picodotdev.blogbitix.graphql.type;

import io.github.picodotdev.blogbitix.graphql.type.CommentEdge;

import java.util.List;
import java.util.stream.Collectors;

public class CommentsConnection {

    private List<CommentEdge> edges;
    private PageInfo pageInfo;

    public CommentsConnection(List<Comment> edges, PageInfo pageInfo) {
        this.edges = edges.stream().map(o -> new CommentEdge(o)).collect(Collectors.toList());
        this.pageInfo = pageInfo;
    }

    public List<CommentEdge> getEdges() {
        return edges;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }
}
