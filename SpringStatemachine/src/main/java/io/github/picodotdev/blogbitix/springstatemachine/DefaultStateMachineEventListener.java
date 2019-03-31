package io.github.picodotdev.blogbitix.springstatemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class DefaultStateMachineEventListener extends StateMachineListenerAdapter<Main.States, Main.Events> {
    
    private static Logger logger = LoggerFactory.getLogger(DefaultStateMachineEventListener.class);
    
    @Override
    public void stateChanged(State<Main.States, Main.Events> from, State<Main.States, Main.Events> to) {
        logger.info("Listener stateChanged from {} to {}", (from != null) ? from.getId() : null, (to != null) ? to.getId() : null);
    }

    @Override
    public void stateEntered(State<Main.States, Main.Events> state) {
        logger.info("Listener stateEntered in {}", state.getId());
    }

    @Override
    public void stateExited(State<Main.States, Main.Events> state) {
        logger.info("Listener stateExited in {}", state.getId());
    }

    @Override
    public void transition(Transition<Main.States, Main.Events> transition) {
        logger.info("Listener transition from {} to {}", (transition.getSource() != null) ? transition.getSource().getId() :null, (transition.getTarget() != null) ? transition.getTarget().getId() : null);
    }

    @Override
    public void transitionStarted(Transition<Main.States, Main.Events> transition) {
        logger.info("Listener transitionStarted from {} to {}", (transition.getSource() != null) ? transition.getSource().getId() : null, (transition.getTarget() != null) ? transition.getTarget().getId() : null);
    }

    @Override
    public void transitionEnded(Transition<Main.States, Main.Events> transition) {
        logger.info("Listener transitionEnded from {} to {}", (transition.getSource() != null) ? transition.getSource().getId() : null, (transition.getTarget() != null) ? transition.getTarget().getId() : null);
    }

    @Override
    public void stateMachineStarted(StateMachine<Main.States, Main.Events> stateMachine) {
        logger.info("Listener stateMachineStarted");
    }

    @Override
    public void stateMachineStopped(StateMachine<Main.States, Main.Events> stateMachine) {
        logger.info("Listener stateMachineStopped");
    }

    @Override
    public void eventNotAccepted(Message<Main.Events> event) {
        logger.info("Listener eventNotAccepted {}", event);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        logger.info("Listener extendedStateChanged {} to {}", key, value);
    }

    @Override
    public void stateMachineError(StateMachine<Main.States, Main.Events> stateMachine, Exception exception) {
        logger.info("Listener stateMachineError {}", exception.getMessage());
    }

    @Override
    public void stateContext(StateContext<Main.States, Main.Events> stateContext) {
        logger.info("Listener stateContext (Message Payload: {}, Message Headers: {}, Variables: {}", (stateContext.getMessage() != null) ? stateContext.getMessage().getPayload() : null, stateContext.getMessageHeaders(), stateContext.getExtendedState().getVariables());
    }    
}
