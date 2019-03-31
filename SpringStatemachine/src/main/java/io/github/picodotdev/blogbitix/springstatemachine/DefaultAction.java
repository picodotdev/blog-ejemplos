package io.github.picodotdev.blogbitix.springstatemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class DefaultAction implements Action<Main.States, Main.Events> {

    private static Logger logger = LoggerFactory.getLogger(DefaultAction.class);

    @Override
    public void execute(StateContext<Main.States, Main.Events> context) {
        logger.info("Action Source: {}, State: {}, Target: {}, Event: {}", (context.getSource() != null) ? context.getSource().getId() : null, context.getStateMachine().getState().getId(), (context.getTarget() != null) ? context.getTarget().getId() : null, context.getEvent());
    }
}
