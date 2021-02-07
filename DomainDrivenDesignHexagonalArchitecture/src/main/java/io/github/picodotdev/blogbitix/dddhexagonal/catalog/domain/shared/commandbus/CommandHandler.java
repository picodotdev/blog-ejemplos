package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.commandbus;

public interface CommandHandler<T extends Command> {

    void handle(T command) throws Exception;
}