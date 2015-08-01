package io.github.picodotdev.machinarum;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import io.github.picodotdev.machinarum.Purchase.State;
import io.github.picodotdev.machinarum.Purchase.Trigger;

public class PurchaseTest {

    @Test
    public void testNormal() throws Exception {
        Purchase purchase = new Purchase(5, new BigDecimal("150"));

        purchase.fire(Trigger.RESERVE);
        assertEquals(State.RESERVED, purchase.getState());

        purchase.fire(Trigger.DELIVERY);
        assertEquals(State.TRANSIT, purchase.getState());

        purchase.fire(Trigger.DELIVERIED);
        assertEquals(State.DELIVERIED, purchase.getState());
    }

    @Test
    public void testCancel() throws Exception {
        Purchase purchase = new Purchase(5, new BigDecimal("150"));

        purchase.fire(Trigger.RESERVE);
        assertEquals(State.RESERVED, purchase.getState());

        purchase.fire(Trigger.CANCEL);
        assertEquals(State.CANCELED, purchase.getState());
    }

    @Test
    public void testMethods() throws Exception {
        Purchase purchase = new Purchase(5, new BigDecimal("150"));
        Optional<Map<String,Object>> data = Optional.empty();
        
        Assert.assertTrue(purchase.isState(Purchase.State.CREATED));
        Assert.assertTrue(Arrays.asList(Trigger.RESERVE, Trigger.CANCEL).containsAll(purchase.getTriggers()));
        Assert.assertTrue(purchase.hasTrigger(Trigger.RESERVE));
        Assert.assertTrue(purchase.canFire(Trigger.RESERVE));
        Assert.assertTrue(purchase.canFire(Trigger.RESERVE, data));
        purchase.fire(Trigger.RESERVE);
        purchase.fire(Trigger.CANCEL, data);
        assertEquals(State.CANCELED, purchase.getState());
    }
    
    @Test
    public void testHandlers() throws Exception {
        Purchase purchase = new Purchase(5, new BigDecimal("150"));
        
        purchase.fire(Trigger.DELIVERIED);
    }
}