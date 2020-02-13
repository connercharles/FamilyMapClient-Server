package com.example.server;

import com.sun.net.httpserver.HttpServer;
import com.example.server.handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private  static void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        server.setExecutor(null);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private static void registerHandlers(HttpServer server) {
        server.createContext("/", new FileRequestHandler());
        server.createContext("/user/register", new RegisterRequestHandler());
        server.createContext("/user/login", new LoginRequestHandler());
        server.createContext("/clear", new ClearRequestHandler());
        server.createContext("/fill", new FillRequestHandler());
        server.createContext("/load", new LoadRequestHandler());
        server.createContext("/person", new PersonRequestHandler());
        server.createContext("/event", new EventRequestHandler());
    }

    public static void main(String[] args){
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Error. Wrong arguments.");
            }
            // port number from arg
            int port = Integer.parseInt(args[0]);
            if (port > 0 && port < 65535)
                startServer(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


}
