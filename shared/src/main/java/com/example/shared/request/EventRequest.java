package com.example.shared.request;

/**
 * request body for event call
 */
public class EventRequest {
    /**
     * unique authToken
     */
    private String authToken;

    /**
     * unique id for this event
     */
    private String eventID;

    /**
     * gets authToken
     * @return AuthToken this authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * sets AuthToken
     * @param authToken new AuthToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * creates an empty event
     */
    public EventRequest() {
    }

    /**
     * Creates eventrequest
     * @param authToken new authToken
     * @param eventID new eventID
     */
    public EventRequest(String authToken, String eventID) {
        this.authToken = authToken;
        this.eventID = eventID;
    }

    /**
     * eventID getter
     */
    public String geteventID() {
        return eventID;
    }

    /**
     * eventID setter
     * @param eventID new eventID string, should be unique
     */
    public void seteventID(String eventID) {
        this.eventID = eventID;
    }

}
