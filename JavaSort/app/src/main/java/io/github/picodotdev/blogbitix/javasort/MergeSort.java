package io.github.picodotdev.blogbitix.javasort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T> implements SortAlgorithm<T> {

    @Override
    public List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        int n = list.size();
        if (n < 2) {
            return list;
        }
        int mid = n / 2;
        List<T> l = list.subList(0, mid);
        List<T> r = list.subList(mid, n);
        l = sort(l, comparator);
        r = sort(r, comparator);
        list = merge(l, r, comparator);
        return list;
    }

    private List<T> merge(List<T> l, List<T> r, Comparator<T> comparator) {
        List<T> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < l.size() && j < r.size()) {
            T a = l.get(i);
            T b = r.get(j);
            if (comparator.compare(a, b) <= 0) {
                list.add(a);
                i += 1;
            } else {
                list.add(b);
                j += 1;
            }
        }
        while (i < l.size()) {
            T o = l.get(i);
            list.add(o);
            i += 1;
        }
        while (j < r.size()) {
            T o = r.get(j);
            list.add(o);
            j += 1;
        }
        return list;
    }
}
