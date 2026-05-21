package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class BarberShop {

    private static Logger logger = LoggerFactory.getLogger(BarberShop.class);

    private ArrayBlockingQueue<Client> clients;
    private Semaphore chairs;
    private List<Barber> barbers;

    private Iterator<Long> times;

    public BarberShop(int capacity, int numBarbers) {
        this.clients = new ArrayBlockingQueue<Client>(capacity);
        this.chairs = new Semaphore(numBarbers);

        this.barbers = new ArrayList<>();
        for (int i = 0; i < numBarbers; ++i) {
            Barber barber = new Barber("Barber " + (i + 1), this);
            barbers.add(barber);
        }

        this.times = new Random().longs(1000, 4000).iterator();
    }

    public List<Barber> getBarbers() {
        return barbers;
    }

    public boolean enter(Client client) throws InterruptedException {
        boolean entered = clients.offer(client, 1, TimeUnit.SECONDS);
        if (!entered) {
            return false;
        }
        logger.info("{} awaiting to barber ({})", client.getName(), clients.size());
        chairs.acquire();
        client.awaitShave();
        return true;
    }

    public Client next() throws InterruptedException {
        return clients.take();
    }

    private long getTime() throws InterruptedException {
        return times.next();
    }

    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
