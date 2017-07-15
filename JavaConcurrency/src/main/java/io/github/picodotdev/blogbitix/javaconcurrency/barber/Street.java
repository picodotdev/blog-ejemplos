package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

public class Street implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Street.class);

    private BarberShop shop;

    public Street(BarberShop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Client client = new Client(shop);
                logger.info("New client {}", client.getName());
                new Thread(client).start();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}