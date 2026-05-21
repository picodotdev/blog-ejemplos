package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.usecases;

import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventRepository;

@Component
public class GetEventUseCase {

    private EventRepository eventRepository;

    public GetEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event handle(EventId id) {
        return eventRepository.findById(id);
    }
}
