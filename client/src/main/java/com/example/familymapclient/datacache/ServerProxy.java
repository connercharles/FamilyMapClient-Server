package com.example.familymapclient.datacache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.example.shared.request.LoginRequest;
import com.example.shared.request.RegisterRequest;
import com.example.shared.result.EventResult;
import com.example.shared.result.LoginResult;
import com.example.shared.result.PersonResult;
import com.example.shared.result.RegisterResult;

import java.io.IOException;
import java.net.URL;

public class ServerProxy {
    private ClientAccess clientAccess;

    public ServerProxy() {
        this.clientAccess = new ClientAccess();
    }

    // post
    // has a request body and a response body
    public LoginResult login(LoginRequest request){
        try {
            URL url = new URL("http://" + DataCache.getInstance().serverHost + ":"
                    + DataCache.getInstance().serverPort + "/user/login");

            // convert result to json
            Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
            String requestBody = gsonRes.toJson(request);

            String respBody = clientAccess.postUrl(url, requestBody);

            // put into LoginResult
            Gson gsonReq = new Gson();
            LoginResult loginResult = gsonReq.fromJson(respBody, LoginResult.class);

            // error checking
            if (loginResult == null) {
                LoginResult errorResult = new LoginResult("Error");
                return errorResult;
            } else if (loginResult.getMessage() != null) {
                return loginResult;
            }

            // contains authToken, personID, username
            DataCache.getInstance().authToken = loginResult.getAuthToken();

            // get all the user's data to store in the cache
            PersonResult personResult = getPersons();
            EventResult eventResult = getEvents();

            // set user into cache and link all people as well
            DataCache.getInstance().findUser(personResult.getData(), loginResult.getPersonID());
            DataCache.getInstance().organizePersons(personResult.getData());
            DataCache.getInstance().organizeEvents(eventResult.getData());

            return loginResult;
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        LoginResult errorResult = new LoginResult("Error");

        return errorResult;
    }

    // post
    // has a request body and a response body
    public RegisterResult register(RegisterRequest request){
        try {
            URL url = new URL("http://" + DataCache.getInstance().serverHost + ":"
                    + DataCache.getInstance().serverPort + "/user/register");

            // convert result to json
            Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
            String requestBody = gsonRes.toJson(request);

            String respBody = clientAccess.postUrl(url, requestBody);

            // put into Register Result
            Gson gsonReq = new Gson();
            RegisterResult registerResult = gsonReq.fromJson(respBody, RegisterResult.class);

            // error checking
            if (registerResult == null) {
                RegisterResult errorResult = new RegisterResult("Error");
                return errorResult;
            } else if (registerResult.getMessage() != null) {
                return registerResult;
            }

            // contains authToken, personID, username
            DataCache.getInstance().authToken = registerResult.getAuthToken();


            // get all the user's data to store in the cache
            PersonResult personResult = getPersons();
            EventResult eventResult = getEvents();

            // set user into cache and link all people as well
            DataCache.getInstance().findUser(personResult.getData(), registerResult.getPersonID());
            DataCache.getInstance().organizePersons(personResult.getData());
            DataCache.getInstance().organizeEvents(eventResult.getData());
            return registerResult;
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        RegisterResult errorResult = new RegisterResult("Error");

        return errorResult;
    }

    // get
    public EventResult getEvents(){
        try{
            URL url = new URL("http://" + DataCache.getInstance().serverHost + ":"
                    + DataCache.getInstance().serverPort + "/events");

            String respBody = clientAccess.getUrl(url);

            // put into Event Result
            Gson gsonReq = new Gson();
            EventResult result = gsonReq.fromJson(respBody, EventResult.class);

            return result;
        } catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        return null;
    }

    // get
    public PersonResult getPersons(){
        try{
            URL url = new URL("http://" + DataCache.getInstance().serverHost + ":"
                    + DataCache.getInstance().serverPort + "/person");

            String respBody = clientAccess.getUrl(url);

            // put into Person Result
            Gson gsonReq = new Gson();
            PersonResult result = gsonReq.fromJson(respBody, PersonResult.class);

            return result;
        } catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        return null;
    }



}
