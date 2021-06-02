package io.github.picodotdev.blogbitix.javasort;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface SortAlgorithm<T> {

    List<T> sort(Collection<T> collection, Comparator<T> comparator);

    default void swap(List<T> list, int i, int j) {
        T ti = list.get(i);
        T tj = list.get(j);
        list.set(j, ti);
        list.set(i, tj);
    }
}
