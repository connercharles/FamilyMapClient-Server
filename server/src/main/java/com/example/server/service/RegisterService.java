package com.example.server.service;

import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.UserDAO;
import com.example.shared.model.User;
import com.example.shared.request.FillRequest;
import com.example.shared.request.RegisterRequest;
import com.example.shared.result.RegisterResult;

import java.util.UUID;

/**
 * Encapsulates the actual register service api call
 */
public class RegisterService extends AuthService{
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth token
     * @param r request body
     * @return response body
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException {
        RegisterResult result = new RegisterResult();
        Database db = new Database();
        try{
            db.openConnection();
            // find user
            UserDAO checkForUser = new UserDAO(db.getConnection());
            User foundUser = checkForUser.find(r.getUserName());

            if (foundUser != null) {
                throw new Exception("Username already in use.");
            }

            foundUser = makeUser(r);
            checkForUser.insert(foundUser);

            String authToken = insertAuthToken(db.getConnection(), r.getUserName());
            result.setAuthToken(authToken);
            result.setUserName(foundUser.getUserName());
            result.setPersonID(foundUser.getpersonID());

            db.closeConnection(true);

            // fill this user
            FillService fillService = new FillService();
            FillRequest fillRequest = new FillRequest();
            fillRequest.setUserName(r.getUserName());
            fillService.fill(fillRequest);
        } catch(Exception e) {
            db.closeConnection(false);
            result = new RegisterResult("Error: " + e.toString());
        }
        return result;
    }

    private User makeUser(RegisterRequest r) {
        User user = new User();
        user.setUserName(r.getUserName());
        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setGender(r.getGender());
        user.setpersonID(UUID.randomUUID().toString());

        return user;
    }

    public RegisterService() {
    }

}
