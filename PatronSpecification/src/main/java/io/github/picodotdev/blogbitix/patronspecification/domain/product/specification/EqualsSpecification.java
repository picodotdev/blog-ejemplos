package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;

public class EqualsSpecification<T> implements Specification<T>, org.springframework.data.jpa.domain.Specification<T> {

    private String property;
    private Object value;
    private String propertyAttributeName;

    public EqualsSpecification(String property, Object value) {
        this(property, value, null);
    }

    public EqualsSpecification(String property, Object value, String path) {
        this.property = property;
        this.value = value;
        this.propertyAttributeName = Specifications.getAttributeName(path, property);
    }

    @Override
    public boolean isSatisfied(T product) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, Product.class);
            Object v = descriptor.getReadMethod().invoke(product);
            if (v == value) {
                return true;
            }
            if (v != null && value == null || v == null && value != null) {
                return false;
            }
            return value.equals(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(propertyAttributeName), value);
    }
}