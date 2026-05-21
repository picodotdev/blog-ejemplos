package io.github.picodotdev.blogbitix.entitiesid.domain.product;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProductId implements Serializable  {

    private BigInteger id;

    protected ProductId() {
    }

    protected ProductId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getValue() {
        return id;
    }

    public void setValue(BigInteger id) {
        this.id = id;
    }

    public static ProductId valueOf(BigInteger id) {
        return new ProductId(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof ProductId))
            return false;

        ProductId that = (ProductId) o;
        return Objects.equals(this.id, that.id);
    }
}
