# REST Contract Testing Demo

## Overview

This project demonstrates a REST-based service that provides Hatsune Miku's message and phone number. It utilizes **consumer-driven contract testing** with **Pact** to ensure the provider meets the consumer's expectations.

---

## Components

### 1. Consumer

The consumer simulates an application that interacts with the REST service. It generates a **Pact contract** during testing to define the expected behavior of the provider.

#### **Consumer Methods**
- **`getMikuMessage()`**:
  - Sends a `GET` request to `/miku-message`.
  - Expects a response:
    ```json
    {
      "message": "Hatsune Miku says hello!"
    }
    ```

- **`getMikuPhoneNumber()`**:
  - Sends a `GET` request to `/miku-message/phone-number`.
  - Expects a response:
    ```json
    {
      "phoneNumber": "+81-123-456-7890"
    }
    ```

---

### 2. Provider

The provider is a RESTful service implemented with **Spring Boot**. It fulfills the contract generated by the consumer, ensuring the specified endpoints return the expected data.

---

## Contract Testing

### Workflow

1. **Consumer Test**:
   - Simulates the consumer's interaction with the REST service.
   - Generates a **Pact file** defining expected requests and responses.

2. **Provider Test**:
   - Starts the provider service.
   - Verifies the provider's behavior matches the Pact contract for all defined interactions.

### Tests

- **Consumer Test**:
  - Verifies the consumer generates correct requests and handles responses.
  - Produces a Pact file stored in `target/pacts`.

- **Provider Test**:
  - Runs the provider against the Pact file.
  - Verifies endpoints respond as expected.

---

## How it Works

1. **The Consumer** defines requests for `/miku-message` and `/miku-message/phone-number`:
   - Pact generates a contract capturing these interactions.
   
2. **The Provider** tests itself against this contract:
   - Ensures it satisfies the consumer's requirements.

This approach ensures seamless integration between the consumer and provider while catching potential issues early.

