package io.github.picodotdev.blogbitix.temporal.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultInventoryActivities implements InventoryActivities {

    private static final Logger logger = LogManager.getLogger(DefaultInventoryActivities.class);

    @Override
    public void bookTickets(Long listingId, int quantity) throws ReserveTicketsException {
        logger.info("Inventory book tickets (listingId: {}, quantity: {})", listingId, quantity);
    }

    @Override
    public void releaseTickets(Long listingId, int quantity) throws ReleaseTicketsException {
        logger.info("Inventory release tickets (listingId: {}, quantity: {})", listingId, quantity);
    }
}
