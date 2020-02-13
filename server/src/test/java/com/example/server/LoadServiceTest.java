package com.example.server;

import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.example.shared.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.LoadRequest;
import com.example.shared.result.LoadResult;
import com.example.server.service.LoadService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class LoadServiceTest extends ServiceTest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();

        users = new ArrayList<>();
        persons = new ArrayList<>();
        events = new ArrayList<>();

        users.add(user1);
        users.add(user2);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);

    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void loadPass() throws Exception {
        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadResult loadResult = new LoadResult();
        loadResult.setMessage(users.size(), persons.size(), events.size());

        LoadService loadService = new LoadService();
        LoadResult result = loadService.load(loadRequest);

        assertEquals(loadResult.getMessage(), result.getMessage());
    }

    @Test
    public void loadFail() throws Exception {
        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadResult loadResult = new LoadResult();
        loadResult.setMessage(0, 0, 0);

        LoadService loadService = new LoadService();
        LoadResult result = loadService.load(loadRequest);

        assertNotEquals(loadResult.getMessage(), result.getMessage());
    }


}