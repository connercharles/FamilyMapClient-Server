package com.example.shared.result;

/**
 * Response body for Register call
 */
public class RegisterResult extends Result {

    //"authToken": "cf7a368f", // Non-empty auth token string
    //"userName": "susan", // User name passed in with request
    //"personID": "39f9fe46" // Non-empty string containing the Person ID of the
    //   user’s generated Person object

    private String authToken;
    private String userName;
    private String personID;


    /**
     * creates RegisterResult
     * @param personID new personID
     * @param userName new username
     */
    public RegisterResult(String personID, String userName) {
        super(null);
        this.personID = personID;
        this.userName = userName;
    }

    /**
     * creates RegisterResult
     */
    public RegisterResult() {
        super(null);
    }

    /**
     * Creates filled RegisterResult
     * @param authToken new auth token
     * @param userName new username
     * @param personID new personid
     * @param message new message
     */
    public RegisterResult(String authToken, String userName, String personID, String message) {
        super(message);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    /**
     * creates LoginResult
     * @param message new message
     */
    public RegisterResult(String message) {
        super(message);
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
}
