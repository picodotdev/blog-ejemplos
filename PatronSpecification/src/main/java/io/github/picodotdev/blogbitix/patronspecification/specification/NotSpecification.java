package io.github.picodotdev.blogbitix.patronspecification.specification;

public class NotSpecification<T> implements Specification<T> {

    private Specification<T> specification;

    public NotSpecification(Specification<T> specification) {
        this.specification = specification;
    }

    public boolean isSatisfied(T object) {
        return !specification.isSatisfied(object);
    }
}