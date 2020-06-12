package io.github.picodotdev.blogbitix.patronspecification.specification;

import java.util.Arrays;
import java.util.List;

public class OrSpecification<T> implements Specification<T> {

    private List<Specification<T>> specifications;

    public OrSpecification(Specification<T>... specifications) {
        this.specifications = Arrays.asList(specifications);
    }

    public boolean isSatisfied(T object) {
        return specifications.stream().anyMatch(s -> { return s.isSatisfied(object); });
    }
}