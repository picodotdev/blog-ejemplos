package io.github.picodotdev.blogbitix.eventbus.domain.shared.querybus;

public interface QueryBus {

    <T> T handle(Query<T> query) throws Exception;
}
