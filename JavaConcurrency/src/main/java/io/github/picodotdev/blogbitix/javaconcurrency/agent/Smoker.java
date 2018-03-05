package io.github.picodotdev.blogbitix.javaconcurrency.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class Smoker implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Smoker.class);

    private Table table;
    private Component component;
    private Semaphore components;
    
    public Smoker(Table table, Component component) {
        this.table = table;
        this.component = component;
        this.components = new Semaphore(0);
    }

    public Component getComponent() {
        return component;
    }

    public boolean hasComponent(Component component) {
        return this.component == component;
    }
    
    public void smoke() throws InterruptedException {
        logger.info("Smoker {} smoking during {}ms", component, 3000);
        Thread.sleep(3000);
    }

    public void awake() {
        components.release();
    }
    
    private void awaitComponents() throws InterruptedException {
        components.acquire();
    }
    
    public void run() {
        while (true) {
            try {
                awaitComponents();
                smoke();
                table.awakeAgent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
