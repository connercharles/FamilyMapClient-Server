package com.example.shared.request;


/**
 * Request body for Register call
 */
public class RegisterRequest {
    /**
     * Unique username that is made to help user login
     */
    private String userName;

    /**
     * password for user to login
     */
    private String password;

    /**
     * user's email
     */
    private String email;

    /**
     * user's first name
     */
    private String firstName;

    /**
     * user's last name
     */
    private String lastName;

    /**
     * user's gender (m or f)
     */
    private String gender;

    /**
     * creates RegisterRequest with everything filled
     * @param userName new username
     * @param password new password
     * @param email new email
     * @param firstName new first name
     * @param lastName new last name
     * @param gender new gender
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public RegisterRequest() {
    }

    /**
     * username getter
     * @return username string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * username setter
     * @param userName new username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * password getter
     * @return password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     * @param password new password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * email getter
     * @return email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter
     * @param email new email string to be changed
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * first name getter
     * @return first name string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * first name setter
     * @param firstName new first name string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * last name getter
     * @return last name string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * last name getter
     * @param lastName new last name string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gender getter
     * @return gender string (m or f)
     */
    public String getGender() {
        return gender;
    }

    /**
     * gender setter
     * @param gender new gender string
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

}
