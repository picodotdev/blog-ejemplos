package io.github.picodotdev.blogbitix.javapact;

import java.io.IOException;
import java.util.UUID;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "serviceProvider", port = "0")
class ServiceConsumerPactTest {

    @Autowired
    private OkHttpClient okHttpClient;

    private Service service;

    @BeforeEach
    void beforeEach(MockServer mockServer) {
        service = new ServiceClient(okHttpClient, mockServer.getUrl());
    }

    @Pact(consumer="serviceConsumer")
    public RequestResponsePact defaultNameEnglishPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get message with empty name with en-GB locale interaction")
                .method("GET")
                .headers("Accept-Language", "en-GB")
                .path("/message")
                .matchQuery("random", ".*", "16fc8a5f-b9ab-4b26-8049-81a4e7901820")
                .willRespondWith()
                .status(200)
                .body("Hello World!")
                .toPact();
    }

    @Pact(consumer="serviceConsumer")
    public RequestResponsePact customNameEnglishPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get message with a name with en-GB locale interaction")
                .method("GET")
                .headers("Accept-Language", "en-GB")
                .path("/message/Java")
                .matchQuery("random", ".*", "16fc8a5f-b9ab-4b26-8049-81a4e7901820")
                .willRespondWith()
                .status(200)
                .body("Hello Java!")
                .toPact();
    }

    @Pact(consumer="serviceConsumer")
    public RequestResponsePact defaultNameSpanishPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get message with empty name with es-ES locale interaction")
                .method("GET")
                .headers("Accept-Language", "es-ES")
                .path("/message")
                .matchQuery("random", ".*", "16fc8a5f-b9ab-4b26-8049-81a4e7901820")
                .willRespondWith()
                .status(200)
                .body("¡Hola mundo!")
                .toPact();
    }

    @Pact(consumer="serviceConsumer")
    public RequestResponsePact customNameSpanishPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get message with a name with es-ES locale interaction")
                .method("GET")
                .headers("Accept-Language", "es-ES")
                .path("/message/Java")
                .matchQuery("random", ".*", "16fc8a5f-b9ab-4b26-8049-81a4e7901820")
                .willRespondWith()
                .status(200)
                .body("¡Hola Java!")
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "defaultNameEnglishPact")
    void defaultNameEnglish(MockServer ms) throws IOException {
        String result = service.message("en-GB", UUID.randomUUID().toString()).execute().body();
        assertEquals("Hello World!", result);
    }

    @Test
    @PactTestFor(pactMethod = "customNameEnglishPact")
    void customNameEnglish() throws IOException {
        String result = service.message("Java", "en-GB", UUID.randomUUID().toString()).execute().body();
        assertEquals("Hello Java!", result);
    }

    @Test
    @PactTestFor(pactMethod = "defaultNameSpanishPact")
    void defaultNameSpanish(MockServer ms) throws IOException {
        String result = service.message("es-ES", UUID.randomUUID().toString()).execute().body();
        assertEquals("¡Hola mundo!", result);
    }

    @Test
    @PactTestFor(pactMethod = "customNameSpanishPact")
    void customNameSpanish() throws IOException {
        String result = service.message("Java", "es-ES", UUID.randomUUID().toString()).execute().body();
        assertEquals("¡Hola Java!", result);
    }
}
