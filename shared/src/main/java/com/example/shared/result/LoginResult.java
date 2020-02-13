package com.example.shared.result;

import java.util.Objects;

/**
 * Response body for Login call
 */
public class LoginResult extends Result {
    /**
     * unique personID for user
     */
    private String personID;
    /**
     * unique username for user
     */
    private String userName;
    /**
     * unique authToken for user to login
     */
    private String authToken;
    /**
     * gets username
     * @return string username
     */
    public String getUserName() {
        return userName;
    }
    /**
     * sets username
     * @param userName new username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets personID
     * @return string personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * sets personID
     * @param personID new personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * gets authToken
     * @return string authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * sets authToken
     * @param authToken new authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * creates LoginResult
     * @param personID new personID
     * @param userName new username
     * @param authToken new authToken
     * @param message new message
     */
    public LoginResult(String personID, String userName, String authToken, String message) {
        super(message);
        this.personID = personID;
        this.authToken = authToken;
    }

    /**
     * creates LoginResult
     * @param personID new personID
     * @param userName new username
     */
    public LoginResult(String personID, String userName) {
        super(null);
        this.personID = personID;
        this.userName = userName;
    }

    /**
     * creates LoginResult
     * @param message new message
     */
    public LoginResult(String message) {
        super(message);
    }

    public LoginResult() {
        super(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return personID.equals(that.personID) &&
                userName.equals(that.userName) &&
                authToken.equals(that.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, userName, authToken);
    }
}
