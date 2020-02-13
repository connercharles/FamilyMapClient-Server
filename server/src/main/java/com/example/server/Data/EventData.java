package com.example.server.Data;


public class EventData {
    public EventParts[] data;

    public EventParts[] getData() {
        return data;
    }

    public void setData(EventParts[] data) {
        this.data = data;
    }

    public EventData(EventParts[] data) {
        this.data = data;
    }

    public EventData() {
    }
}
