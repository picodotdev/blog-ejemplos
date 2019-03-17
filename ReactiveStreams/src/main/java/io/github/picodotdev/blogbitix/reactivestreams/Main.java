package io.github.picodotdev.blogbitix.reactivestreams;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class Main {

    private static class PrintSubscriber implements Flow.Subscriber<Integer> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            System.out.println("Received item: " + item);
            subscription.request(1);
            Sleeper.sleep(1000);
        }

        @Override
        public void onError(Throwable error) {
            error.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("PrintSubscriber completed");
        }
    }

    public static class PowProcessor extends SubmissionPublisher<Integer> implements Flow.Processor<Integer, Integer> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            submit(item * item);
            subscription.request(1);

        }

        @Override
        public void onError(Throwable error) {
            error.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("PowProcessor completed");
            close();
        }
    }

    private static class Sleeper {
        private static void sleep(int time) {
            try {
                Thread.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Flow.Processor<Integer, Integer> processor = new PowProcessor();
        Flow.Subscriber<Integer> subscriber = new PrintSubscriber();

        publisher.subscribe(processor);
        processor.subscribe(subscriber);

        IntStream.range(0, 10).forEach(it -> {
            publisher.submit(it);
            Sleeper.sleep(2000);
        });

        publisher.close();
    }
}
