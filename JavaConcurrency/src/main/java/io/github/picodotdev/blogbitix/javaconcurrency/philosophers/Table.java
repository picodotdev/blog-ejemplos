package io.github.picodotdev.blogbitix.javaconcurrency.philosophers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Table implements Runnable {

    private List<Philosopher> philosophers;

    public Table(int numPhilosophers) {
        if (numPhilosophers < 2) {
            throw new IllegalArgumentException("There should be more than one philosopher");
        }

        this.philosophers = new ArrayList<>();

        Fork leftFork = new Fork();
        for (int i = 0; i < numPhilosophers; ++i) {
            boolean isLastPhilosopher = (i == numPhilosophers -1);
            Fork rightFork = (isLastPhilosopher) ? philosophers.get(0).getLeftFork() : new Fork();

            Philosopher philosopher = new Philosopher("Philosopher " + (i + 1), leftFork, rightFork);
            philosophers.add(philosopher);
            leftFork = rightFork;
		}
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(philosophers.size());
        for (Philosopher p : philosophers) {
            executorService.submit(p);
        }
    }
}
