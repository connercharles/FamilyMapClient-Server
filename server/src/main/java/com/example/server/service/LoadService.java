package com.example.server.service;

import com.example.server.dao.*;
import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.example.shared.model.User;
import com.example.shared.request.LoadRequest;
import com.example.shared.result.LoadResult;

/**
 * Encapsulates the actual login service api call
 */
public class LoadService extends Service {

    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database
     * @param r request body
     * @return response body
     */
    public LoadResult load(LoadRequest r) throws DataAccessException {
        // result
        LoadResult result = new LoadResult();
        // create the DAO people and call delete
        Database db = new Database();
        try{
            db.openConnection();

            db.clearTables();

            // populate tables
            for (User newUser : r.getUsers()) {
                UserDAO userDAO = new UserDAO(db.getConnection());
                userDAO.insert(newUser);
            }
            for (Person newPerson : r.getPersons()) {
                PersonDAO personDAO = new PersonDAO(db.getConnection());
                personDAO.insert(newPerson);
            }
            for (Event newEvent : r.getEvents()) {
                EventDAO eventDAO = new EventDAO(db.getConnection());
                eventDAO.insert(newEvent);
            }

            db.closeConnection(true);

            result.setMessage(r.getUsers().size(), r.getPersons().size(), r.getEvents().size());
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    public LoadService() {
    }
}
