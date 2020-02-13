package com.example.server.service;

import com.google.gson.Gson;
import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.UserDAO;
import com.example.shared.model.User;

public abstract class Service {
    final String success = " succeeded.";

    String getMessage(String methodName){
        return methodName + success;
    }


    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }

    public boolean userExists(String username) throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        UserDAO userDAO = new UserDAO(db.getConnection());
        User found = userDAO.find(username);

        db.closeConnection(false);
        return found != null;
    }

    public User getUser(String username) throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        UserDAO userDAO = new UserDAO(db.getConnection());
        User found = userDAO.find(username);

        db.closeConnection(false);
        return found;
    }
}
