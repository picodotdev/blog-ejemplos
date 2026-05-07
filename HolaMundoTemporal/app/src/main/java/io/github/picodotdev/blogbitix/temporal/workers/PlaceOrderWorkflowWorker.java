package io.github.picodotdev.blogbitix.temporal.workers;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.picodotdev.blogbitix.temporal.activities.DefaultFraudActivities;
import io.github.picodotdev.blogbitix.temporal.activities.DefaultInventoryActivities;
import io.github.picodotdev.blogbitix.temporal.activities.DefaultOrderActivities;
import io.github.picodotdev.blogbitix.temporal.activities.DefaultPaymentActivities;
import io.github.picodotdev.blogbitix.temporal.workflow.DefaultPlaceOrderWorkflow;
import io.github.picodotdev.blogbitix.temporal.workflow.PlaceOrderWorkflow;

import static io.github.picodotdev.blogbitix.temporal.Main.PLACE_ORDER_WORKFLOW_TASK_QUEUE;

public class PlaceOrderWorkflowWorker {

    private static final Logger logger = LogManager.getLogger(PlaceOrderWorkflowWorker.class);

    private WorkflowServiceStubs service;
    private WorkflowClient client;
    private WorkerFactory factory;

    public PlaceOrderWorkflowWorker() {
        this.service = WorkflowServiceStubs.newLocalServiceStubs();
        this.client = WorkflowClient.newInstance(service);
        this.factory = WorkerFactory.newInstance(client);
    }

    public void start() {
        Worker worker = factory.newWorker(PLACE_ORDER_WORKFLOW_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(DefaultPlaceOrderWorkflow.class);
        worker.registerActivitiesImplementations(new DefaultFraudActivities());
        worker.registerActivitiesImplementations(new DefaultOrderActivities());
        worker.registerActivitiesImplementations(new DefaultPaymentActivities());
        worker.registerActivitiesImplementations(new DefaultInventoryActivities());

        logger.info("Starting PlaceOrderWorkflowWorker...");

        factory.start();
    }

    public void shutdown() {
        factory.shutdown();
    }
}
