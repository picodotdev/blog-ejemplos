package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Street implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Street.class);

    private AtomicLong counter;
    private BarberShop shop;

    private Iterator<Long> times;

    public Street(BarberShop shop) {
        this.counter = new AtomicLong();
        this.shop = shop;

        this.times = new Random().longs(1500, 3500).iterator();
    }

    @Override
    public void run() {
        while (true) {
            try {
                long i = counter.incrementAndGet();
                Client client = new Client("Client " + i, shop);
                logger.info("New client {}", client.getName());
                new Thread(client).start();

                spendTime(getTime());
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