package io.github.picodotdev.blogbitix.holamundorabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receive {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(String.format("Received  «%s»", message));
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);

            Thread.sleep(20000);
        } finally {
            channel.close();
            connection.close();
        }
    }
}