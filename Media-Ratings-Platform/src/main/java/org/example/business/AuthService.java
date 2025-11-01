package org.example.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.data.AuthRepository;
import org.example.json.JsonConverter;
import org.example.models.Response;
import org.example.models.Token;
import org.example.models.User;
import org.example.security.HashManager;

import java.io.IOException;

public class AuthService {
    private final AuthRepository authRepository;
    HashManager hashManager = new HashManager();
    Token tokenManager = new Token();
    private  final JsonConverter jsonConverter = new JsonConverter();
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Response handleLogin(HttpExchange exchange) {
        ObjectMapper mapper = new ObjectMapper();
        Token token = new Token();
        Response response = new Response();
        try {
            User user = mapper.readValue(exchange.getRequestBody(), User.class);
            String passwordHash = authRepository.getHashedPassword(user.getUsername());
            if(hashManager.checkPassword(user.getPassword(), passwordHash)){
                token.setTokenValue(authRepository.getToken(user.getUsername()));
                response.setBody(jsonConverter.toJson(token));
                response.setStatusCode(200);
            }else{
                response.setStatusCode(403);
            }
                return response;
        }catch(IOException ex){
            ex.printStackTrace();
            response.setStatusCode(500);
            response.setBody(ex.getMessage());
            return response;
        }
    }
    public Response handleRegistration(HttpExchange exchange){
        ObjectMapper mapper = new ObjectMapper();
        Response response = new Response();
        try {
            User requestBody = mapper.readValue(exchange.getRequestBody(), User.class);
            String hashedPassword = hashManager.hashPassword(requestBody.getPassword());
            requestBody.setPassword(hashedPassword);
            String user_token = tokenManager.CreateToken(requestBody.getUsername());
            boolean createUser = authRepository.createUser(requestBody, user_token);
            if(createUser){
                response.setStatusCode(200);
                response.setBody(null);
            }else{
                response.setStatusCode(400);
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }
}
