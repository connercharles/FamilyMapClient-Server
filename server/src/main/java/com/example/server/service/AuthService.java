package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.DataAccessException;
import com.example.shared.model.AuthToken;

import java.sql.Connection;
import java.util.UUID;

public abstract class AuthService extends Service{
    public String insertAuthToken(Connection conn, String userName) throws DataAccessException {
        // make authToken
        String uuid = UUID.randomUUID().toString();

        // add to database
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        AuthToken newData = new AuthToken(uuid, userName);
        authTokenDAO.insert(newData);

        // return authToken
        return uuid;
    }
}
