package io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus;

public interface CommandHandler<T extends Command> {

    void handle(T command) throws Exception;
}
