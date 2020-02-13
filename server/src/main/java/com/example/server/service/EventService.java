package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;

import com.example.server.dao.EventDAO;
import com.example.shared.model.AuthToken;
import com.example.shared.model.Event;
import com.example.shared.request.EventRequest;
import com.example.shared.result.EventResult;

import java.util.ArrayList;

/**
 * Encapsulates the actual events service api call
 */
public class EventService {
    /**
     *  Returns the single Event object with the specified ID.
     * @param r request body
     * @return response body
     */
    public EventResult event(EventRequest r) throws DataAccessException {
        EventResult result = new EventResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find event
            EventDAO checkForEvent = new EventDAO(db.getConnection());
            Event foundEvent = checkForEvent.find(r.geteventID());

            if (foundEvent == null) {
                throw new Exception("Event not in database.");
            }

            // find user
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken authToken = authTokenDAO.find(r.getAuthToken());

            if (authToken == null) {
                throw new Exception("User not in database.");
            }

            if (!foundEvent.getUsername().equals(authToken.getUserName())) {
                throw new Exception("Not authorized to retrieve this data.");
            }

            result.copyInfo(foundEvent);

            db.closeConnection(false);
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token
     * @param r request body
     * @return response body
     */
    public EventResult eventAll(EventRequest r) throws DataAccessException {
        EventResult result = new EventResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find user
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken authToken = authTokenDAO.find(r.getAuthToken());

            if (authToken == null) {
                throw new Exception("User not in database.");
            }

            EventDAO eventDAO = new EventDAO(db.getConnection());
            ArrayList<Event> events = eventDAO.findAll(authToken.getUserName());
            result = new EventResult(events);

            db.closeConnection(false);
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }
}
