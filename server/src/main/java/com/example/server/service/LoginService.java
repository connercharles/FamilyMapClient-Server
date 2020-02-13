package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.UserDAO;
import com.example.shared.model.AuthToken;
import com.example.shared.model.User;
import com.example.shared.request.LoginRequest;
import com.example.shared.result.LoginResult;

/**
 * Encapsulates the actual login service api call
 */
public class LoginService extends AuthService {
    /**
     *  Logs in the user and returns an auth token.
     * @param r request body
     * @return response body
     */
    public LoginResult login(LoginRequest r) throws DataAccessException {
        LoginResult result = new LoginResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find user
            UserDAO checkForUser = new UserDAO(db.getConnection());
            User foundUser = checkForUser.find(r.getUserName());
            // find their authtoken
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken found = authTokenDAO.findUserName(r.getUserName());

            // check if it all matches
            if (userExists(foundUser, r.getUserName(), r.getPassword())) {
                if (found == null){
                    String authKey = insertAuthToken(db.getConnection(), r.getUserName());
                    result.setAuthToken(authKey);
                } else {
                    result.setAuthToken(found.getAuthKey());
                }
            } else {
                throw new Exception("Username/password incorrect");
            }

            result.setUserName(r.getUserName());
            result.setPersonID(foundUser.getpersonID());

            db.closeConnection(true);
        } catch(Exception e) {
            db.closeConnection(false);
            result = new LoginResult("Error: " + e.toString());
        }
        return result;
    }

    private boolean userExists(User foundUser, String userName, String password) {
        if (foundUser == null){
            return false;
        }
        boolean result = (foundUser.getUserName().equals(userName) && foundUser.getPassword().equals(password));
        return result;
    }

    public LoginService() {
    }
}
