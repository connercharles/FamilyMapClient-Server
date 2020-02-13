package com.example.server;

import com.example.server.dao.*;
import com.example.shared.model.AuthToken;
import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.example.shared.model.User;

public abstract class ServiceTest {
    final User user1 = new User("user_1", "myUserName", "password", "user@aol.com", "Yu", "Ser", "m");
    final User user2 = new User("user_2", "this_guy", "pw", "user2@aol.com", "Test", "Man", "m");
    final User userNew = new User("user_new", "newGuy", "new", "user3@aol.com", "green", "giant", "m");

    final Person person1 = new Person("user_1", "myUserName", "Yu", "Ser", "m", "dad1", "mommy1", null);
    final Person person2 = new Person("mommy1", "myUserName", "Mother", "Ser", "f", null, null, "mommy1");
    final Person person3 = new Person("dad1", "myUserName", "Father", "Ser", "m", null, null, "dad1");
    final Person person4 = new Person("user_2", "this_guy", "Test", "Man", "m", null, null, null);

    final Event event1 = new Event("Yu_birth", "myUserName", "user_1", "1.3f", "1.3f", "country1", "city1", "birth", 2000);
    final Event event2 = new Event("mommy_death", "myUserName", "mommy1", "1.3f", "2.3f", "country2", "city2", "death", 2005);
    final Event event3 = new Event("dad_death", "myUserName", "dad1", "1.3f", "3.3f", "country3", "city3", "death", 2005);
    final Event event4 = new Event("mommy_marriage", "myUserName", "mommy1", "4.3f", "10.3f", "country4", "city4", "marriage", 1999);
    final Event event5 = new Event("dad_marriage", "myUserName", "dad1", "6.3f", "6.3f", "country5", "city5", "marriage", 1999);
    final Event event6 = new Event("mommy_birth", "myUserName", "mommy1", "7.3f", "7.3f", "country6", "city6", "birth", 1970);
    final Event event7 = new Event("dad_birth", "myUserName", "dad1", "2.3f", "7.3f", "country7", "city7", "birth", 1971);
    final Event event8 = new Event("TestMan_birth", "this_guy", "user_2", "10.3f", "10.3f", "Japan", "Ushiku", "birth", 2002);

    final AuthToken authToken1 = new AuthToken("you_good", "myUserName");
    final AuthToken authToken2 = new AuthToken("you_good_too", "this_guy");

    private Database db;


    public void setUpDB() throws Exception {
        // fill database with test data
        db = new Database();
        db.openConnection();
        db.clearTables();
        db.createTables();

        UserDAO userDAO = new UserDAO(db.getConnection());
        PersonDAO personDAO = new PersonDAO(db.getConnection());
        EventDAO eventDAO = new EventDAO(db.getConnection());
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());

        userDAO.insert(user1);
        userDAO.insert(user2);
        personDAO.insert(person1);
        personDAO.insert(person2);
        personDAO.insert(person3);
        personDAO.insert(person4);
        eventDAO.insert(event1);
        eventDAO.insert(event2);
        eventDAO.insert(event3);
        eventDAO.insert(event4);
        eventDAO.insert(event5);
        eventDAO.insert(event6);
        eventDAO.insert(event7);
        eventDAO.insert(event8);
        authTokenDAO.insert(authToken1);
        authTokenDAO.insert(authToken2);

        db.closeConnection(true);
    }

    public void tearDownDB() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

}
