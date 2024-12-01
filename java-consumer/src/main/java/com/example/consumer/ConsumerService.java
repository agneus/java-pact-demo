package com.example.consumer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConsumerService {

    private final String providerUrl;

    public ConsumerService(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getMikuMessage() throws IOException, JSONException {
        return fetchFieldFromEndpoint("/miku-message", "message");
    }

    public String getMikuPhoneNumber() throws IOException, JSONException {
        return fetchFieldFromEndpoint("/miku-message/phone-number", "phoneNumber");
    }

    private String fetchFieldFromEndpoint(String endpoint, String field) throws IOException, JSONException {
        // Create a URL for the given endpoint
        URL url = new URL(providerUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            Scanner scanner = new Scanner(connection.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            // Parse the JSON response and extract the specified field
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString(field);
        } else {
            throw new IOException("Failed to fetch data from " + endpoint + ". HTTP code: " + responseCode);
        }
    }
}

