package io.github.picodotdev.blogbitix.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {

    @ActivityMethod
    Long generateOrderId();

    @ActivityMethod
    void createOrder(Long orderId, Long listingId, int quantity);

    @ActivityMethod
    OrderStatus approveOrder(Long orderId);

    enum OrderStatus {
        CREATED, APPROVED, CANCELLED
    }
}
