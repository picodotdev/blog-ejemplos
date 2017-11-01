package io.github.picodotdev.blogbitix.graphql;

import graphql.servlet.GraphQLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthContext extends GraphQLContext {

    private String user;
    private Optional<HttpServletRequest> request;
    private Optional<HttpServletResponse> response;

    public AuthContext(String user, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        super(request, response);
        this.user = user;
        this.request = request;
        this.response = response;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Optional<HttpServletRequest> getRequest() {
        return request;
    }

    public void setRequest(Optional<HttpServletRequest> request) {
        this.request = request;
    }

    public Optional<HttpServletResponse> getResponse() {
        return response;
    }

    public void setResponse(Optional<HttpServletResponse> response) {
        this.response = response;
    }
}
