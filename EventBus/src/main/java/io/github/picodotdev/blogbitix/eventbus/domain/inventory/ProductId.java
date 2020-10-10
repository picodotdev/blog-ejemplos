package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import java.util.Objects;
import java.util.UUID;

public class ProductId {

    private UUID id;

    public ProductId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return id.equals(productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
