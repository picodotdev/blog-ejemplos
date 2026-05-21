package io.github.picodotdev.machinarum;

@FunctionalInterface
public interface TriConsumer<T, S, U> {

    void accept(T t, S s, U u);
}
