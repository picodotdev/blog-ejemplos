package io.github.picodotdev.blogbitix.temporal.workflow;

import java.util.UUID;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import static io.github.picodotdev.blogbitix.temporal.Main.PLACE_ORDER_WORKFLOW_TASK_QUEUE;

public class Workflow {

    private WorkflowServiceStubs service;
    private WorkflowClient client;

    public Workflow() {
        this.service = WorkflowServiceStubs.newLocalServiceStubs();
        this.client = WorkflowClient.newInstance(service);
    }

    public void run() {
        UUID uuid = UUID.randomUUID();
        PlaceOrderWorkflow workflow = client.newWorkflowStub(PlaceOrderWorkflow.class,
                                                             WorkflowOptions.newBuilder()
                                                                            .setTaskQueue(PLACE_ORDER_WORKFLOW_TASK_QUEUE)
                                                                            .setWorkflowId(uuid.toString())
                                                                            .build()
                                                            );

        workflow.placeOrder(1L, 3);
    }

    public void shutdown() {
        service.shutdown();
        service.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
    }
}
