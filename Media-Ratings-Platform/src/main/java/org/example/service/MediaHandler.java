package org.example.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.MediaService;
import org.example.models.Response;

import java.io.IOException;

public class MediaHandler implements HttpHandler{
    Response response = new Response();
    MediaService mediaService;
    public  MediaHandler(MediaService mediaService) {
        this.mediaService = mediaService;
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
                putRequests(exchange);
                break;
            case "DELETE":
                delRequests(exchange);
                break;
            default:
                response.notFound("Method not supported");
        }
    }
    public void postRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.endsWith("/media")){
            response = mediaService.handleCreateMedia(exchange);
        }else{
            response.notFound("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
    public void getRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.matches("/api/media/[0-9]+")){
            response = mediaService.handleGetMedia(exchange);
        }else{
            response.notFound("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
    public void putRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.matches("/api/media/[0-9]+")){
            response = mediaService.handleUpdateMedia(exchange);
        }else{
            response.notFound("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
    public void delRequests(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.matches("/api/media/[0-9]+")){
            response = mediaService.handleDeleteMedia(exchange);
        }  else{
            response.notFound("Method not supported");
        }
        ResponseSender.sendResponse(exchange, response);
    }
}
