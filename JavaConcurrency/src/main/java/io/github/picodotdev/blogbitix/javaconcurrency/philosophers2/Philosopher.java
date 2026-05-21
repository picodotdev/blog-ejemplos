package io.github.picodotdev.blogbitix.javaconcurrency.philosophers2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Philosopher implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Philosopher.class);

    private String name;
    private Table table;
    private Fork right;
    private Fork left;
    private boolean isLeftHanded;

    public Philosopher(String name, Table table, Fork left, Fork right, boolean isLeftHanded) {
        this.name = name;
        this.table = table;
        this.right = right;
        this.left = left;
        this.isLeftHanded = isLeftHanded;
    }

    public void think() throws InterruptedException {
        long time = table.getTime();
        logger.info("{} thinking during {}ms", name, time);
        spendTime(time);
    }

    public void eat() throws InterruptedException {
        takeForks();
        long time = table.getTime();
        logger.info("{} eating during {}ms", name, time);
        spendTime(time);
        dropForks();
    }

    public void run() {
        while (true) {
            try {
                think();
                eat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void takeForks() {
        if (isLeftHanded) {
            left.take();
            right.take();
        } else {
            right.take();
            left.take();
        }
    }

    private void dropForks() {
        if (isLeftHanded) {
            left.drop();
            right.drop();
        } else {
            right.drop();
            left.drop();
        }
    }

    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
