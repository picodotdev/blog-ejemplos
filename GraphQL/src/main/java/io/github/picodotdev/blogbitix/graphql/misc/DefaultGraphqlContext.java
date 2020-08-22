package io.github.picodotdev.blogbitix.graphql.misc;

import graphql.kickstart.execution.context.GraphQLContext;
import org.dataloader.DataLoaderRegistry;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

public class DefaultGraphqlContext implements GraphQLContext {

    private graphql.GraphQLContext data;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private Session session;
    private HandshakeRequest handshakeRequest;

    private DataLoaderRegistry dataLoaderRegistry;

    public DefaultGraphqlContext(graphql.GraphQLContext data) {
        this.data = data;
    }

    public DefaultGraphqlContext(graphql.GraphQLContext data, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.data = data;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public DefaultGraphqlContext(graphql.GraphQLContext data, Session session, HandshakeRequest handshakeRequest) {
        this.data = data;
        this.session = session;
        this.handshakeRequest = handshakeRequest;
    }

    @Override
    public Optional<Subject> getSubject() {
        return Optional.empty();
    }

    @Override
    public Optional<DataLoaderRegistry> getDataLoaderRegistry() {
        return Optional.ofNullable(dataLoaderRegistry);
    }

    public void setDataLoaderRegistry(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    public graphql.GraphQLContext getData() {
        return data;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }
}