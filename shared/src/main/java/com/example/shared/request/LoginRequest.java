package com.example.shared.request;

/**
 * Request body for Login call
 */
public class LoginRequest {

    /**
     * unique string username for the user
     */
    private String userName;
    /**
     * string password to allow login for user
     */
    private String password;


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
     * gets password
     * @return string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * creates login request
     * @param userName new username
     * @param password new password
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
