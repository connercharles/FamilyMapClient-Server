package com.example.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.server.dao.DataAccessException;
import com.example.shared.request.LoadRequest;
import com.example.shared.result.LoadResult;
import com.example.server.service.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadRequestHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            // only accept posts
            if (httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                // Get the request body put into data object
                InputStream reqBody = httpExchange.getRequestBody();
                String reqData = readString(reqBody);
                Gson gsonReq = new Gson();
                LoadRequest loadRequest = gsonReq.fromJson(reqData, LoadRequest.class);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // call service and get the result
                LoadService service = new LoadService();
                LoadResult result = service.load(loadRequest);

                // convert result to json
                Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gsonRes.toJson(result);

                // Write result out to body
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(jsonString, respBody);

                respBody.close();
            }
        } catch (IOException | DataAccessException e) {
            closeOnCatch(httpExchange, e);
        }

    }
}
