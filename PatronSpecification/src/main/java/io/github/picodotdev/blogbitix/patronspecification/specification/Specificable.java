package io.github.picodotdev.blogbitix.patronspecification.specification;

public interface Specificable<T> {

    boolean satisfies(Specification<T> object);
}