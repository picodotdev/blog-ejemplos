package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import java.math.BigInteger;
import java.util.Objects;

public class EventId {

    private BigInteger id;

    protected EventId() {
    }

    protected EventId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getValue() {
        return id;
    }

    public static EventId valueOf(String id) {
        return new EventId(new BigInteger(id));
    }

    public static EventId valueOf(BigInteger id) {
        return new EventId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof EventId)) return false;
        EventId that = (EventId) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
