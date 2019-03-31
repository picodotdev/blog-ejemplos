package io.github.picodotdev.blogbitix.springstatemachine;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.Random;

public class RandomGuard implements Guard<Main.States, Main.Events> {

    @Override
    public boolean evaluate(StateContext<Main.States, Main.Events> context) {
        return new Random().nextBoolean();
    }
}
