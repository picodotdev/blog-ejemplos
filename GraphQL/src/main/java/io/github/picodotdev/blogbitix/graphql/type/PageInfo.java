package io.github.picodotdev.blogbitix.graphql.type;

public class PageInfo {

    private String startCursor;
    private String endCursor;
    private boolean hasNextPage;

    public PageInfo(String startCursor, String endCursor, boolean hasNextPage) {
        this.startCursor = startCursor;
        this.endCursor = endCursor;
        this.hasNextPage = hasNextPage;
    }

    public String getStartCursor() {
        return startCursor;
    }

    public String getEndCursor() {
        return endCursor;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }
}
