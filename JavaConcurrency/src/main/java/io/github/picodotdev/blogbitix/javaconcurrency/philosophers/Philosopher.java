package io.github.picodotdev.blogbitix.javaconcurrency.philosophers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

public class Philosopher implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Philosopher.class);

    private String name;
    private Fork leftFork;
    private Fork rightFork;

    private Iterator<Long> times;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;

        this.times = new Random().longs(2000, 7000).iterator();
    }

    public void eat() throws InterruptedException {
        try {
            logger.info("{} trying eat...", name);
            boolean leftTaked = leftFork.take();
            if (leftTaked) {
                boolean rightTaked = rightFork.take();
                if (rightTaked) {
                    long time = getTime();
                    logger.info("{} eating during {}ms", name, time);
                    spendTime(time);
                }
            }
            if (!leftFork.isHeld() || !rightFork.isHeld()) {
                logger.info("{} cannot eat", name);
            }
        } finally {
            leftFork.drop();
            rightFork.drop();
        }
    }

    public void think() throws InterruptedException {
        long time = getTime();
        logger.info("{} thinkink during {}ms", name, time);
        spendTime(time);
    }


    public Fork getLeftFork() {
        return leftFork;
    }

    public Fork getRightFork() {
        return rightFork;
    }

    @Override
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

    private long getTime() throws InterruptedException {
        return times.next();
    }

    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
