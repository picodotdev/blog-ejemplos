package io.github.picodotdev.blogbitix.javareference;

import java.lang.ref.*;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        SoftReference<String> soft = new SoftReference<>(new String("Hello World!"), queue);
        WeakReference<String> weak = new WeakReference<>(new String("Hello World!"), queue);
        PhantomReference<String> phantom = new PhantomReference<>(new String("Hello World!"), queue);



        Reference<? extends String> reference = queue.poll();

        while (reference == null) {
		    String[] foo = new String[1000000];
		    for (int i = 0; i < foo.length; ++i) {
		        foo[i] = UUID.randomUUID().toString();
		    }
			reference = queue.poll();
		}

        while (reference != null) {
            System.out.println(reference.getClass().getName());
            reference = queue.poll();
        }
    }
}
