package com.example.server.service;

import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.shared.result.ClearResult;

/**
 * Encapsulates the actual clear service api call
 */
public class ClearService extends Service{

    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and event data.
     * @return response body
     */
    public ClearResult clear() throws DataAccessException {
        // result
        ClearResult result = new ClearResult();
        // create the DAO people and call delete
        // open database and wreck havoc
        Database db = new Database();
        try{
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);

            result.setMessage(getMessage("Clear"));
        } catch(Exception e) {
            db.closeConnection(false);
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    public ClearService() {
    }
}
