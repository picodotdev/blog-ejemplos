package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.commandbus;

public interface CommandBus {

    void handle(Command command) throws Exception;
}