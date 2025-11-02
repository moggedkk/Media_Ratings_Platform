package org.example.models;

import lombok.Getter;
import lombok.Setter;

public class Response {
    @Getter @Setter
    int statusCode;
    @Getter @Setter
    String body;

    public Response ok(String body) {
        Response r = new Response();
        r.setStatusCode(200);
        r.setBody(body);
        return r;
    }

    public Response unauthorized(String message) {
        Response r = new Response();
        r.setStatusCode(401);
        r.setBody("{\"error\": \"" + message + "\"}");
        return r;
    }

    public Response notFound(String message) {
        Response r = new Response();
        r.setStatusCode(404);
        r.setBody("{\"error\": \"" + message + "\"}");
        return r;
    }

    public Response serverError(String message) {
        Response r = new Response();
        r.setStatusCode(500);
        r.setBody("{\"error\": \"" + message + "\"}");
        return r;
    }
}
