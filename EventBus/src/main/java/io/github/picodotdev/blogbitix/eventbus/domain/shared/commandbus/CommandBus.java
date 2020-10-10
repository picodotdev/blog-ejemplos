package io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus;

public interface CommandBus {

    <T> void handle(Command command) throws Exception;
}
