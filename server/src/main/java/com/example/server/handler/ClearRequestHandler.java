package com.example.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.server.dao.DataAccessException;
import com.example.shared.result.ClearResult;
import com.example.shared.result.Result;
import com.example.server.service.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearRequestHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            // only accept posts
            if (httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                // I guess it worked
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // call service and get the result
                ClearService service = new ClearService();
                ClearResult result = service.clear();

                // convert result to json
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(result);

                // Get the response body output stream.
                OutputStream respBody = httpExchange.getResponseBody();
                // Write the JSON string to the output stream.
                writeString(jsonString, respBody);
                // Close the output stream.  This is how Java knows we are done
                // sending data and the response is complete
                respBody.close();
            }
        } catch (IOException | DataAccessException e) {
            closeOnCatch(httpExchange, e);
        }

    }
}
