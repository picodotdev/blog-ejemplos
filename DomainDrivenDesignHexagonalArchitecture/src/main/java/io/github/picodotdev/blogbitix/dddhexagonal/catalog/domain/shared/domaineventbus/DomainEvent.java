package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class DomainEvent {

    private DomainEventId id;
    private LocalDateTime date;
    private Map<String, Object> data;

    public DomainEvent() {
        this(Collections.emptyMap());
    }

    public DomainEvent(Map<String, Object> data) {
        this.id = new DomainEventId(UUID.randomUUID());
        this.date = LocalDateTime.now(ZoneId.of("UTC"));
        this.data = data;
    }

    public DomainEventId getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Map<String, Object> getData() {
        return data;
    }
}