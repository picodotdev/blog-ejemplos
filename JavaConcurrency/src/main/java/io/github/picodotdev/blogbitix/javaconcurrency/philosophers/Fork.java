package io.github.picodotdev.blogbitix.javaconcurrency.philosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    private ReentrantLock lock;

    public Fork() {
        this.lock = new ReentrantLock();
    }

    public boolean take() throws InterruptedException {
        return lock.tryLock(250, TimeUnit.MILLISECONDS);
    }

    public boolean isHeld() {
        return lock.isHeldByCurrentThread();
    }

    public void drop() throws InterruptedException {
        if (isHeld()) {
            lock.unlock();
        }
    }
}
