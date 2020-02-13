package com.example.server;

import com.example.shared.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.shared.request.PersonRequest;
import com.example.shared.result.PersonResult;
import com.example.server.service.PersonService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class PersonServiceTest extends ServiceTest {
    private ArrayList<Person> persons;

    @BeforeEach
    public void setUp() throws Exception {
        setUpDB();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tearDownDB();
    }

    @Test
    public void personPass() throws Exception {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setpersonID(person1.getpersonID());
        personRequest.setAuthToken(authToken1.getAuthKey());

        PersonService personService = new PersonService();
        PersonResult personResult = personService.person(personRequest);

        PersonResult compareTest = new PersonResult();
        compareTest.copyInfo(person1);

        assertEquals(compareTest, personResult);
    }

    @Test
    public void personFail() throws Exception {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setpersonID(person1.getpersonID());
        personRequest.setAuthToken(authToken1.getAuthKey());

        PersonService personService = new PersonService();
        PersonResult personResult = personService.person(personRequest);

        PersonResult compareTest = new PersonResult();
        compareTest.copyInfo(person2);

        assertNotEquals(compareTest, personResult);
    }

    @Test
    public void personAllPass() throws Exception {
        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        PersonRequest personRequest = new PersonRequest();
        personRequest.setpersonID(person1.getpersonID());
        personRequest.setAuthToken(authToken1.getAuthKey());

        PersonService personService = new PersonService();
        PersonResult personResult = personService.personAll(personRequest);

        PersonResult compareTest = new PersonResult(persons);

        assertEquals(compareTest, personResult);
    }

    @Test
    public void personAllFail() throws Exception {
        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);

        PersonRequest personRequest = new PersonRequest();
        personRequest.setpersonID(person1.getpersonID());
        personRequest.setAuthToken(authToken1.getAuthKey());

        PersonService personService = new PersonService();
        PersonResult personResult = personService.personAll(personRequest);

        PersonResult compareTest = new PersonResult(persons);

        assertNotEquals(compareTest, personResult);
    }


}
