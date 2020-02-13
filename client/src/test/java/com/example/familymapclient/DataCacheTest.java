package com.example.familymapclient;

import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.datacache.ServerProxy;
import com.example.familymapclient.model.EventCli;
import com.example.familymapclient.model.PersonCli;
import com.example.shared.request.LoginRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


public class DataCacheTest {
    // assuming that all that is in the database is the sheila parker data from the test drivers

    @BeforeEach
    public void setUp() {
        DataCache.getInstance().serverHost = "localhost";
        DataCache.getInstance().serverPort = "8080";

        // login
        ServerProxy serverProxy = new ServerProxy();
        LoginRequest loginRequest = new LoginRequest("sheila", "parker");
        serverProxy.login(loginRequest);
    }

    @AfterEach
    public void tearDown() {
        // log out
        DataCache.getInstance().selfDestruct();
    }


    @Test
    public void familyRelationsPass(){
        // check if sheila has her husband and parents
        PersonCli user = DataCache.getInstance().user;

        assertNotNull(user.getSpouse());
        assertEquals("Davis_Hyer", user.getSpouse().getpersonID());

        assertNotNull(user.getFather());
        assertEquals("Blaine_McGary", user.getFather().getpersonID());

        assertNotNull(user.getMother());
        assertEquals("Betty_White", user.getMother().getpersonID());
    }

    @Test
    public void familyRelationsFail(){
        // check if sheila has her husband and parents
        PersonCli user = DataCache.getInstance().user;

        assertNotNull(user.getSpouse());
        assertNotNull(user.getFather());
        assertNotNull(user.getMother());
    }

    @Test
    public void filterEventsPass(){
        // change settings
        DataCache.getInstance().femaleLines = false;
        DataCache.getInstance().motherLines = false;

        // check if there's an event that should be there
        ArrayList<EventCli> events = DataCache.getInstance().getFilteredEvents();
        // father's father's event
        for (EventCli event : events) {
            if (event.getEventID().equals("Rodham_Marriage")){
                // a little redundant but just to make sure it gets called and then done!
                assertEquals("Rodham_Marriage", event.getEventID());
                return;
            }
        }

        fail();
    }

    @Test
    public void filterEventsFail(){
        // change settings
        DataCache.getInstance().femaleLines = false;
        DataCache.getInstance().motherLines = false;

        // check if there's an event that shouldn't be there
        ArrayList<EventCli> events = DataCache.getInstance().getFilteredEvents();
        // 1. mother's mother's event
        // 2. mother's father's event
        for (EventCli event : events) {
            assertNotEquals("Mrs_Jones_Surf", event.getEventID());
            assertNotEquals("Jones_Frog", event.getEventID());
        }
    }

    @Test
    public void sortEventPass(){
        PersonCli mrsJones = null;
        // get Mrs Jones
        for (PersonCli person : DataCache.getInstance().getAllPeople()) {
            if(person.getpersonID().equals("Mrs_Jones")){
                mrsJones = person;
            }
        }

        assertNotNull(mrsJones);
        Collections.sort(mrsJones.getEvents());

        assertEquals(2000, mrsJones.getEvents().get(0).getYear());
    }

    @Test
    public void sortEventFail(){
        PersonCli mrsJones = null;
        // get Mrs Jones
        for (PersonCli person : DataCache.getInstance().getAllPeople()) {
            if(person.getpersonID().equals("Mrs_Jones")){
                mrsJones = person;
            }
        }

        assertNotNull(mrsJones);
        Collections.sort(mrsJones.getEvents());

        assertNotEquals(2012, mrsJones.getEvents().get(0).getYear());
    }

    @Test
    public void searchPeoplePass(){
        ArrayList<PersonCli> result = DataCache.getInstance().searchPeople("ham");
        assertEquals(2, result.size());

        ArrayList<PersonCli> result2 = DataCache.getInstance().searchPeople("An");
        assertEquals(1, result2.size());
    }

    @Test
    public void searchPeopleFail(){
        ArrayList<PersonCli> result = DataCache.getInstance().searchPeople("jones");
        assertNotEquals(11, result.size());

        ArrayList<PersonCli> result2 = DataCache.getInstance().searchPeople("i");
        assertNotEquals(5, result2.size());
    }

    @Test
    public void searchEventPass(){
        ArrayList<EventCli> result = DataCache.getInstance().searchEvents("ham");
        assertEquals(2, result.size());

        ArrayList<EventCli> result2 = DataCache.getInstance().searchEvents("20");
        assertEquals(27, result2.size());
    }

    @Test
    public void searchEventFail(){
        ArrayList<PersonCli> result = DataCache.getInstance().searchPeople("");
        assertNotEquals(0, result.size());

        ArrayList<PersonCli> result2 = DataCache.getInstance().searchPeople("2 d");
        assertNotEquals(1, result2.size());
    }
}
