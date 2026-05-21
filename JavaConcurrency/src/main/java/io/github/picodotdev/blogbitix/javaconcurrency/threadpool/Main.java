package io.github.picodotdev.blogbitix.javaconcurrency.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        List<Callable<UUID>> tasks = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            tasks.add(newTask());
        }

        {
            long start = System.currentTimeMillis();
            logger.info("Executing sequential...");

            List<UUID> results = new ArrayList<>();
            for (Callable<UUID> task : tasks) {
                 UUID uuid = task.call();
                results.add(uuid);
            }
            for(UUID uuid : results) {
                System.out.println(uuid);
            }

            long end = System.currentTimeMillis();
            logger.info("Sequential time ({} ms)...", end - start);
        }

        {
            long start = System.currentTimeMillis();
            logger.info("Executing concurrent...");

            ExecutorService executor = Executors.newFixedThreadPool(4);
            List<Future<UUID>> results = executor.invokeAll(tasks);
            executor.shutdown();
            for(Future<UUID> uuid : results) {
                System.out.println(uuid.get());
            }

            long end = System.currentTimeMillis();
            logger.info("Concurrent time ({} ms)...", end - start);
        }
    }

    private static Callable<UUID> newTask() {
        return new Callable<UUID>() {
            @Override
            public UUID call() throws Exception {
                UUID uuid = UUID.randomUUID();
                logger.info("Starting task {}", uuid);
                Thread.sleep(3000);
                return uuid;
            }
        };
    }
}
