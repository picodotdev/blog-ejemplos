package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.querybus;

public interface QueryHandler<T, U extends Query<T>> {

    T handle(U query) throws Exception;
}
