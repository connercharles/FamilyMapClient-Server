package com.example.server;

import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.PersonDAO;
import com.example.shared.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PersonDAOTest {
    private Database db;
    private Person chuckNorris;
    private Person jimNorris;
    private Person mommaNorris;
    private ArrayList<Person> people;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        chuckNorris = new Person("12345", "bigMan", "Chuck",
                "Norris", "M", "unknown", "300",
                "none");
        jimNorris = new Person("200", "bigMan", "Jim",
                "Norris", "M", "unknown", "300",
                null);
        mommaNorris = new Person("300", "bigMan", "Momma",
                "Norris", "F", null, null,
                null);
        people = new ArrayList<>();
        people.add(chuckNorris);
        people.add(jimNorris);
        people.add(mommaNorris);

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
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.insert(chuckNorris);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.find(chuckNorris.getpersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(chuckNorris, compareTest);
    }

    @Test
    public void findFail() throws Exception {
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.insert(chuckNorris);
            compareTest = pDao.find("1");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNotEquals(compareTest, chuckNorris);
    }

    @Test
    public void findAllPass() throws Exception {
        ArrayList<Person> compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.insert(chuckNorris);
            pDao.insert(jimNorris);
            pDao.insert(mommaNorris);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.findAll(chuckNorris.getAssociatedUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(compareTest.size(), 3);
        assertEquals(people, compareTest);
    }

    @Test
    public void findAllFail() throws Exception {
        ArrayList<Person> compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.insert(chuckNorris);
            pDao.insert(jimNorris);
            pDao.insert(mommaNorris);

            compareTest = pDao.findAll("userName");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotEquals(compareTest, people);
        assertEquals(0, compareTest.size());
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.insert(chuckNorris);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.find(chuckNorris.getpersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(chuckNorris, compareTest);

    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //if we call the method the first time it will insert it successfully
            pDao.insert(chuckNorris);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            pDao.insert(chuckNorris);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our person is not in the database
        //Set our compareTest to an actual person
        Person compareTest = chuckNorris;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = pDao.find(chuckNorris.getpersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }


    @Test
    public void clearPass() throws Exception {
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.insert(chuckNorris);
            pDao.clear();
            compareTest = pDao.find(chuckNorris.getpersonID());
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
            PersonDAO pDao = new PersonDAO(conn);

            db.dropTables();
            pDao.clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);
    }

}
