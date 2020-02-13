package com.example.shared.model;

//import Data.EventParts;

public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event(Event copy) {
        this.eventID = copy.eventID;
        this.associatedUsername = copy.associatedUsername;
        this.personID = copy.personID;
        this.latitude = copy.latitude;
        this.longitude = copy.longitude;
        this.country = copy.country;
        this.city = copy.city;
        this.eventType = copy.eventType;
        this.year = copy.year;
    }

    public Event(String eventID, String username, String personID, String latitude, String longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

//    public Event(EventParts ep) {
//        this.longitude = ep.longitude;
//        this.latitude = ep.latitude;
//        this.city = ep.city;
//        this.country = ep.country;
//    }

    public Event() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getUsername().equals(getUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude().equals(getLatitude()) &&
                    oEvent.getLongitude().equals(getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
