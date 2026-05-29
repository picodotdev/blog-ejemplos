package io.github.picodotdev.blogbitix.temporal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.picodotdev.blogbitix.temporal.workers.PlaceOrderWorkflowWorker;
import io.github.picodotdev.blogbitix.temporal.workflow.Workflow;

@SpringBootApplication
public class Main implements ApplicationRunner {

    public static final String PLACE_ORDER_WORKFLOW_TASK_QUEUE = "place-order-workflow-task-queue";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        PlaceOrderWorkflowWorker worker = new PlaceOrderWorkflowWorker();
        Workflow workflow = new Workflow();

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            executor.submit(worker::start);
            Thread.sleep(1000);
            executor.submit(workflow::run);
            Thread.sleep(5000);

            worker.shutdown();
            workflow.shutdown();
        }
    }
}
