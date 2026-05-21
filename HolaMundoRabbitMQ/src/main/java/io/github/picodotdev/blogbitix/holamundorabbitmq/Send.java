package io.github.picodotdev.blogbitix.holamundorabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Send {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 10; ++i) {
                String message = String.format("Hello World at %s", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(String.format("Sent «%s»", message));

                Thread.sleep(1500);
            }
        } finally {
            channel.close();
            connection.close();
        }
    }
}