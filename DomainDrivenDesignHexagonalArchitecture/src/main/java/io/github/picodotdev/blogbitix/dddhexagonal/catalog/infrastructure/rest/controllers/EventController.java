package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.command.CreateEventCommand;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.query.GetEventQuery;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventDate;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventSchedule;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.commandbus.CommandBus;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.querybus.QueryBus;

@RestController
@RequestMapping("/event")
public class EventController {

    private CommandBus commandBus;
    private QueryBus queryBus;

    public EventController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus = queryBus;
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestParam("startDate") EventDate startDate, @RequestParam("endDate") EventDate endDate) throws Exception {
        CreateEventCommand command = CreateEventCommand.Builder.getInstance()
                .eventSchedule(EventSchedule.valueOf(startDate, startDate))
                .build();
        commandBus.handle(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Event> getEvent(@PathVariable("id") EventId eventId) throws Exception {
        GetEventQuery command = GetEventQuery.Builder.getInstance()
                .eventId(eventId)
                .build();
        Event event = queryBus.handle(command);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(event);
    }
}
