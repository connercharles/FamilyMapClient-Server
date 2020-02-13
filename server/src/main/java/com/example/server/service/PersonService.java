package com.example.server.service;

import com.example.server.dao.*;
import com.example.shared.model.AuthToken;
import com.example.shared.model.Person;
import com.example.shared.model.User;
import com.example.shared.request.PersonRequest;
import com.example.shared.result.PersonResult;

import java.util.ArrayList;

/**
 * Encapsulates the actual persons service api call
 */
public class PersonService {
    /**
     * Returns the single Person object with the specified ID
     * @param r request body
     * @return response body
     */
    public PersonResult person(PersonRequest r) throws DataAccessException {
        PersonResult result = new PersonResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find person
            PersonDAO checkForPerson = new PersonDAO(db.getConnection());
            Person foundPerson = checkForPerson.find(r.getpersonID());

            if (foundPerson == null) {
                throw new Exception("Person not in database.");
            }

            // find user
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken authToken = authTokenDAO.find(r.getAuthToken());

            if (authToken == null) {
                throw new Exception("User not in database.");
            }

            if (!foundPerson.getAssociatedUsername().equals(authToken.getUserName())) {
                throw new Exception("Not authorized to retrieve this data.");
            }

            result.copyInfo(foundPerson);

            db.closeConnection(false);
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    /**
     * Returns ALL family members of the current user. The current user is
     * determined from the provided auth token
     * @param r request body
     * @return response body
     */
    public PersonResult personAll(PersonRequest r) throws DataAccessException {
        PersonResult result = new PersonResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find user
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken authToken = authTokenDAO.find(r.getAuthToken());

            if (authToken == null) {
                throw new Exception("User not in database.");
            }

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            ArrayList<Person> people = personDAO.findAll(authToken.getUserName());
            result = new PersonResult(people);

            db.closeConnection(false);
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    public PersonService() {
    }
}
