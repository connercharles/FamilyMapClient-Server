package com.example.familymapclient.model;

import com.example.shared.model.Event;
import com.google.android.gms.maps.model.LatLng;

public class EventCli extends Event implements Comparable<EventCli>{
    private PersonCli person;

    public EventCli() {
        person = null;
    }

    public EventCli(PersonCli person) {
        this.person = person;
    }

    public PersonCli getPerson() {
        return person;
    }

    public void setPerson(PersonCli person) {
        this.person = person;
    }

    public void copyEvent(Event event){
        this.setEventID(event.getEventID());
        this.setPersonID(event.getPersonID());
        this.setCity(event.getCity());
        this.setCountry(event.getCountry());
        this.setEventType(event.getEventType().toUpperCase());
        this.setLatitude(event.getLatitude());
        this.setLongitude(event.getLongitude());
        this.setUsername(event.getUsername());
        this.setYear(event.getYear());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(person.getFirstName() + " " + person.getLastName() + "\n");
        result.append(this.getEventType() + ": " + this.getCity() + ", " + this.getCountry());
        result.append(" (" + this.getYear() + ")");
        return result.toString();
    }

    public String toStringNameless(){
        StringBuilder result = new StringBuilder();
        result.append(this.getEventType() + ": " + this.getCity() + ", " + this.getCountry());
        result.append(" (" + this.getYear() + ")");
        return result.toString();
    }

    @Override
    public int compareTo(EventCli o) {
        return Integer.compare(this.getYear(), o.getYear());
    }

    public LatLng getCoordinates(){
        return new LatLng(Double.parseDouble(this.getLatitude()),
                Double.parseDouble(this.getLongitude()));
    }
}
