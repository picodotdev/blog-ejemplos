package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.query;

import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.usecases.GetEventUseCase;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventRepository;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.querybus.QueryHandler;

@Component
public class GetEventQueryHandler implements QueryHandler<Event, GetEventQuery> {

    private GetEventUseCase useCase;

    public GetEventQueryHandler(GetEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public Event handle(GetEventQuery query) throws Exception {
        return useCase.handle(query.getEventId());
    }
}
