package com.example.server.dao;

import com.example.shared.model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * Takes Person objects and interacts with the database with it.
 */
public class PersonDAO {
    /**
     * Connection with the database
     */
    private Connection conn;
    /**
     * Creates PersonDAO with a connection
     * @param conn the connection
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a person into the database
     * @param person person to be added
     * @throws DataAccessException if there's a problem with the database
     */
    public boolean insert(Person person) throws DataAccessException {
        boolean commit = true;
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getpersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        return commit;
    }



    /**
     * Queries for a person in the database
     * @param personID person's unique ID to find them
     * @return Person if found
     * @throws DataAccessException if there's a problem with the database
     */
    public Person find(String personID) throws DataAccessException {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
     * Gets all the Persons in the database
     * @return all the persons in the database
     * @throws DataAccessException if there's a problem with the database
     */
    public ArrayList<Person> findAll(String username) throws DataAccessException {
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                Person person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                people.add(person);
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        }
    }

    /**
     * Clears all the data for Persons in the database
     * @return if it was successful
     * @throws DataAccessException if there's a problem with the database
     */
    public boolean clear() throws DataAccessException {
        String sql = "DELETE FROM Persons;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting persons");
        }
    }

    /**
     * Updates a specified person in the database
     * @param person specified person
     * @throws DataAccessException if there's a problem with the database
     */
    public boolean update(Person person) throws DataAccessException {
        boolean commit = true;

        String sql = "UPDATE Persons SET PersonID=?, AssociatedUsername=?, FirstName=?, LastName=?, Gender=?, " +
                "FatherID=?, MotherID=?, SpouseID=? WHERE PersonID=?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getpersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.setString(9, person.getpersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while default updating Person in database.");
        }

        return commit;
    }

}
