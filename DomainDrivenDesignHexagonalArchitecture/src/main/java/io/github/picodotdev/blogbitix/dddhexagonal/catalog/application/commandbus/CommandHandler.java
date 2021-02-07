package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.commandbus;

public interface CommandHandler<T extends Command> {

    void handle(T command) throws Exception;
}