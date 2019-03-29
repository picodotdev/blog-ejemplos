package io.github.picodotdev.blogbitix.javaterminal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Terminal terminal = new Terminal();
        Printer printer = new Printer(terminal);

        List<Progress> progresses = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Progress progress = new Progress(printer, i, i * 5 + 5);
            progresses.add(progress);
        }

        for (Progress progress : progresses) {
            Thread thread = new Thread(progress);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread: threads) {
            thread.join();
        }

        System.out.println();
    }
}
