package io.github.picodotdev.blogbitix.temporal.activities;

import java.util.Random;

import io.temporal.workflow.Workflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultPaymentActivities implements PaymentActivities {

    private static final Logger logger = LogManager.getLogger(DefaultPaymentActivities.class);

    @Override
    public Long authorizePayment() {
        Long paymentId = new Random().nextLong(0, 1_000_000);
        logger.info("Payments authorize (paymentId: {})", paymentId);
        return paymentId;
    }
}
