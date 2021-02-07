package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.querybus;

public interface QueryBus {

    <T> T handle(Query<T> query) throws Exception;
}
