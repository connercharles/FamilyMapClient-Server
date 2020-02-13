package com.example.shared.model;

import java.util.Objects;

/**
 * Represents a User (person logging into the program)
 */
public class User {
    //Username: Unique user name (non-empty string)
    //Password: User’s password (non-empty string)
    //Email: User’s email address (non-empty string)
    //First Name: User’s first name (non-empty string)
    //Last Name: User’s last name (non-empty string)
    //Gender: User’s gender (string: "f" or "m")
    //Person ID: Unique Person ID assigned to this user’s generated Person object - see Family
    //History Information section for details (non-empty string)
    /**
     * Unique ID that users will not see but for database stuff
     */
    private String personID;

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
     * Creates User with all variables set to parameters
     * @param personID user's personID
     * @param userName user's username
     * @param password user's password
     * @param email user's email
     * @param firstName user's first name
     * @param lastName user's last name
     * @param gender user's gender (m or f)
     */
    public User(String personID, String userName, String password, String email, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Creates an empty user
     */
    public User() {
    }

    /**
     * Displays all user data in a string
     * @return a string of all user data
     */
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", personID='" + personID + '\'' +
                '}';
    }

    /**
     * Checks if two Users are equal to each other
     * @param o object to be checked
     * @return boolean if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender) &&
                personID.equals(user.personID);
    }

    /**
     * Creates a hashcode of all the user data
     * @return unique hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, firstName, lastName, gender, personID);
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

    /**
     * personID getter
     * @return personID string
     */
    public String getpersonID() {
        return personID;
    }

    /**
     * personID setter
     * @param personID new personID string
     */
    public void setpersonID(String personID) {
        this.personID = personID;
    }
}
