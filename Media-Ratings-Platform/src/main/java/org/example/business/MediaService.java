package org.example.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.data.MediaRepository;
import org.example.data.UserRepository;
import org.example.models.Response;
import org.example.security.ExtractToken;

import org.example.models.Media;
import java.io.IOException;

public class MediaService {
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    public MediaService(MediaRepository mediaRepository,  UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }
    public Response handleCreateMedia(HttpExchange exchange){
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        String user_token = ExtractToken.extractUserToken(exchange);
        if(user_token == null){

        }
        try{
            Media media = mapper.readValue(exchange.getRequestBody(), Media.class);
            // get userId if exists
            String userId = userRepository.getUserId(user_token);
            // create media_genre entries


            // insert media
            if(userId == null){

            }

            // insert where usertoken eq given token


        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

        return response;
    }
    public Response handleUserFavorites(HttpExchange exchange) {
        Response response = new Response();
        return response;
    }
}
