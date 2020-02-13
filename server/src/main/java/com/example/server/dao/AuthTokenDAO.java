package com.example.server.dao;

import com.example.shared.model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Takes AuthToken objects and interacts with the database with it
 */
public class AuthTokenDAO {
    /**
     * Connection with the database
     */
    private Connection conn;

    /**
     * Creates AuthTokenDAO with a connection
     * @param conn the connection
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * Inserts an AuthToken into the database
     * @param authToken AuthToken to be added
     * @throws if there's a problem with the database
     */
    public boolean insert(AuthToken authToken) throws DataAccessException {
        boolean commit = true;
        String sql = "INSERT INTO AuthTokens (UserName, Authkey) VALUES(?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getUserName());
            stmt.setString(2, authToken.getAuthKey());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException(e.toString());
        }

        return commit;
    }

    /**
     * Queries for an AuthToken in the database
     * @param authKey AuthToken to find
     * @return AuthKey if found
     * @throws DataAccessException if there's a problem with the database
     */
    public AuthToken find(String authKey) throws DataAccessException {
        AuthToken authToken = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE Authkey = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authKey);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                authToken = new AuthToken(rs.getString("Authkey"), rs.getString("UserName"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Queries for an AuthToken in the database
     * @param userName AuthToken to find
     * @return AuthKey if found
     * @throws DataAccessException if there's a problem with the database
     */
    public AuthToken findUserName(String userName) throws DataAccessException {
        AuthToken authToken = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                authToken = new AuthToken(rs.getString("Authkey"), rs.getString("UserName"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Deletes the specified AuthToken from database
     * @param authToken specified AuthToken
     * @throws DataAccessException if there's a database problem
     */
    public boolean delete(AuthToken authToken) throws DataAccessException {
        return false;
    }

    /**
     * Creates AuthToken table for data
     * @throws DataAccessException if there's a database problem
     */
    public boolean createTable() throws DataAccessException {
        return false;
    }

    /**
     * Updates a specified AuthToken in the database
     * @param authToken specified AuthToken
     * @throws DataAccessException if there's a database problem
     */
    public boolean update(AuthToken authToken) throws DataAccessException {
        return false;
    }

    /**
     * Clears all the data for AuthTokens in the database
     * @return if it was successful
     * @throws DataAccessException if there's a database problem
     */
    public boolean clear() throws DataAccessException {
        String sql = "DELETE FROM AuthTokens;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        }
    }
}
