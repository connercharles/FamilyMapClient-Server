package com.example.server;

import com.example.shared.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.EventRequest;
import com.example.shared.result.EventResult;
import com.example.server.service.EventService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class EventServiceTest extends ServiceTest {
    private ArrayList<Event> events;

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void eventPass() throws Exception {
        EventRequest eventRequest = new EventRequest();
        eventRequest.seteventID(event1.getEventID());
        eventRequest.setAuthToken(authToken1.getAuthKey());

        EventService eventService = new EventService();
        EventResult eventResult = eventService.event(eventRequest);

        EventResult compareTest = new EventResult();
        compareTest.copyInfo(event1);

        assertEquals(compareTest, eventResult);
    }

    @Test
    public void eventFail() throws Exception {
        EventRequest eventRequest = new EventRequest();
        eventRequest.seteventID(event1.getEventID());
        eventRequest.setAuthToken(authToken1.getAuthKey());

        EventService eventService = new EventService();
        EventResult eventResult = eventService.event(eventRequest);

        EventResult compareTest = new EventResult();
        compareTest.copyInfo(event2);

        assertNotEquals(compareTest, eventResult);
    }

    @Test
    public void eventAllPass() throws Exception {
        events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);

        EventRequest eventRequest = new EventRequest();
        eventRequest.seteventID(event1.getEventID());
        eventRequest.setAuthToken(authToken1.getAuthKey());

        EventService eventService = new EventService();
        EventResult eventResult = eventService.eventAll(eventRequest);

        EventResult compareTest = new EventResult(events);

        assertEquals(compareTest, eventResult);
    }

    @Test
    public void eventAllFail() throws Exception {
        events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        events.add(event4);
        events.add(event5);
        events.add(event6);

        EventRequest eventRequest = new EventRequest();
        eventRequest.seteventID(event1.getEventID());
        eventRequest.setAuthToken(authToken1.getAuthKey());

        EventService eventService = new EventService();
        EventResult eventResult = eventService.eventAll(eventRequest);

        EventResult compareTest = new EventResult(events);

        assertNotEquals(compareTest, eventResult);
    }


}