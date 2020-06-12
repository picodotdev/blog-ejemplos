package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.Period;

public class IsLongTermSpecification implements Specification<Product>, org.springframework.data.jpa.domain.Specification<Product> {

    private String dateAttributeName;

    public IsLongTermSpecification() {
        this(null) ;
    }

    public IsLongTermSpecification(String path) {
        this.dateAttributeName = Specifications.getAttributeName(path, "date");
    }

    @Override
    public boolean isSatisfied(Product product) {
       return !Period.between(product.getDate(), LocalDate.now()).minus(Product.LONG_TERM_PERIOD).isNegative();
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        LocalDate longTermDate = LocalDate.now().plus(Product.LONG_TERM_PERIOD);
        return criteriaBuilder.lessThan(root.get(dateAttributeName), longTermDate);
    }
}