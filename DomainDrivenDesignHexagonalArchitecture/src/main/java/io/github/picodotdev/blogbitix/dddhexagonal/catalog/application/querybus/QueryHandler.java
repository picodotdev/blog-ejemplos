package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.querybus;

public interface QueryHandler<T, U extends Query<T>> {

    T handle(U query) throws Exception;
}
