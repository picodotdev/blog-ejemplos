package io.github.picodotdev.machinarum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Purchase implements Subject<Purchase.State> {

    enum State {
        CREATED, RESERVED, TRANSIT, DELIVERIED, CANCELED
    }

    enum Trigger {
        RESERVE, DELIVERY, DELIVERIED, CANCEL
    }

    static {
        StateMachineBuilder<State, Trigger, Purchase, Optional<Map<String, Object>>> smb = new StateMachineBuilder<>();

        smb.state(State.CREATED).permit(Trigger.RESERVE, Purchase::reserve).permit(Trigger.CANCEL, State.CANCELED);
        smb.state(State.RESERVED).permit(Trigger.DELIVERY, State.TRANSIT, Purchase::isDeliverable).permit(Trigger.CANCEL, State.CANCELED);
        smb.state(State.TRANSIT).permit(Trigger.DELIVERIED, State.DELIVERIED);
        smb.state(State.DELIVERIED).enter(Purchase::deliveried);
        smb.state(State.CANCELED);
        
        smb.unhandledTriggerHandler(Purchase::onUnhandledTrigger).exceptionHandler(Purchase::onException);
        
        stateMachine = smb.build();
    }

    private static StateMachine<State, Trigger, Purchase, Optional<Map<String, Object>>> stateMachine;
    private static BigDecimal PREMIUM_DELIVERY = new BigDecimal("100");

    private LocalDateTime date;
    private State state;
    private int items;
    private BigDecimal amount;

    public Purchase(int items, BigDecimal amount) {
        this.date = LocalDateTime.now();
        this.state = State.CREATED;
        this.items = items;
        this.amount = amount;
    }

    public StateMachine<State, Trigger, Purchase, Optional<Map<String, Object>>> getStateMachine() {
        return stateMachine;
    }

    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    // StateMachine methods
    public boolean isState(State state) {
        return getStateMachine().isState(this, state);        
    }
    
    public Set<Trigger> getTriggers() {
        return getStateMachine().getTriggers(this);        
    }
    
    public boolean hasTrigger(Trigger trigger) {
        return getStateMachine().hasTrigger(this, trigger);        
    }
    
    public boolean canFire(Trigger trigger) {
        return getStateMachine().canFire(this, trigger);
    }
    
    public boolean canFire(Trigger trigger, Optional<Map<String, Object>> data) {
        return getStateMachine().canFire(this, trigger, data);
    }
    
    public void fire(Trigger trigger) {
        getStateMachine().fire(this, trigger);
    }
    
    public void fire(Trigger trigger, Optional<Map<String, Object>> data) {
        getStateMachine().fire(this, trigger, data);
    }
    
    private void onUnhandledTrigger(Trigger trigger, Optional<Map<String, Object>> data) {
        System.out.printf("UnhandledTrigger %s\n", trigger);
    }
    
    private void onException(Trigger trigger, Optional<Map<String, Object>> data) {
        System.out.printf("Execption %s\n", trigger);
    }

    // Business StateMachine methods
    private boolean isDeliverable(Optional<Map<String, Object>> m) {
        return date.plusMinutes(60).isAfter(LocalDateTime.now());
    }    

    private boolean hasStock(Optional<Map<String, Object>> m) {
        return items <= 5;
    }

    private State reserve(Optional<Map<String, Object>> m) {
        return (hasStock(m))?State.RESERVED:State.CANCELED;
    }
    
    private void deliveried(Optional<Map<String, Object>> m) {
        System.out.println("Deliveried");
    }
}