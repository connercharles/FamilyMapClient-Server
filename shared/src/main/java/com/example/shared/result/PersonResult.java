package com.example.shared.result;

import com.example.shared.model.Person;

import java.util.ArrayList;
import java.util.Objects;

public class PersonResult extends Result {

    /**
     * unique id for this person
     */
    private String personID;

    /**
     * unique username for this person
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
     * all persons in database
     */
    private ArrayList<Person> data;

    /**
     * Creates PersonResult with everything
     * @param personID new personID
     * @param userName new username
     * @param firstName new first name
     * @param lastName new last name
     * @param gender new gender
     * @param fatherID new father's id
     * @param motherID new mother's id
     * @param spouseID new spouse's id
     * @param message new message
     * @param persons new persons list
     */
    public PersonResult(String personID, String userName, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, String message, ArrayList<Person> persons) {
        super(message);
        this.personID = personID;
        this.associatedUsername = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.data = persons;
    }

    public PersonResult(ArrayList<Person> persons) {
        super(null);
        this.personID = null;
        this.associatedUsername = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        this.data = persons;
    }

    public PersonResult() {
        super(null);
    }

    public void copyInfo(Person person){
        this.personID = person.getpersonID();
        this.associatedUsername = person.getAssociatedUsername();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
    }

    /**
     * gets all persons
     * @return Arraylist of all persons
     */
    public ArrayList<Person> getData() {
        return data;
    }

    /**
     * sets persons
     * @param persons new persons
     */
    public void setData(ArrayList<Person> persons) {
        this.data = persons;
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
    public String getUserName() {
        return associatedUsername;
    }

    /**
     * username setter
     * @param userName new unique username string
     */
    public void setUserName(String userName) {
        this.associatedUsername = userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResult that = (PersonResult) o;
        return Objects.equals(personID, that.personID) &&
                Objects.equals(associatedUsername, that.associatedUsername) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(fatherID, that.fatherID) &&
                Objects.equals(motherID, that.motherID) &&
                Objects.equals(spouseID, that.spouseID) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID, data);
    }
}
