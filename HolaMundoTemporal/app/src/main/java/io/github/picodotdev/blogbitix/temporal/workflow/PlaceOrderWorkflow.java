package io.github.picodotdev.blogbitix.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PlaceOrderWorkflow {

    @WorkflowMethod
    void placeOrder(Long listingId, int quantity);
}
