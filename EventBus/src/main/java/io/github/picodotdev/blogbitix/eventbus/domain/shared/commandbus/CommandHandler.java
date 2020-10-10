package io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus;

public interface CommandHandler<T> {

    void handle(T command) throws Exception;
}
