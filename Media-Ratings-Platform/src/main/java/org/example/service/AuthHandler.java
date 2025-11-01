package org.example.service;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.AuthService;
import org.example.business.UserService;
import org.example.models.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AuthHandler implements HttpHandler, ResponseSender {
    private final AuthService authService;
    private final UserService userService;
    Response response = new Response();
    public AuthHandler(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        System.out.println("Incoming " + method + " on " + path);
        switch(method){
            case "POST":
                postRequests(exchange);
                break;
            case "GET":
                getRequests(exchange);
                break;
            case "PUT":
                break;
            default:
                response.setStatusCode(404);
                response.setBody("Method not supported");
        }
    }
    // Post
    private void postRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.endsWith("/login")){
            response = authService.handleLogin(exchange);
        } else if (path.endsWith("/register")) {
            response = authService.handleRegistration(exchange);
        }else{
            response.setStatusCode(400);
            response.setBody("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
    // Get
    private void getRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.endsWith("/profile")){
            response = userService.handleUserProfile(exchange);
        } else if (path.endsWith("/ratings")) {
            response = userService.handleUserRatings(exchange);
        }else if (path.endsWith("/favorites")) {
            response = userService.handleUserFavorites(exchange);
        }else{
            response.setStatusCode(400);
            response.setBody("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
}
