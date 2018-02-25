package io.github.picodotdev.blogbitix.javaconcurrency.philosophers2;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    private ReentrantLock lock;

    public Fork() {
        this.lock = new ReentrantLock();
    }

    public void take() {
        lock.lock();
    }

    public void drop() {
        if (!isHeld())
            return;
        lock.unlock();
    }

    public boolean isHeld() {
        return lock.isHeldByCurrentThread();
    }
}