package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsCheapSpecification implements Specification<Product>, org.springframework.data.jpa.domain.Specification<Product> {

    private String priceAttributeName;

    public IsCheapSpecification() {
        this(null) ;
    }

    public IsCheapSpecification(String path) {
        this.priceAttributeName = Specifications.getAttributeName(path, "price");
    }

    @Override
    public boolean isSatisfied(Product product) {
       return Product.CHEAP_PRICE.compareTo(product.getPrice()) == 1;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lt(root.get(priceAttributeName), Product.CHEAP_PRICE);
    }
}