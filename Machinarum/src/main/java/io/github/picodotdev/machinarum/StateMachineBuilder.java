package io.github.picodotdev.machinarum;

import java.util.HashMap;
import java.util.Map;

public class StateMachineBuilder<S, T, O extends Subject<S>, M> {

    private Map<S, StateBuilder<S, T, O, M>> stateBuilders;
    private TriConsumer<O, T, M> unhandledTriggerHandler;
    private TriConsumer<O, T, M> exceptionHandler;

    public StateMachineBuilder() {
        stateBuilders = new HashMap<>();
    }

    public StateBuilder<S, T, O, M> state(S state) {
        StateBuilder<S, T, O, M> stateBuilder = new StateBuilder<>();
        stateBuilders.put(state, stateBuilder);
        return stateBuilder;
    }
    
    public StateMachineBuilder<S, T, O, M> unhandledTriggerHandler(TriConsumer<O, T, M> unhandledTriggerHandler) {
        this.unhandledTriggerHandler = unhandledTriggerHandler;
        return this;
    }
    
    public StateMachineBuilder<S, T, O, M> exceptionHandler(TriConsumer<O, T, M> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public StateMachine<S, T, O, M> build() {
        Map<S, State<S, T, O, M>> states = new HashMap<>();
        stateBuilders.forEach((S s, StateBuilder<S, T, O, M> b) -> { states.put(s, b.build()); });
        return new StateMachine<S, T, O, M>(states, unhandledTriggerHandler, exceptionHandler);
    }
}