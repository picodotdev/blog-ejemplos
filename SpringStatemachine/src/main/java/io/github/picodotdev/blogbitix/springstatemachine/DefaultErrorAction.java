package io.github.picodotdev.blogbitix.springstatemachine;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class DefaultErrorAction implements Action<Main.States, Main.Events> {

    @Override
    public void execute(StateContext<Main.States, Main.Events> context) {
        context.getException().printStackTrace();
    }
}
