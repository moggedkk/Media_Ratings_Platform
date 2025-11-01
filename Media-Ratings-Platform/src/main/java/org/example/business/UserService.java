package org.example.business;

import com.sun.net.httpserver.HttpExchange;
import org.example.data.UserRepository;
import org.example.models.Response;
import org.example.security.ExtractId;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Response handleUserProfile(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String userId = ExtractId.extractUserProfileId(path);
        System.out.println("User Profile Id: " + userId);
        Response response = new Response();

        return response;
    }
    public Response handleUserRatings(HttpExchange exchange) {
        Response response = new Response();
        return response;
    }
    public Response handleUserFavorites(HttpExchange exchange) {
        Response response = new Response();
        return response;
    }
}
