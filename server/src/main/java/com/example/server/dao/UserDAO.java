package com.example.server.dao;

import com.example.shared.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Takes User objects and interacts with the database with it.
 */
public class UserDAO {
    /**
     * Connection with the database
     */
    private Connection conn;

    /**
     * Creates UserDAO with a connection
     * @param conn the connection
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a User into the database
     * @param user user to be added
     * @throws DataAccessException if there's a problem with the database
     */
    public boolean insert(User user) throws DataAccessException {
        boolean commit = true;
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Users (PersonID, UserName, Password, Email, FirstName, " +
                "LastName, Gender) VALUES(?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getpersonID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setString(7, user.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        return commit;
    }



    /**
     * Queries for a user in the database
     * @param usersUserName user's unique user name to find them
     * @return User if found
     * @throws DataAccessException if there's a problem with the database
     */
    public User find(String usersUserName) throws DataAccessException {
        User user = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usersUserName);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                user = new User(rs.getString("PersonID"), rs.getString("UserName"),
                        rs.getString("Password"), rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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
     * Clears all the data for Users in the database
     * @return if it was successful
     * @throws DataAccessException if there's a problem with the database
     */
    public boolean clear() throws DataAccessException {
        String sql = "DELETE FROM Users;"; // see if this syntax works
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting users");
        }
    }


}
