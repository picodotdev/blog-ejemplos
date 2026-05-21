package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    private String name;
    private BarberShop shop;

    private Iterator<Long> times;

    private Semaphore shave;

    public Client(String name, BarberShop shop) {
        this.name = name;
        this.shop = shop;

        this.shave = new Semaphore(0);
        this.times = new Random().longs(2000, 7000).iterator();
    }

    public String getName() {
        return name;
    }

    public void awaitShave() throws InterruptedException {
        shave.acquire();
    }

    public void shaved() {
        shave.release();
    }

    @Override
    public void run() {
        try {
            boolean entered = shop.enter(this);
            if (!entered) {
                logger.info("{} exited, barbershop at full capacity", name);
            } else {
                logger.info("{} exited", name);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
