package io.github.picodotdev.blogbitix.temporal.workflow;

import java.time.Duration;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import io.github.picodotdev.blogbitix.temporal.activities.FraudActivities;
import io.github.picodotdev.blogbitix.temporal.activities.FraudActivities.FraudDecide;
import io.github.picodotdev.blogbitix.temporal.activities.FraudActivities.FraudDecision;
import io.github.picodotdev.blogbitix.temporal.activities.InventoryActivities;
import io.github.picodotdev.blogbitix.temporal.activities.OrderActivities;
import io.github.picodotdev.blogbitix.temporal.activities.PaymentActivities;
import io.github.picodotdev.blogbitix.temporal.activities.PaymentAuthorizationException;

public class DefaultPlaceOrderWorkflow implements PlaceOrderWorkflow {

    private final FraudActivities fraudActivities = Workflow.newActivityStub(FraudActivities.class,
                                                                             ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build());

    private final InventoryActivities inventoryActivities = Workflow.newActivityStub(InventoryActivities.class,
                                                                                     ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build());

    private final OrderActivities orderActivities = Workflow.newActivityStub(OrderActivities.class,
                                                                             ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build());

    private final PaymentActivities paymentActivities = Workflow.newActivityStub(PaymentActivities.class,
                                                                                 ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build());

    @Override
    public void placeOrder(Long listingId, int quantity) {
        Long orderId = orderActivities.generateOrderId();

        FraudDecide fraudDecide = fraudActivities.decide(orderId);
        if (fraudDecide == FraudDecide.REJECT) {
            fraudActivities.decision(orderId, null, FraudDecision.REJECT);
            return;
        }

        try {
            inventoryActivities.bookTickets(listingId, quantity);
            Long authorizeId = paymentActivities.authorizePayment();

            orderActivities.createOrder(orderId, listingId, quantity);
            fraudActivities.decision(orderId, authorizeId, FraudDecision.APPROVE);
            orderActivities.approveOrder(orderId);
        } catch (PaymentAuthorizationException e) {
            inventoryActivities.releaseTickets(listingId, quantity);
            fraudActivities.authorizeFailure(orderId);
        }
    }
}
