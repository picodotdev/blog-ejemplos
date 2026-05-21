package io.github.picodotdev.blogbitix.guava;

import com.google.common.eventbus.Subscribe;

public class EventListener {
 
    @Subscribe
    public void onEvent(Event event) {
        System.out.printf("Event: %s%n", event.getMessage());
    }
}