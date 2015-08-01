package io.github.picodotdev.machinarum;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public class State<S, T, O, M> {

    private Map<T, TriggerBehaviour<S, T, O, M>> triggerBehaviours;
    private Map<T, List<BiConsumer<O, M>>> triggerEnters;
    private Map<T, List<BiConsumer<O, M>>> triggerExits;
    private List<BiConsumer<O, M>> enters;
    private List<BiConsumer<O, M>> exits;

    public State(Map<T, TriggerBehaviour<S, T, O, M>> triggerBehaviours, Map<T, List<BiConsumer<O, M>>> triggerEnters, Map<T, List<BiConsumer<O, M>>> triggerExits, List<BiConsumer<O, M>> enters, List<BiConsumer<O, M>> exits) {
        this.triggerBehaviours = triggerBehaviours;
        this.triggerEnters = triggerEnters;
        this.triggerExits = triggerExits;
        this.enters = enters;
        this.exits = exits;        
    }
    
    public boolean canHandle(T trigger) {
        return getHandler(trigger).isPresent();
    }
    
    public Optional<TriggerBehaviour<S, T, O, M>> getHandler(T trigger) {
        return Optional.ofNullable(triggerBehaviours.get(trigger));
    }
    
    public Set<T> getPermittedTriggers() {
        return triggerBehaviours.keySet();
    }
    
    public void enter(O object, T trigger, M data) {
        enters.stream().forEach((c) -> { c.accept(object, data); });
        triggerEnters.getOrDefault(trigger, Collections.emptyList()).stream().forEach((c) -> { c.accept(object, data); });
    }
    
    public void exit(O object, T trigger, M data) {
        triggerExits.getOrDefault(trigger, Collections.emptyList()).stream().forEach((c) -> { c.accept(object, data); });
        exits.stream().forEach((c) -> { c.accept(object, data); });        
    }
}