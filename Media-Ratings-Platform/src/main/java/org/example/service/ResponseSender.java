package org.example.service;

import com.sun.net.httpserver.HttpExchange;
import org.example.models.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public interface ResponseSender {
    static void sendResponse(HttpExchange exchange, Response response) throws IOException {
        if (response.getBody() == null) response.setBody("");
        byte[] responseBytes = response.getBody().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(response.getStatusCode(), responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
