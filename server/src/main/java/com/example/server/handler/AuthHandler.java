package com.example.server.handler;


import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.shared.model.AuthToken;

public abstract class AuthHandler extends Handler {

    boolean isAuthorized(String authToken) throws DataAccessException {
        Database db = new Database();
        boolean result;
        try{
            db.openConnection();
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken found = authTokenDAO.find(authToken);

            if (found == null) {
                result = false;
            } else {
                result = true;
            }

            db.closeConnection(false);
        } catch (DataAccessException e){
            db.closeConnection(false);
            throw new DataAccessException("Error: Invalid AuthToken.");
        }

        return result;
    }

}
