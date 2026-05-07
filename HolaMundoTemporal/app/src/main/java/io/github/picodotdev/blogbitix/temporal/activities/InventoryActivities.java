package io.github.picodotdev.blogbitix.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface InventoryActivities {

    @ActivityMethod
    void bookTickets(Long listingId, int quantity) throws ReserveTicketsException;

    @ActivityMethod
    void releaseTickets(Long listingId, int quantity) throws ReleaseTicketsException;
}
