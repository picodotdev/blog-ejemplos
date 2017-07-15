package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BarberShop {

    private static Logger logger = LoggerFactory.getLogger(BarberShop.class);

    private Semaphore chairs;
    private List<Client> clients;
    private List<Barber> barbers;

    private Iterator<Long> times;
    private ExecutorService pingExecutorService;

    public BarberShop(int numChairs, int numBarbers) {
        this.chairs = new Semaphore(numChairs);
        this.clients = Collections.synchronizedList(new ArrayList<>());
        this.barbers = new ArrayList<>();
        for (int i = 0; i < numBarbers; ++i) {
            Barber barber = new Barber(this);
            barbers.add(barber);
        }

        this.times = new Random().longs(1000, 4000).iterator();
        this.pingExecutorService = Executors.newFixedThreadPool(numBarbers);
        this.ping();
    }

    public List<Barber> getBarbers() {
        return barbers;
    }

    public boolean enter(Client client) throws InterruptedException {
        boolean entered = chairs.tryAcquire(1, TimeUnit.SECONDS);
        if (!entered) {
            return false;
        }
        clients.add(client);
        logger.info("{} awaiting to barber ({})", client.getName(), clients.size());
        ping();
        client.awaitShave();
        return true;
    }

    public Optional<Client> next() {
        synchronized (clients) {
            if (clients.size() == 0) {
                return Optional.empty();
            }
            chairs.release();
            return Optional.of(clients.remove(0));
        }
    }

    private void ping() {
        barbers.stream().forEach(barber -> {
            pingExecutorService.submit(() -> {
                try {
                    barber.ping();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private long getTime() throws InterruptedException {
        return times.next();
    }

    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
