package io.github.picodotdev.blogbitix.javaconcurrency.agent;

import java.util.EnumSet;
import java.util.concurrent.locks.ReentrantLock;

public class Pusher implements Runnable {

    private static ReentrantLock lock;

    private Table table;
    private Component component;

    static {
        lock = new ReentrantLock();
    }

    public Pusher(Table table, Component component) {
        this.table = table;
        this.component = component;
    }

    public void run() {
        while (true) {
            try {
                table.take(component);
                lock.lock();

                table.setComponent(component);
                EnumSet<Component> components = EnumSet.complementOf(table.getComponents());

                if (components.size() == 1) {
                    table.awakeSmoker(components.iterator().next());
                }
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}