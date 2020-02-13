package com.example.shared.model;

import java.util.Objects;

/**
 * Represents a person within the genealogy data
 */
public class Person {
    //Person ID: Unique identifier for this person (non-empty string)
    //Associated Username: User (Username) to which this person belongs
    //First Name: Person’s first name (non-empty string)
    //Last Name: Person’s last name (non-empty string)
    //Gender: Person’s gender (string: "f" or "m")
    //Father ID: Person ID of person’s father (possibly null)
    //Mother ID: Person ID of person’s mother (possibly null)
    //Spouse ID: Person ID of person’s spouse (possibly null)

    /**
     * unique id for this person
     */
    private String personID;

    /**
     * unique associatedUsername for this person
     */
    private String associatedUsername;

    /**
     * first name of this person
     */
    private String firstName;

    /**
     * last name of this person
     */
    private String lastName;

    /**
     * gender of this person (f or m)
     */
    private String gender;

    /**
     * father's unique id
     */
    private String fatherID;

    /**
     * mother's unique id
     */
    private String motherID;

    /**
     * spouse's unique id
     */
    private String spouseID;

    /**
     * creates a person with all data set up through parameters
     * @param personID person's ID string
     * @param associatedUsername person's associatedUsername string
     * @param firstName person's first name string
     * @param lastName person's last name string
     * @param gender person's gender string
     * @param fatherID person's father's id string
     * @param motherID person's mother's id string
     * @param spouseID person's spouse's id string
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * creates an empty person
     */
    public Person() {
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

    /**
     * username getter
     * @return username string
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     * username setter
     * @param associatedUsername new unique associatedUsername string
     */
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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
     * last name setter
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
     * @param gender new gender string (m or f)
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * father's id getter
     * @return father's id string
     */
    public String getFatherID() {
        return fatherID;
    }

    /**
     * father's id setter
     * @param fatherID new father's id string, should be unique
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * mother's id getter
     * @return mother's id string
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     * mother's id setter
     * @param motherID new mother's id string, should be unique
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     * spouse's id getter
     * @return spouse's id string
     */
    public String getSpouseID() {
        return spouseID;
    }

    /**
     * spouse's id setter
     * @param spouseID new spouse's id string, should be unique
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Checks if two Persons are equal to each other
     * @param o object to be checked
     * @return boolean if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) &&
                associatedUsername.equals(person.associatedUsername) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    /**
     * Creates a hashcode of all the person data
     * @return unique hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }

    /**
     * Displays all person data in a string
     * @return a string of all person data
     */
    @Override
    public String toString() {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                '}';
    }
}
