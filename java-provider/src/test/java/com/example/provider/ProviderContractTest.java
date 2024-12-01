package com.example.provider;

import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Provider("HatsuneMikuProvider")
@PactBroker(url = "http://localhost:9292/")
public class ProviderContractTest {
    private static ConfigurableApplicationContext applicationContext;


    @BeforeAll
    static void startProvider() {
        // Start the Spring Boot application
        applicationContext = SpringApplication.run(ProviderService.class);
    }


    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @AfterAll
    static void stopProvider() {
        // Stop the Spring Boot application after all tests
        if (applicationContext != null) {
            applicationContext.close();
        }
    }
}

