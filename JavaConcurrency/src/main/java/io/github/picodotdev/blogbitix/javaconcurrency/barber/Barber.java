package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barber implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Barber.class);

    private String name;
    private BarberShop shop;

    private Iterator<Long> times;
    private Semaphore sleep;
    private Lock turn;

    public Barber(BarberShop shop) {
        this.name = UUID.randomUUID().toString().split("-")[0];
        this.shop = shop;

        this.sleep = new Semaphore(0);
        this.turn = new ReentrantLock();
        this.times = new Random().longs(2000, 7000).iterator();
    }

    public void shave(Client client) throws InterruptedException {
        long time = getTime();
        logger.info("{} shaving {} during {}ms", name, client.getName(), time);
        spendTime(time);
        logger.info("{} shaved {}", name, client.getName());
        client.shaved();
    }

    public void ping() throws InterruptedException {
        turn.lock();
        sleep.release();
        turn.unlock();
    }

    public void awaked() throws InterruptedException {
        logger.info("{} awaked", name);
    }

    public void sleep() throws InterruptedException {
        logger.info("{} sleeping", name);
        sleep.acquire();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Client client = null;
                turn.lock();
                Optional<Client> next = shop.next();
                client = (next.isPresent()) ? next.get() : null;
                if (client == null) {
                    sleep.drainPermits();
                }
                turn.unlock();
                if (client != null) {
                    shave(client);
                } else {
                    sleep();
                    awaked();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private long getTime() throws InterruptedException {
        return times.next();
    }

    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}