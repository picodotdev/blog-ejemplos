package io.github.picodotdev.blogbitix.javasort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class QuickSort<T> implements SortAlgorithm<T> {

    @Override
    public List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        sort(list, 0, list.size() - 1, comparator);
        return list;
    }

    void sort(List<T> list, int begin, int end, Comparator<T> comparator) {
        if (begin < end) {
            int p = partition(list, begin, end, comparator);
            sort(list, begin, p - 1, comparator);
            sort(list, p + 1, end, comparator);
        }
    }

    int partition(List<T> list, int begin, int end, Comparator<T> comparator) {
        T pivot = list.get(end);
        int i = begin;
        for (int j = begin; j < end; ++j) {
            T o = list.get(j);
            if (comparator.compare(o, pivot) < 0) {
                swap(list, i, j);
                i += 1;
            }
        }
        swap(list, i, end);
        return i;
    }
}
