package org.example.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpClientImpl implements HttpClient {

    public HttpURLConnection sendRequest(String method, String url, Map<String, String> headers, byte[] payload) {
        HttpURLConnection httpConnection = null;
        try{
            URL urlLink = new URL(url);
            httpConnection = (HttpURLConnection) urlLink.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod(method.toUpperCase(Locale.ROOT));
            for (Map.Entry<String, String> header: headers.entrySet()) {
                httpConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            if (payload != null && !method.toUpperCase(Locale.ROOT).equals("GET")) {
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(payload);
                outputStream.flush();
            }
            httpConnection.connect();
            return httpConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return httpConnection;
        }
    }

    public HttpURLConnection sendRequest(String method, String url, Map<String, String> headers) {
        byte [] payload = null;
        return sendRequest(method, url, headers, payload);
    }

    public Map<String, String> getHeaders(HttpURLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        Map<String , String> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> entry: headerFields.entrySet()) {
            headers.put(entry.getKey(), entry.getValue().get(0));
        }
        return headers;
    }

    public String getPayload(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner sc = null;
        try{
            sc = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            throw e;
        }
        while (sc.hasNextLine()) {
            stringBuilder.append(sc.nextLine());
        }
        return stringBuilder.toString();
    }

    @Override
    public Response get(String url, Map<String, String> headers) {
       HttpURLConnection httpConnection = sendRequest("GET", url, headers);
       Response response = new Response();
       try{
           response.setStatusCode(httpConnection.getResponseCode());
           response.setStatusText(httpConnection.getResponseMessage());
           response.setHeaders(getHeaders(httpConnection));
           response.setPayload(getPayload(httpConnection));
           return response;
       } catch (IOException e) {
           e.printStackTrace();
           return response;
       }
    }

    @Override
    public Response post(String url, Map<String, String> headers, byte[] payload) {
        HttpURLConnection connection = sendRequest("POST", url, headers, payload);
        Response response = new Response();
        try{
            response.setStatusCode(connection.getResponseCode());
            response.setStatusText(connection.getResponseMessage());
            response.setHeaders(getHeaders(connection));
            response.setPayload(getPayload(connection));
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Response post(String url, Map<String, String> headers, String payload) {
        return post(url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Response put(String url, Map<String, String> headers, byte[] payload) {
        HttpURLConnection connection = sendRequest("PUT", url, headers, payload);
        Response response = new Response();
        try{
            response.setStatusCode(connection.getResponseCode());
            response.setStatusText(connection.getResponseMessage());
            response.setHeaders(getHeaders(connection));
            response.setPayload(getPayload(connection));
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Response put(String url, Map<String, String> headers, String payload) {
        return put(url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Response delete(String url, Map<String, String> headers, byte[] payload) {
        HttpURLConnection connection = sendRequest("DELETE", url, headers, payload);
        Response response = new Response();
        try{
            response.setStatusCode(connection.getResponseCode());
            response.setStatusText(connection.getResponseMessage());
            response.setHeaders(getHeaders(connection));
            response.setPayload(getPayload(connection));
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Response delete(String url, Map<String, String> headers, String payload) {
        return delete(url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }
}
