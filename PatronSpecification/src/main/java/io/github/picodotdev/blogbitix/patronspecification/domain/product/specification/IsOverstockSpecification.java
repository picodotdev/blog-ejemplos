package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsOverstockSpecification implements Specification<Product>, org.springframework.data.jpa.domain.Specification<Product> {

    private String priceAttributeName;

    public IsOverstockSpecification() {
        this(null) ;
    }

    public IsOverstockSpecification(String path) {
        this.priceAttributeName = Specifications.getAttributeName(path, "units");
    }

    @Override
    public boolean isSatisfied(Product product) {
        return product.getUnits() > Product.OVERSTOCK_UNITS;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.gt(root.get(priceAttributeName), Product.OVERSTOCK_UNITS);
    }
}