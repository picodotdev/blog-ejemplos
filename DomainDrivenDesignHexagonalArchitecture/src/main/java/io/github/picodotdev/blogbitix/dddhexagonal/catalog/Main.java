package io.github.picodotdev.blogbitix.dddhexagonal.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.command.CreateEventCommand;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventDate;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventSchedule;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.application.commandbus.CommandBus;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private CommandBus commandBus;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Invocando commandBus");

        EventSchedule eventSchedule = EventSchedule.valueOf(EventDate.valueOf("2121-09-03T10:15:30"), EventDate.valueOf("2121-12-03T22:00:00"));
        CreateEventCommand command = CreateEventCommand.Builder.getInstance()
                .eventSchedule(eventSchedule)
                .build();
        commandBus.handle(command);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
