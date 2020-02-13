package com.example.shared.request;

/**
 * request body for person call
 */
public class PersonRequest {
    /**
     * unique authToken
     */
    private String authToken;

    /**
     * unique id for this person
     */
    private String personID;

    /**
     * gets authToken
     * @return String this authToken
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
     * creates an empty person
     */
    public PersonRequest() {
        this.personID = null;
    }

    /**
     * Creates personrequest
     * @param authToken new authToken
     * @param personID new personID
     */
    public PersonRequest(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    /**
     * personID getter
     */
    public String getpersonID() {
        return personID;
    }

    /**
     * personID setter
     * @param personID new personID string, should be unique
     */
    public void setpersonID(String personID) {
        this.personID = personID;
    }
}
