package org.example.server;

import org.example.business.AuthService;
import org.example.business.MediaService;
import org.example.business.UserService;
import org.example.data.AuthRepository;
import org.example.data.MediaRepository;
import org.example.data.UserRepository;
import org.example.service.*;
import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpServer {
    public HttpServer() {
        try{
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8080), 0);

            AuthRepository authRepository = new AuthRepository();
            AuthService authService = new AuthService(authRepository);
            UserRepository userRepository = new UserRepository();
            UserService userService = new UserService(userRepository);
            MediaRepository mediaRepository = new MediaRepository();
            MediaService mediaService = new MediaService(mediaRepository, userRepository);

            server.createContext("/", new RootHandler());
            server.createContext("/api/users/", new AuthHandler(authService,userService));
            server.createContext("/api/media", new MediaHandler(mediaService));
            server.createContext("/api/leaderboard/", new LeaderboardHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8080");
        } catch (IOException e) {
            System.out.println("Error on starting server");
        }
    }
}
