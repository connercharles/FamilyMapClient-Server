package com.example.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.server.dao.DataAccessException;
import com.example.shared.request.EventRequest;
import com.example.shared.result.EventResult;
import com.example.shared.result.Result;
import com.example.server.service.EventService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventRequestHandler extends AuthHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            // only accept posts
            if (httpExchange.getRequestMethod().toUpperCase().equals("GET")) {
                // Get the HTTP request headers
                Headers reqHeaders = httpExchange.getRequestHeaders();

                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    if (isAuthorized(authToken)) {
                        // get args [0], [1] event, [2] eventID
                        String urlPath = httpExchange.getRequestURI().toString();
                        String[] handleArgs = urlPath.split("/");

                        EventRequest eventRequest = new EventRequest();
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        // call service and get the result
                        EventService service = new EventService();
                        EventResult result;

                        eventRequest.setAuthToken(authToken);
                        // if it's event/IDNumber
                        if (handleArgs.length >= 3) {
                            // get specific person info
                            eventRequest.seteventID(handleArgs[2]);
                            result = service.event(eventRequest);
                        } else {
                            // if it's the general person
                            result = service.eventAll(eventRequest);
                        }

                        // convert result to json
                        Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
                        String jsonString = gsonRes.toJson(result);

                        // Write result out to body
                        OutputStream respBody = httpExchange.getResponseBody();
                        writeString(jsonString, respBody);

                        respBody.close();

                    } else {
                        // The auth token was invalid somehow, so we return a "not authorized"
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        EventResult result = new EventResult();
                        result.setMessage("Error no authorization.");

                        // convert result to json
                        Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
                        String jsonString = gsonRes.toJson(result);

                        // Write result out to body
                        OutputStream respBody = httpExchange.getResponseBody();
                        writeString(jsonString, respBody);

                        respBody.close();

                    }
                } else {
                    // We did not get an auth token, so we return a "not authorized"
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Result result = new Result();
                    result.setMessage("Error not a GET.");

                    // convert result to json
                    Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gsonRes.toJson(result);

                    // Write result out to body
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(jsonString, respBody);

                    respBody.close();
                }
            }
        } catch (IOException | DataAccessException e) {
            closeOnCatch(httpExchange, e);
        }

    }

}
