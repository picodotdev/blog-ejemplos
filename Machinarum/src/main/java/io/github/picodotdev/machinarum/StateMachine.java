package io.github.picodotdev.machinarum;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class StateMachine<S, T, O extends Subject<S>, M> {

    private Map<S, State<S, T, O, M>> states;
    private TriConsumer<O, T, M> unhandledTriggerHandler;
    private TriConsumer<O, T, M> exceptionHandler;

    StateMachine(Map<S, State<S, T, O, M>> states, TriConsumer<O, T, M> unhandledTriggerHandler, TriConsumer<O, T, M> exceptionHandler) {
        this.states = states;
        this.unhandledTriggerHandler = unhandledTriggerHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public S getState(O object) {
        return object.getState();
    }

    private void setState(O object, S state) {
        object.setState(state);
    }

    public boolean isState(O object, S state) {
        return getState(object) == state;
    }

    public Set<T> getTriggers(O object) {
        return states.get(getState(object)).getPermittedTriggers();
    }

    public boolean hasTrigger(O object, T trigger) {
        return getTriggers(object).contains(trigger);
    }

    public boolean canFire(O object, T trigger) {
        return canFire(object, trigger, null);
    }

    public boolean canFire(O object, T trigger, M data) {
        synchronized (object) {
            Optional<TriggerBehaviour<S, T, O, M>> triggerBehaviour = states.get(getState(object)).getHandler(trigger);
            if (!triggerBehaviour.isPresent()) {
                return false;
            }
            return triggerBehaviour.get().isMet(object, data);
        }
    }

    public void fire(O object, T trigger) {
        fire(object, trigger, null);
    }

    public void fire(O object, T trigger, M data) {
        synchronized (object) {
            Optional<TriggerBehaviour<S, T, O, M>> optTriggerBehaviour = states.get(getState(object)).getHandler(trigger);

            if (optTriggerBehaviour.isPresent()) {
                TriggerBehaviour<S, T, O, M> triggerBehaviour = optTriggerBehaviour.get();
                if (triggerBehaviour.isMet(object, data)) {
                    Optional<S> state = null;

                    try {
                        state = triggerBehaviour.select(object, data);
                    } catch (Exception e) {
                        exceptionHandler.accept(object, trigger, data);
                    }

                    if (state.isPresent()) {
                        states.get(state.get()).exit(object, trigger, data);
                        setState(object, state.get());
                        states.get(state.get()).enter(object, trigger, data);
                    }
                }
            } else {
                unhandledTriggerHandler.accept(object, trigger, data);
            }
        }
    }
}