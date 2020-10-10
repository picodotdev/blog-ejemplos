package io.github.picodotdev.blogbitix.eventbus.domain.order;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.aggregateroot.AggregateRoot;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventCollection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements AggregateRoot {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("3000.00");

    private OrderId id;
    private LocalDateTime date;
    private List<Item> items;

    private EventCollection events;

    protected Order(OrderId id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.date = LocalDateTime.now();
        this.events = new EventCollection();
    }

    public static Order create(OrderId id) throws Exception {
        return create(id, new ArrayList<>());
    }

    public static Order create(OrderId id, List<Item> items) throws Exception {
        Order order = new Order(id);
        order.getItems().addAll(items);
        if (!order.isValidAmount()) {
            throw new Exception("Invalid order amount");
        }
        order.getEvents().add(new OrderCreated(order.getId()));
        return order;
    }

    public OrderId getId() {
        return id;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getAmount() {
        return items.stream()
                .map(Item::getAmount)
                .reduce(new BigDecimal("0.00"), (a, b) -> new BigDecimal("0.00").add(a).add(b));
    }

    private boolean isValidAmount() {
        return getAmount().compareTo(MAX_AMOUNT) == -1;
    }

    @Override
    public EventCollection getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
