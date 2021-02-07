package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.query;

import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventRepository;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.querybus.QueryHandler;

@Component
public class GetEventQueryHandler implements QueryHandler<Event, GetEventQuery> {

    private EventRepository eventRepository;

    public GetEventQueryHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event handle(GetEventQuery query) throws Exception {
        return eventRepository.findById(query.getEventId());
    }
}
