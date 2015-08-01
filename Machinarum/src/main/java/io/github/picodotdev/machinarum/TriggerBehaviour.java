package io.github.picodotdev.machinarum;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class TriggerBehaviour<S, T, O, M> {

    private BiFunction<O, M, S> selector;
    private BiPredicate<O, M> guard;

    public TriggerBehaviour(BiFunction<O, M, S> selector, BiPredicate<O, M> guard) {
        this.selector = selector;
        this.guard = guard;
    }

    public boolean isMet(O object, M data) {
        if (guard == null) {
            return true;
        }
        return guard.test(object, data);
    }

    public Optional<S> select(O object, M data) {
        if (!isMet(object, data)) {
            throw new IllegalStateException();
        }
        return Optional.ofNullable(selector.apply(object, data));
    }
    
    public static <S, T, O, M> TriggerBehaviour<S, T, O, M> of(S state) {
        return new TriggerBehaviour<>((O o, M m) -> {
            return state;
        }, null);
    }
    
    public static <S, T, O, M> TriggerBehaviour<S, T, O, M> identity(BiPredicate<O, M> guard) {
        return new TriggerBehaviour<>((O o, M m) -> { 
            return null; 
        }, guard);
    }
}