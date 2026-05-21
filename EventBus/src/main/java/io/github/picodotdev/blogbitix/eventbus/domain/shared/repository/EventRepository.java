package io.github.picodotdev.blogbitix.eventbus.domain.shared.repository;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;

public interface EventRepository {

    void add(Event event);
    boolean exists(Event event);
}
