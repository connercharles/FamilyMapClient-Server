package com.example.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.shared.request.FillRequest;
import com.example.shared.result.FillResult;
import com.example.shared.result.Result;
import com.example.server.service.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillRequestHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            // only accept posts
            if (httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                // get args [0], [1] fill, [2] username, [3] generations
                String urlPath = httpExchange.getRequestURI().toString();
                String[] handleArgs = urlPath.split("/");

                FillRequest fillRequest = new FillRequest();
                fillRequest.setUserName(handleArgs[2]);
                // set default gen to 4
                if (handleArgs.length >= 4) {
                    fillRequest = new FillRequest(handleArgs[2], Integer.parseInt(handleArgs[3]));
                }

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // call service and get the result
                FillService service = new FillService();
                FillResult result = service.fill(fillRequest);

                // convert result to json
                Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gsonRes.toJson(result);

                // Write result out to body
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(jsonString, respBody);

                respBody.close();
            }
        } catch (IOException e) {
            closeOnCatch(httpExchange, e);
        }

    }
}
