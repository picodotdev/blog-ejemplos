package io.github.picodotdev.blogbitix.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface FraudActivities {

    @ActivityMethod
    FraudDecide decide(Long orderId);

    @ActivityMethod
    void authorizeFailure(Long orderId);

    @ActivityMethod
    void decision(Long orderId, Long auhtorizeId, FraudDecision decision);

    enum FraudDecide {
        APPROVE, REJECT
    }

    enum FraudDecision {
        APPROVE, REJECT
    }
}
