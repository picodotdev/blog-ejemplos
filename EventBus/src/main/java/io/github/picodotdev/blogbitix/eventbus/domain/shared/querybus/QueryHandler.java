package io.github.picodotdev.blogbitix.eventbus.domain.shared.querybus;

public interface QueryHandler<T,U> {

    T handle(U query) throws Exception;
}
