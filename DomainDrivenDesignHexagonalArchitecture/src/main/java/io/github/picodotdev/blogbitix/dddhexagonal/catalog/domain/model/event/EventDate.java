package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.exceptions.InvalidDate;

public class EventDate {

    private final LocalDateTime datetime;

    private EventDate(final LocalDateTime datetime) throws InvalidDate {
        this.datetime = datetime;
    }

    public static EventDate valueOf(final String datetime) throws InvalidDate {
        return new EventDate(toDate(datetime));
    }

    public LocalDateTime geValue() {
        return datetime;
    }

    public boolean isAfter(final EventDate datetime) {
        return this.datetime.isAfter(datetime.getDateTime());
    }

    public boolean isFutureDate() {
        return datetime.isAfter(LocalDateTime.now());
    }

    private LocalDateTime getDateTime() {
        return datetime;
    }

    private static LocalDateTime toDate(final String date) throws InvalidDate {
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException ex) {
            throw new InvalidDate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof EventDate)) return false;
        EventDate that = (EventDate) o;
        return Objects.equals(this.datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datetime);
    }
}
