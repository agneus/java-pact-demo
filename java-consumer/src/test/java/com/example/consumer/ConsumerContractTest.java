package com.example.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class) // Enables Pact functionality in tests
@PactTestFor(providerName = "HatsuneMikuProvider", port = "8080")
public class ConsumerContractTest {

    @Pact(consumer = "HatsuneMikuConsumer")
    public V4Pact createPact(PactDslWithProvider builder) {
        return builder
                //.given("Miku is ready to say hello")
                .uponReceiving("A request for Miku's message")
                .path("/miku-message")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody().stringType("message", "Hatsune Miku says hello!"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createPact", port = "8080")
    public void testConsumerService(MockServer mockServer) throws IOException {
        // Use the mock server URL in the ConsumerService
        ConsumerService consumerService = new ConsumerService(mockServer.getUrl());

        // Call the ConsumerService and validate the response
        String response = consumerService.getMikuMessage();
        assertEquals("Hatsune Miku says hello!", response);
    }

    /*
    @Pact(consumer = "HatsuneMikuConsumer")
    public V4Pact createPactForPhoneNumber(PactDslWithProvider builder) {
        return builder
                .uponReceiving("A request for Miku's phone number")
                .path("/miku-message/phone-number")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody().stringType("phoneNumber", "+81-123-456-7890"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createPactForPhoneNumber", port = "8080")
    public void testGetMikuPhoneNumber(MockServer mockServer) throws IOException {
        ConsumerService consumerService = new ConsumerService(mockServer.getUrl());
        String response = consumerService.getMikuPhoneNumber();
        assertEquals("+81-123-456-7890", response);
    }
    */
}

