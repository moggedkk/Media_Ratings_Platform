package org.example.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.data.MediaRepository;
import org.example.data.UserRepository;
import org.example.models.Response;
import org.example.security.ExtractMediaId;
import org.example.security.ExtractToken;

import org.example.models.Media;
import java.io.IOException;
import java.util.Objects;

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
            return response.unauthorized("token not correct");
        }
        try{
            Media media = mapper.readValue(exchange.getRequestBody(), Media.class);
            String userId = userRepository.getUserId(user_token);
            if(userId == null){
                return response.notFound("user not found");
            }
            media.setUser_id(Integer.parseInt(userId));
            String mediaId = mediaRepository.createMedia(media);
            if(mediaId != "-1"){
                for(int i = 0; i < media.getGenres().size(); i++){
                    String genreId = mediaRepository.getGenreId(media.getGenres().get(i).name());
                    boolean genreCreation = mediaRepository.createGenre(mediaId, genreId);
                    if(!genreCreation){
                        return response.serverError("Internal server error");
                    }
                }
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return response.ok("{}");
    }
    public Response handleGetMedia(HttpExchange exchange)  {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        String path = exchange.getRequestURI().getPath();
        String user_token = ExtractToken.extractUserToken(exchange);
        if(user_token == null){
            return response.unauthorized("token not correct");
        }
        String userId = userRepository.getUserId(user_token);
        if(userId == null){
            return response.notFound("user not found");
        }
        String mediaId = ExtractMediaId.ExtractId(path);
        try{
            Media media = mediaRepository.getMedia(mediaId);
            if(media.getUser_id() != Integer.parseInt(userId)){
                return  response.unauthorized("not authorized");
            }
            return response.ok(mapper.writeValueAsString(media));
        }catch(JsonProcessingException e){
            return  response.serverError("Internal server error");
        }
    }
    public Response handleDeleteMedia(HttpExchange exchange) {
        Response response = new Response();
        String path = exchange.getRequestURI().getPath();
        String user_token = ExtractToken.extractUserToken(exchange);
        if(user_token == null){
            return response.unauthorized("token not correct");
        }
        String userId = userRepository.getUserId(user_token);
        if(userId == null){
            return response.notFound("user not found");
        }
        String mediaId = ExtractMediaId.ExtractId(path);
        boolean delMedia = mediaRepository.deleteMedia(mediaId);
        if(!delMedia){
            return response.notFound("media not found");
        }
        return response.ok("{}");
    }
    public Response handleUpdateMedia(HttpExchange exchange)  {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        String path = exchange.getRequestURI().getPath();
        String user_token = ExtractToken.extractUserToken(exchange);
        if(user_token == null){
            return response.unauthorized("token not correct");
        }
        String userId = userRepository.getUserId(user_token);
        if(userId == null){
            return response.notFound("user not found");
        }
        String mediaId = ExtractMediaId.ExtractId(path);
        String dbUserId = mediaRepository.getUserId(mediaId);
        if(!Objects.equals(dbUserId, userId)){
            return response.unauthorized("not authorized");
        }
        try {
            Media media = mapper.readValue(exchange.getRequestBody(), Media.class);
            // delete genres
            boolean deleteGenres = mediaRepository.deleteGenre(mediaId);
            // update media
            boolean updateMedia = mediaRepository.updateMedia(media, mediaId);
            // create new genres
            for(int i = 0; i < media.getGenres().size(); i++){
                String genreId = mediaRepository.getGenreId(media.getGenres().get(i).name());
                boolean genreCreation = mediaRepository.createGenre(mediaId, genreId);
                if(!genreCreation){
                    return response.serverError("Internal server error");
                }
            }
            return response.ok("{}");
        }catch(IOException e){
            System.err.println(e.getMessage());
            return response.serverError("Parsing error");
        }
    }
    public Response handleUserFavorites(HttpExchange exchange) {
        Response response = new Response();

        return response;
    }
}
