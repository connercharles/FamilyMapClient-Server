package com.example.shared.request;

import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.example.shared.model.User;

import java.util.ArrayList;

/**
 * request body for load call
 */
public class LoadRequest {
    /**
     * list of all users
     */
    private ArrayList<User> users;
    /**
     * list of all persons
     */
    private ArrayList<Person> persons;
    /**
     * list of all events
     */
    private ArrayList<Event> events;

    /**
     * creates LoadRequest with everything needed
     * @param users new Users
     * @param persons new Persons
     * @param events new Events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     * gets Users
     * @return ArrayList of Users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * sets Users
     * @param users new Users
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * gets persons
     * @return ArrayList of Persons
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * sets persons
     * @param persons new persons
     */
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    /**
     * gets events
     * @return Arraylist of events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * sets events
     * @param events new events
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
