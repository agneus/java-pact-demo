package com.example.provider;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class ProviderService {

    public static void main(String[] args) {
        SpringApplication.run(ProviderService.class, args);
    }
}

@RestController
@RequestMapping("/miku-message")
class MikuMessageController {


    @GetMapping
    public Response getMikuMessage() {
        return new Response("Hatsune Miku says hello!", "message");
    }

    @GetMapping("/phone-number")
    public Response getMikuPhoneNumber() {
        return new Response("+81-123-456-7890", "phoneNumber");
    }
}

class Response {
    private Object value;
    private String key;

    public Response(Object value, String key) {
        this.value = value;
        this.key = key;
    }

    @JsonAnyGetter
    public Map<String, Object> getDynamicJson() {
        return Map.of(key, value);
    }
}
