package io.github.picodotdev.blogbitix.temporal.activities;

import java.util.Random;

import io.temporal.workflow.Workflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultOrderActivities implements OrderActivities {

    private static final Logger logger = LogManager.getLogger(DefaultOrderActivities.class);

    @Override
    public Long generateOrderId() {
        Long orderId = new Random().nextLong(0, 1_000_000);
        logger.info("Order generate id (orderId: {})", orderId);
        return orderId;
    }

    @Override
    public void createOrder(Long orderId, Long listingId, int quantity) {
        logger.info("Order create (orderId: {}, listingId: {}, quantity; {})", orderId, listingId, quantity);
    }

    @Override
    public OrderStatus approveOrder(Long orderId) {
        logger.info("Order approve (orderId: {})", orderId);
        return OrderStatus.APPROVED;
    }
}
