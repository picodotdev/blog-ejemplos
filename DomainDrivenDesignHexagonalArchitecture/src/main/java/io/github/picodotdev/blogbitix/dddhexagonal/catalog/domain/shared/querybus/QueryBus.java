package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.querybus;

public interface QueryBus {

    <T> T handle(Query<T> query) throws Exception;
}
