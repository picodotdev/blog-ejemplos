package io.github.picodotdev.blogbitix.graphql.type;

import graphql.relay.Relay;

public class CommentEdge {

    private Comment comment;
    private String cursor;

    public CommentEdge(Comment comment) {
        this.comment = comment;
        this.cursor = toGlobalId(comment);
    }

    public Comment getNode() {
        return comment;
    }

    public String getCursor() {
        return cursor;
    }

    public static String toGlobalId(Comment comment) {
        return new Relay().toGlobalId(Comment.class.getName(), comment.getId().toString());
    }

    public static Long fromGlobalId(String cursor) {
        String id = new Relay().fromGlobalId(cursor).getId();
        return Long.parseLong(id);
    }
}
