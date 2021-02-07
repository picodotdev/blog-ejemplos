package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.commandbus;

public interface CommandBus {

    void handle(Command command) throws Exception;
}