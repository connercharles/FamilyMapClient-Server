package com.example.shared.result;

import com.example.shared.model.Event;

import java.util.ArrayList;
import java.util.Objects;

/**
 * response body for event call
 */
public class EventResult extends Result {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;
    private ArrayList<Event> data;

    public EventResult(String eventID, String username, String personID, String latitude, String longitude,
                 String country, String city, String eventType, int year, String message, ArrayList<Event> events) {
        super(message);
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.data = events;
    }

    public EventResult(ArrayList<Event> events) {
        super(null);
        this.eventID = null;
        this.associatedUsername = null;
        this.personID = null;
        this.latitude = null;
        this.longitude = null;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = null;
        this.data = events;
    }

    public EventResult() {
        super(null);
    }

    public void copyInfo(Event event) {
        this.eventID = event.getEventID();
        this.associatedUsername = event.getUsername();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }


        /**
         * Get the Event ID
         */
    public String getEventID() {
        return eventID;
    }


    /**
     * Set the Event ID
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }


    /**
     * Get the user's name
     */
    public String getUsername() {
        return associatedUsername;
    }


    /**
     * Set the user's name
     */
    public void setUsername(String username) {
        this.associatedUsername = username;
    }


    /**
     * Get the Person's ID
     */
    public String getPersonID() {
        return personID;
    }


    /**
     * Set the Person's ID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }


    /**
     * Get the Latitude
     */
    public String getLatitude() {
        return latitude;
    }


    /**
     * Set the Latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    /**
     * Get the Longitude
     */
    public String getLongitude() {
        return longitude;
    }


    /**
     * Set the Longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    /**
     * Get the Country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Set the Country
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Get the City
     */
    public String getCity() {
        return city;
    }


    /**
     * Set the City
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Get Event Type
     */
    public String getEventType() {
        return eventType;
    }


    /**
     * Set the Event Type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    /**
     * Get the Year
     */
    public int getYear() {
        return year;
    }


    /**
     * Set the Year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * sets events
     * @return arraylist of all events
     */
    public ArrayList<Event> getData() {
        return data;
    }

    /**
     * sets events
     * @param events new events
     */
    public void setData(ArrayList<Event> events) {
        this.data = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult that = (EventResult) o;
        return Objects.equals(eventID, that.eventID) &&
                Objects.equals(associatedUsername, that.associatedUsername) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(year, that.year) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }
}
