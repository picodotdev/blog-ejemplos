package io.github.picodotdev.blogbitix.eventbus.domain.purchase;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.aggregateroot.AggregateRoot;
import io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent.DomainEventCollection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements AggregateRoot {

    private OrderId id;
    private LocalDateTime date;
    private List<Item> items;

    private DomainEventCollection events;

    protected Order(OrderId id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.date = LocalDateTime.now();
        this.events = new DomainEventCollection();
    }

    public static Order create(OrderId id) {
        return create(id, new ArrayList<>());
    }

    public static Order create(OrderId id, List<Item> items) {
        Order order = new Order(id);
        order.getItems().addAll(items);
        order.getEvents().add(new OrderCreated(order.getId(), order.hashStock()));
        return order;
    }

    @Override
    public DomainEventCollection getEvents() {
        return events;
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

    public boolean hashStock() {
        return items.stream().allMatch( it -> it.getQuantity() < it.getProduct().getStock());
    }

    public BigDecimal getAmount() {
        return items.stream()
                .map(Item::getAmount)
                .reduce(new BigDecimal("0.00"), (a, b) -> new BigDecimal("0.00").add(a).add(b));
    }
}
