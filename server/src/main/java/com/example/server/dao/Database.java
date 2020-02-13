package com.example.server.dao;

import java.sql.*;

public class Database {
    private Connection conn;

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
        }
    }

    public void createTables() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){
            //First lets open our connection to our database.

            //We pull out a statement from the connection we just established
            //Statements are the basis for our transactions in SQL
            //Format this string to be exactly like a sql create table command
            String sql = "CREATE TABLE IF NOT EXISTS Events " +
                    "(" +
                    "EventID text not null unique, " +
                    "AssociatedUsername text not null, " +
                    "PersonID text not null, " +
                    "Latitude text not null, " +
                    "Longitude text not null, " +
                    "Country text not null, " +
                    "City text not null, " +
                    "EventType text not null, " +
                    "Year int not null, " +
                    "primary key (EventID), " +
                    "foreign key (AssociatedUsername) references Users(Username), " +
                    "foreign key (PersonID) references Persons(PersonID)" +
                    ")";

            stmt.executeUpdate(sql);
            //if we got here without any problems we successfully created the table and can commit

            // create User Tables
            String sql2 = "CREATE TABLE IF NOT EXISTS Users " +
                    "(" +
                    "PersonID text not null unique, " +
                    "UserName text not null unique, " +
                    "Password text not null, " +
                    "Email text not null, " +
                    "FirstName text not null, " +
                    "LastName text not null, " +
                    "Gender text " +
//                    "primary key (UserName) " +
//                    "foreign key (PersonID) references Persons(PersonID), " +
                    ")"; // foreign key (PersonID) ?????????

            stmt.executeUpdate(sql2);

            // create Persons Tables
            String sql3 = "CREATE TABLE IF NOT EXISTS Persons " +
                    "(" +
                    "PersonID text not null unique, " +
                    "AssociatedUsername text not null, " +
                    "FirstName text not null, " +
                    "LastName text not null, " +
                    "Gender text, " +
                    "FatherID text, " +
                    "MotherID text, " +
                    "SpouseID text" +
//                    "primary key (PersonID) " +
//                    "foreign key (UserName) references Users(UserName), " +
                    ")"; // primary key (PersonID) ?????????
            // foreign keys for FatherID and MotherID????
            stmt.executeUpdate(sql3);

            // create AuthTokens Tables
            String sql4 = "CREATE TABLE IF NOT EXISTS AuthTokens " +
                    "(" +
                    "UserName text not null unique, " +
                    "Authkey text not null unique " +
                    ")";
            stmt.executeUpdate(sql4);
        } catch (SQLException e) {
            //if our table creation caused an error, we can just not commit the changes that did happen
            throw new DataAccessException(e.toString());
        }
    }

    public void populateTables() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            // populate authTokens
            String sql1a = "INSERT into AuthTokens (Authkey, UserName) VALUES(\"a201\", \"user1\");";
            String sql2a = "INSERT into AuthTokens (Authkey, UserName) VALUES(\"a202\", \"user2\");";
            String sql3a = "INSERT into AuthTokens (Authkey, UserName) VALUES(\"a203\", \"user3\");";
            String sql4a = "INSERT into AuthTokens (Authkey, UserName) VALUES(\"a204\", \"user4\");";
            String sql5a = "INSERT into AuthTokens (Authkey, UserName) VALUES(\"a205\", \"user5\");";
            stmt.executeUpdate(sql1a);
            stmt.executeUpdate(sql2a);
            stmt.executeUpdate(sql3a);
            stmt.executeUpdate(sql4a);
            stmt.executeUpdate(sql5a);

            // populate Persons
            String sql1p = "INSERT into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender) VALUES(\"100\", \"user1\", \"john\", \"watson\", \"m\");";
            String sql2p = "INSERT into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender) VALUES(\"101\", \"user2\", \"jim\", \"smith\", \"m\");";
            String sql3p = "INSERT into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender) VALUES(\"102\", \"user3\", \"joanne\", \"watson\", \"f\");";
            String sql4p = "INSERT into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender) VALUES(\"103\", \"user4\", \"jordan\", \"smith\", \"f\");";
            String sql5p = "INSERT into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender) VALUES(\"104\", \"user5\", \"some\", \"guy\", \"m\");";
            stmt.executeUpdate(sql1p);
            stmt.executeUpdate(sql2p);
            stmt.executeUpdate(sql3p);
            stmt.executeUpdate(sql4p);
            stmt.executeUpdate(sql5p);

            // populate users
            String sql1u= "INSERT into Users (PersonID, UserName, Password, Email, FirstName, LastName, Gender) VALUES(\"100\", \"user1\", \"password1\", \"john@gmail.com\", \"john\", \"watson\", \"m\");";
            String sql2u= "INSERT into Users (PersonID, UserName, Password, Email, FirstName, LastName, Gender) VALUES(\"101\", \"user2\", \"password2\", \"jim@gmail.com\", \"jim\", \"smith\", \"m\");";
            String sql3u= "INSERT into Users (PersonID, UserName, Password, Email, FirstName, LastName, Gender) VALUES(\"102\", \"user3\", \"password3\", \"joanne@gmail.com\", \"joanne\", \"watson\", \"f\");";
            String sql4u= "INSERT into Users (PersonID, UserName, Password, Email, FirstName, LastName, Gender) VALUES(\"103\", \"user4\", \"password4\", \"jordan@gmail.com\", \"jordan\", \"smith\", \"f\");";
            String sql5u= "INSERT into Users (PersonID, UserName, Password, Email, FirstName, LastName, Gender) VALUES(\"104\", \"user5\", \"password5\", \"some@gmail.com\", \"some\", \"guy\", \"m\");";
            stmt.executeUpdate(sql1u);
            stmt.executeUpdate(sql2u);
            stmt.executeUpdate(sql3u);
            stmt.executeUpdate(sql4u);
            stmt.executeUpdate(sql5u);

        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }


    }

    public void dropTables() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DROP TABLE Events;";
            stmt.executeUpdate(sql);
            String sq2 = "DROP TABLE Users;";
            stmt.executeUpdate(sq2);
            String sq3 = "DROP TABLE Persons;";
            stmt.executeUpdate(sq3);
            String sq4 = "DROP TABLE AuthTokens;";
            stmt.executeUpdate(sq4);
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    public void clearTables() throws DataAccessException
    {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events;";
            stmt.executeUpdate(sql);
            String sq2 = "DELETE FROM Users;";
            stmt.executeUpdate(sq2);
            String sq3 = "DELETE FROM Persons;";
            stmt.executeUpdate(sq3);
            String sq4 = "DELETE FROM AuthTokens;";
            stmt.executeUpdate(sq4);
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    public void clearUserHistory(String username) throws DataAccessException
    {
        String sql = "DELETE FROM Events WHERE AssociatedUsername = ?;";
        String sql2 = "DELETE FROM Persons WHERE AssociatedUsername = ?;";

        try (PreparedStatement stmt1 = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2)){
            stmt1.setString(1, username);
            stmt1.executeUpdate();

            stmt2.setString(1, username);
            stmt2.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }
}
