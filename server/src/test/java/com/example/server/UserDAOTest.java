package com.example.server;

import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.UserDAO;
import com.example.shared.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserDAOTest {
    private Database db;
    private User bobRoss;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bobRoss = new User("100","LuvPainting","happyTreez12",
                "bobby@aol.com","Bob","Ross","M");
        //and make sure to initialize our tables since we don't know if our database files exist yet
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void findPass() throws Exception {
        User compareTest = null;

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(bobRoss);
            compareTest = uDao.find(bobRoss.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bobRoss, compareTest);
    }

    @Test
    public void findFail() throws Exception {
        User compareTest = null;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(bobRoss);
            compareTest = uDao.find("bobby");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNotEquals(compareTest, bobRoss);
    }

    @Test
    public void insertPass() throws Exception {
        User compareTest = null;

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(bobRoss);
            compareTest = uDao.find(bobRoss.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bobRoss, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(bobRoss);
            uDao.insert(bobRoss);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Set our compareTest to an actual person since rollbacks happened
        User compareTest = bobRoss;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            compareTest = uDao.find(bobRoss.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }


    @Test
    public void clearPass() throws Exception {
        User compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(bobRoss);
            uDao.clear();
            compareTest = uDao.find(bobRoss.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        assertNull(compareTest);
    }

    @Test
    public void clearFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);

            db.dropTables();
            uDao.clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);
    }

}
