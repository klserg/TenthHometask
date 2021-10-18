package org.example;

import org.example.http.HttpClientImpl;
import org.example.http.Response;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HttpClientImpl httpClient = new HttpClientImpl();

        showResponse(httpClient.get("http://google.com/", Map.of("Accept", "text/html")));
        showResponse(httpClient.post("http://google.com/", Map.of("Accept", "text/html"), "123"));
        showResponse(httpClient.post("http://google.com/", Map.of("Accept", "text/html"), "123".getBytes()));
        showResponse(httpClient.put("http://google.com/", Map.of("Accept", "text/html"), "123"));
        showResponse(httpClient.put("http://google.com/", Map.of("Accept", "text/html"), "123".getBytes()));
        showResponse(httpClient.delete("http://google.com/", Map.of("Accept", "text/html"), "123"));
        showResponse(httpClient.delete("http://google.com/", Map.of("Accept", "text/html"), "123".getBytes()));
    }

    public static void showResponse(Response response) {
        System.out.printf("Status: %s %s\n", response.getStatusCode(), response.getStatusText());
        for (Map.Entry<String, String> header: response.getHeaders().entrySet()) {
            System.out.printf("%s: %s\n", header.getKey(), header.getValue());
        }
        System.out.printf("Payload: %s", response.getPayload());
    }
}
