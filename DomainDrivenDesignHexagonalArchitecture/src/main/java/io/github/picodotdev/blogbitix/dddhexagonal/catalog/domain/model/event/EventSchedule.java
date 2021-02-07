package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import java.util.Objects;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.exceptions.EndDateIsBeforeStartDate;

public class EventSchedule {

    private EventDate startDate;
    private EventDate endDate;

    private EventSchedule(EventDate startDate, EventDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDate getStartDate() {
        return startDate;
    }

    public EventDate getEndDate() {
        return endDate;
    }

    public boolean isFutureDate() {
        return startDate.isFutureDate();
    }

    public static EventSchedule valueOf(EventDate startDate, EventDate endDate) throws EndDateIsBeforeStartDate {
        validateStartBeforeEnd(startDate, endDate);
        return new EventSchedule(startDate, endDate);
    }

    private static void validateStartBeforeEnd(EventDate startDate, EventDate endDate) throws EndDateIsBeforeStartDate {
        if (startDate.isAfter(endDate)) {
            throw new EndDateIsBeforeStartDate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof EventSchedule)) return false;
        EventSchedule eventSchedule = (EventSchedule) o;
        return Objects.equals(startDate, eventSchedule.startDate)
                && Objects.equals(endDate, eventSchedule.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
