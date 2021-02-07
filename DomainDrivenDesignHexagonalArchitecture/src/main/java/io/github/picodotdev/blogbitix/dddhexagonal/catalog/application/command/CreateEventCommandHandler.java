package io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.command;

import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.usecases.CreateEventUseCase;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventRepository;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.commandbus.CommandHandler;

@Component
public class CreateEventCommandHandler implements CommandHandler<CreateEventCommand> {

    private EventRepository eventRepository;
    private CreateEventUseCase useCase;

    public CreateEventCommandHandler(EventRepository eventRepository, CreateEventUseCase useCase) {
        this.eventRepository = eventRepository;
        this.useCase = useCase;
    }

    @Override
    public void handle(CreateEventCommand command) throws Exception {
        System.out.println("Creando evento");

        EventId id = eventRepository.getId();
        useCase.handle(id, command.getEventSchedule());
    }
}