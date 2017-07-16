package io.github.picodotdev.blogbitix.javaforeach;

import java.util.Iterator;

public class Counter implements Iterable<Integer> {

    private int start;
    private int end;

    public Counter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int i = start;

            public boolean hasNext() {
                return i < end;
            }

            public Integer next() {
                return i++;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
