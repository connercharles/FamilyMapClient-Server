package com.example.shared.model;

import java.util.Objects;

/**
 * Represents an AuthToken used for logging in and verifying users
 */
public class AuthToken {
    /**
     * Unique string key
     */
    private String authKey;

    /**
     * Unique username for the user
     */
    private String userName;

    /**
     * Creates an AuthToken with specified values
     * @param authKey unique string to be AuthKey
     * @param userName unique string to be username
     */
    public AuthToken(String authKey, String userName) {
        this.authKey = authKey;
        this.userName = userName;
    }

    /**
     * Creates empty AuthToken
     */
    public AuthToken() {
    }

    /**
     * AuthKey getter
     * @return sting AuthKey
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * AuthKey setter
     * @param authKey authkey to be set
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    /**
     * username getter
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return authKey.equals(authToken.authKey) &&
                userName.equals(authToken.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authKey, userName);
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authKey='" + authKey + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
