package io.github.picodotdev.blogbitix.graphql.misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthContext {

    private String user;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public AuthContext(String user, HttpServletRequest request, HttpServletResponse response) {
        this.user = user;
        this.request = request;
        this.response = response;
    }

    public String getUser() {
        return user;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
