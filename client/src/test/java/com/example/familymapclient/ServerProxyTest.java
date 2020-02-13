package com.example.familymapclient;

import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.datacache.ServerProxy;
import com.example.shared.request.LoginRequest;
import com.example.shared.request.RegisterRequest;
import com.example.shared.result.EventResult;
import com.example.shared.result.LoginResult;
import com.example.shared.result.PersonResult;
import com.example.shared.result.RegisterResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServerProxyTest {
    // assuming that all that is in the database is the sheila parker data from the test drivers

    @BeforeEach
    public void setUp() {
        DataCache.getInstance().serverHost = "localhost";
        DataCache.getInstance().serverPort = "8080";
    }

    // login in pass/fail
    @Test
    public void loginPass(){
        // already in database
        LoginRequest request = new LoginRequest("sheila", "parker");

        ServerProxy proxy = new ServerProxy();
        LoginResult result = proxy.login(request);

        // will not be null if login was successful
        assertNotNull(result.getAuthToken());
        // should have no message
        assertNull(result.getMessage());
    }

    @Test public void loginFail(){
        // not in database
        LoginRequest request = new LoginRequest("ben", "dover");

        ServerProxy proxy = new ServerProxy();
        LoginResult result = proxy.login(request);

        // should be null
        assertNull(result.getAuthToken());
        // should have error message
        assertNotNull(result.getMessage());
    }

    // login in pass/fail
    @Test
    public void registerPass(){
        // not yet in database
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Ben");
        request.setLastName("Dover");
        request.setGender("m");
        request.setEmail("nope");
        request.setUserName("Ben1");
        request.setPassword("Dover1");

        ServerProxy proxy = new ServerProxy();
        RegisterResult result = proxy.register(request);

        // will not be null if sign in was successful
        assertNotNull(result.getAuthToken());
        // should have no message
        assertNull(result.getMessage());
    }

    @Test public void registerFail(){
        // already in database
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Sheila");
        request.setLastName("Parker");
        request.setGender("f");
        request.setEmail("nope");
        request.setUserName("sheila");
        request.setPassword("parker");

        ServerProxy proxy = new ServerProxy();
        RegisterResult result = proxy.register(request);

        // should be null
        assertNull(result.getAuthToken());
        // should have error message
        assertNotNull(result.getMessage());
    }

    @Test
    public void getPeoplePass(){
        // should be 11 people with sheila data
        ServerProxy proxy = new ServerProxy();
        LoginRequest request = new LoginRequest("sheila", "parker");
        proxy.login(request);

        PersonResult result = proxy.getPersons();

        assertEquals(8, result.getData().size());
    }

    @Test
    public void getPeopleFail(){
        // should be 11 people with sheila data
        ServerProxy proxy = new ServerProxy();
        PersonResult result = proxy.getPersons();

        assertNotEquals(3, result.getData().size());
    }

    @Test
    public void getEventsPass(){
        // should be 19 events with sheila data
        ServerProxy proxy = new ServerProxy();
        LoginRequest request = new LoginRequest("sheila", "parker");
        proxy.login(request);

        EventResult result = proxy.getEvents();

        assertEquals(16, result.getData().size());
    }

    @Test
    public void getEventsFail(){
        // should be 19 events with sheila data
        ServerProxy proxy = new ServerProxy();
        EventResult result = proxy.getEvents();

        assertNotEquals(5, result.getData().size());
    }

}







