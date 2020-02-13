package com.example.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
                if (httpExchange.getRequestMethod().toUpperCase().equals("GET")) {
                    String urlPath = httpExchange.getRequestURI().toString(); // part of URL after handle
                    if (urlPath == null || urlPath.equals("/")) {
                        urlPath = "/index.html";
                    }
                    String filePath = "server/web" + urlPath;
                    File file = new File(filePath);
                    if (file.exists()) {
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        // make sure we have a 404 code
                        filePath = "web/HTML/404.html";
                        file = new File(filePath);
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    OutputStream respBody = httpExchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                }
            } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            httpExchange.getRequestBody().close();
            e.printStackTrace();
        }
    }
}
