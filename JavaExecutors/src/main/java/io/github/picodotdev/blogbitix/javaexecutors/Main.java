package io.github.picodotdev.blogbitix.javaexecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        int numberProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("Number processors: %d%n%n", numberProcessors);

        List<Callable<UUID>> tasks = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            tasks.add(newTask());
        }

        {
            System.out.println("Sequential");
            long start = System.currentTimeMillis();

            List<UUID> results = new ArrayList<>();
            for (Callable<UUID> task : tasks) {
                UUID uuid = task.call();
                results.add(uuid);
            }
            for (UUID uuid : results) {
                System.out.println(uuid);
            }

            long end = System.currentTimeMillis();
            System.out.printf("Sequential time %d ms%n", end - start);
        }

        System.out.println("");

        {
            int numberThreads = numberProcessors * 2;
            System.out.printf("Concurrent with %d threads%n", numberThreads);
            long start = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
            List<Future<UUID>> results = executor.invokeAll(tasks);
            executor.shutdown();
            for (Future<UUID> uuid : results) {
                System.out.println(uuid.get());
            }

            long end = System.currentTimeMillis();
            System.out.printf("Concurrent time %d ms%n", end - start);
        }
    }

    private static Callable<UUID> newTask() {
        return new Callable<UUID>() {
            @Override
            public UUID call() throws Exception {
                UUID uuid = UUID.randomUUID();
                System.out.printf("Starting task %s%n", uuid);
                Thread.sleep(3000);
                return uuid;
            }
        };
    }
}
