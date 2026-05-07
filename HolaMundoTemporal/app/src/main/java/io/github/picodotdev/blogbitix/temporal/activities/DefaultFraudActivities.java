package io.github.picodotdev.blogbitix.temporal.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultFraudActivities implements FraudActivities {

    private static final Logger logger = LogManager.getLogger(DefaultFraudActivities.class);

    @Override
    public FraudDecide decide(Long orderId) {
        logger.info("Fraud decide (orderId: {})", orderId);
        return FraudDecide.APPROVE;
    }

    @Override
    public void authorizeFailure(Long orderId) {
        logger.info("Fraud authorize failure (orderId: {})", orderId);
    }

    @Override
    public void decision(Long orderId, Long authorizeId, FraudDecision decision) {
        logger.info("Fraud decision (orderId: {}, authorizeId: {}, decision: {})", orderId, authorizeId, decision);
    }
}
