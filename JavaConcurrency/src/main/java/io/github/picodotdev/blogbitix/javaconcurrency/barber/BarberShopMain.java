package io.github.picodotdev.blogbitix.javaconcurrency.barber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BarberShopMain {

    private static Logger logger = LoggerFactory.getLogger(BarberShopMain.class);

    public static void main(String[] args) throws InterruptedException {
        BarberShop shop = new BarberShop(5, 2);
        List<Barber> barbers = shop.getBarbers();
        Street street = new Street(shop);

        ExecutorService executorService = Executors.newFixedThreadPool(barbers.size() + 1);
        executorService.submit(street);
        barbers.forEach(executorService::submit);
    }
}
