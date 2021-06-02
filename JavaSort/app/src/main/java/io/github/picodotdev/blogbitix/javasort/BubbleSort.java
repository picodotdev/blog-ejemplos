package io.github.picodotdev.blogbitix.javasort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class BubbleSort<T> implements SortAlgorithm<T> {

    @Override
    public List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 1; j < list.size() - i; ++j) {
                T a = list.get(j - 1);
                T b = list.get(j);
                if (comparator.compare(a, b) > 0) {
                    swap(list, j - 1, j);
                }
            }
        }
        return list;
    }
}
