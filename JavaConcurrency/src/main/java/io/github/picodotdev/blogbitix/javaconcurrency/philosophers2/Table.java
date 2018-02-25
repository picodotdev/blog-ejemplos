package io.github.picodotdev.blogbitix.javaconcurrency.philosophers2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Table implements Runnable {

    private List<Fork> forks;
    private List<Philosopher> philosophers;
    private Iterator<Long> times;

    public Table(int numPhilosophers) {
        if (numPhilosophers < 2) {
            throw new IllegalArgumentException("There should be more than one philosopher");
        }

        this.forks = new ArrayList<>();
        this.philosophers = new ArrayList<>();
        this.times = new Random().longs(2000, 7000).iterator();

        for (int i = 0; i < numPhilosophers; ++i) {
            Fork f = new Fork();
            forks.add(f);
        }
        for (int i = 0; i < numPhilosophers; ++i) {
            int n = (i + 1) % numPhilosophers;
            Fork left = forks.get(i);
            Fork right = forks.get(n);
            boolean isLeftHanded = (n == 0);

            Philosopher p = new Philosopher("Philosopher " + (i + 1),this, left, right, isLeftHanded);
            philosophers.add(p);
        }
    }

    public synchronized long getTime() {
        return times.next();
    }

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(philosophers.size());
        for (Philosopher p : philosophers) {
            executorService.submit(p);
        }
    }
}
