package org.example.security;

import com.sun.net.httpserver.HttpExchange;

public interface ExtractToken {
    static String extractUserToken(HttpExchange exchange) {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            return null;
        }
        String[] parts = authHeader.split(" ");
        authHeader = parts[1];
        return authHeader;
    }
}
