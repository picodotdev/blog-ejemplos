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

    public Barber(String name, BarberShop shop) {
        this.name = name;
        this.shop = shop;

        this.times = new Random().longs(2000, 7000).iterator();
    }

    public void shave(Client client) throws InterruptedException {
        long time = getTime();
        logger.info("{} shaving {} during {}ms", name, client.getName(), time);
        spendTime(time);
        logger.info("{} shaved {}", name, client.getName());
        client.shaved();
    }

    @Override
    public void run() {
        while (true) {
            try {
                logger.info("{} awaiting client", name);
                Client client = shop.next();
                shave(client);
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