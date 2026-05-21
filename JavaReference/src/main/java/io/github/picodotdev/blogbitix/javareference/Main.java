package io.github.picodotdev.blogbitix.javareference;

import java.lang.ref.*;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        ReferenceQueue<String> queue = new ReferenceQueue<>();

        String strong = new String("Hello World!");
        SoftReference<String> soft = new SoftReference<>(new String("Hello World!"), queue);
        WeakReference<String> weak = new WeakReference<>(new String("Hello World!"), queue);
        PhantomReference<String> phantom = new PhantomReference<>(new String("Hello World!"), queue);

        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();

        Reference<? extends String> reference = queue.poll();
        while (reference != null) {
            System.out.println(reference.getClass().getName());
            reference = queue.poll();
        }
    }
}
