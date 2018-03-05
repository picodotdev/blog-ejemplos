package io.github.picodotdev.blogbitix.javaconcurrency.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Table implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Table.class);

    private Agent agent;
    private List<Smoker> smokers;
    private List<Pusher> pushers;
    private EnumMap<Component, Semaphore> components;
    private EnumMap<Component, Boolean> hasComponent;

    public Table() {
        this.agent = new Agent(this);
        this.smokers = new ArrayList<>();
        this.pushers = new ArrayList<>();
        this.components = new EnumMap<>(Component.class);
        this.hasComponent = new EnumMap<>(Component.class);
        for (Component component : Component.values()) {
            Smoker smoker = new Smoker(this, component);
            smokers.add(smoker);

            Pusher pusher = new Pusher(this, component);
            pushers.add(pusher);

            components.put(component, new Semaphore(0));
            hasComponent.put(component, Boolean.FALSE);
        }
    }

    public Boolean hasComponent(Component component) {
        return hasComponent.get(component);
    }

    public void setComponent(Component component) {
        hasComponent.put(component, Boolean.TRUE);
    }
    
    public void take(Component component) throws InterruptedException {
        components.get(component).acquire();
    }

    public EnumSet<Component> getComponents() {
        EnumSet<Component> components = EnumSet.noneOf(Component.class);
        for (Map.Entry<Component, Boolean> entry : hasComponent.entrySet()) {
            if (entry.getValue()) {
                components.add(entry.getKey());
            }
        }
        return components;
    }

    public void putComponents(EnumSet<Component> components) {
        logger.info("Agent generated {}", components);
        for (Component component : Component.values()) {
            hasComponent.put(component, Boolean.FALSE);
        }
        for (Component component : components) {
            this.components.get(component).release();
        }
    }

    public void awakeAgent() {
        agent.awake();
    }

    public void awakeSmoker(Component component) throws InterruptedException {
        for (Smoker smoker: smokers) {
            if (smoker.hasComponent(component)) {
                smoker.awake();
                break;
            }
        }
    }

    public void run() {
        new Thread(agent).start();
        for (Smoker smoker : smokers) {
            new Thread(smoker).start();
        }
        for (Pusher pusher : pushers) {
            new Thread(pusher).start();
        }
    }
}