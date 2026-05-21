package io.github.picodotdev.machinarum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class StateBuilder<S, T, O, M> {

    private Map<T, TriggerBehaviour<S, T, O, M>> triggerBehaviours;
    private Map<T, List<BiConsumer<O, M>>> triggerEnters;
    private Map<T, List<BiConsumer<O, M>>> triggerExits;
    private List<BiConsumer<O, M>> enters;
    private List<BiConsumer<O, M>> exits;

    public StateBuilder() {
        this.triggerBehaviours = new HashMap<>();
        this.triggerEnters = new HashMap<>();
        this.triggerExits = new HashMap<>();
        this.enters = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    public StateBuilder<S, T, O, M> permit(T trigger, S destination) {
        return permit(trigger, destination, null);
    }

    public StateBuilder<S, T, O, M> permit(T trigger, S destination, BiPredicate<O, M> guard) {
        triggerBehaviours.put(trigger, TriggerBehaviour.of(destination));
        return this;
    }

    public StateBuilder<S, T, O, M> permit(T trigger, BiFunction<O, M, S> selector) {
        return permit(trigger, selector, null);
    }

    public StateBuilder<S, T, O, M> permit(T trigger, BiFunction<O, M, S> selector, BiPredicate<O, M> guard) {
        triggerBehaviours.put(trigger, new TriggerBehaviour<>(selector, guard));
        return this;
    }

    public StateBuilder<S, T, O, M> ignore(T trigger) {
        return ignore(trigger, null);
    }

    public StateBuilder<S, T, O, M> ignore(T trigger, BiPredicate<O, M> guard) {
        triggerBehaviours.put(trigger, TriggerBehaviour.identity(guard));
        return this;
    }

    public StateBuilder<S, T, O, M> enter(T trigger, BiConsumer<O, M> action) {
        triggerEnters.putIfAbsent(trigger, new ArrayList<>());
        triggerEnters.get(trigger).add(action);
        return this;
    }

    public StateBuilder<S, T, O, M> enter(BiConsumer<O, M> action) {
        enters.add(action);
        return this;
    }

    public StateBuilder<S, T, O, M> exit(T trigger, BiConsumer<O, M> action) {
        triggerExits.putIfAbsent(trigger, new ArrayList<>());
        triggerExits.get(trigger).add(action);
        return this;
    }

    public StateBuilder<S, T, O, M> exit(BiConsumer<O, M> action) {
        exits.add(action);
        return this;
    }

    public State<S, T, O, M> build() {
        return new State<>(triggerBehaviours, triggerEnters, triggerExits, enters, exits);
    }
}