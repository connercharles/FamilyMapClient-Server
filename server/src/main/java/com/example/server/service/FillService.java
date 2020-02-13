package com.example.server.service;

import com.example.server.Data.EventData;
import com.example.server.Data.EventParts;
import com.example.server.Data.NameData;
import com.example.server.dao.DataAccessException;
import com.example.server.dao.Database;
import com.example.server.dao.EventDAO;
import com.example.server.dao.PersonDAO;
import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.example.shared.model.User;
import com.example.shared.request.FillRequest;
import com.example.shared.result.FillResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Encapsulates the actual fill service api call
 */
public class FillService extends Service {
    private final String FNAMES = "database/fnames.json";
    private final String MNAMES = "database/mnames.json";
    private final String SNAMES = "database/snames.json";
    private final String LOCATIONS = "database/locations.json";

    private final String BIRTH = "birth";
    private final String DEATH = "death";
    private final String MARRIAGE = "marriage";

    private int personsCreated = 1;
    private int eventsCreated = 1;

    private EventParts getEventData() throws IOException {
        File file = new File(LOCATIONS);
        String fileContext = readFileLocation(file);
        EventData eventsList = deserialize(fileContext, EventData.class);

        Random rand = new Random();
        EventParts event = eventsList.data[rand.nextInt(eventsList.data.length)];

        return event;
    }

    private String getNameData(String fileName) throws IOException {
        File file = new File(fileName);
        List<String> fileContext = readFile(file);
        NameData[] namesList = deserialize(fileContext.toString(), NameData[].class);

        Random rand = new Random();
        String name = null;

        while (name == null){
            name = namesList[0].data[rand.nextInt(namesList[0].data.length)];
        }

        return name;
    }

    private List<String> readFile(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        List<String> fileContents = Files.readAllLines(path);
        return fileContents;
    }

    private String readFileLocation(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        String fileContents = new String(Files.readAllBytes(path));
        return fileContents;
    }


    private Person createPerson(String userName, boolean isBoy, int currentYear) throws IOException, DataAccessException {
        Person person = new Person();
        String firstName;
        String gender;
        if (isBoy){
            firstName = getNameData(MNAMES);
            gender = "m";
        } else {
            firstName = getNameData(FNAMES);
            gender = "f";
        }
        person.setFirstName(firstName);
        person.setLastName(getNameData(SNAMES));
        person.setAssociatedUsername(userName);
        person.setGender(gender);
        person.setpersonID(UUID.randomUUID().toString());

        // create events for them
        // death
        Event death = createEvent(person, currentYear, DEATH);
        addEventToDatabase(death);

        // birth - 25
        Event birth = createEvent(person, currentYear - 25, BIRTH);
        addEventToDatabase(birth);

        return person;
    }

    private Event createEvent(Person person, int currentYear, String eventType) throws IOException {
        EventParts eventPart = getEventData();
//        Event event = new Event(eventPart);
        Event event = new Event();
//                this.longitude = ep.longitude;
//        this.latitude = ep.latitude;
//        this.city = ep.city;
//        this.country = ep.country;
        event.setLongitude(eventPart.longitude);
        event.setLatitude(eventPart.latitude);
        event.setCity(eventPart.city);
        event.setCountry(eventPart.country);

        event.setUsername(person.getAssociatedUsername());
        event.setPersonID(person.getpersonID());
        event.setYear(currentYear);
        event.setEventType(eventType);
        event.setEventID(UUID.randomUUID().toString());

        return event;
    }

    private void updateChildInDatabase(Person child) throws DataAccessException{
        Database db = new Database();
        try{
            db.openConnection();
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            personDAO.update(child);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    private void addPersonToDatabase(Person person) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            personDAO.insert(person);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    private void addEventToDatabase(Event event) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            EventDAO eventDAO = new EventDAO(db.getConnection());
            eventDAO.insert(event);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    // dun..dun dun dun...dun..dun dun dun...
    private void marry(Person mother, Person father, int year) throws IOException, DataAccessException {
        mother.setSpouseID(father.getpersonID());
        father.setSpouseID(mother.getpersonID());

        // put that in the event's records
        Event marriageM = createEvent(mother, year - 5, MARRIAGE);
        Event marriageF = new Event(marriageM);
        marriageF.setPersonID(father.getpersonID());
        marriageF.setEventID(UUID.randomUUID().toString());
        // pic or it didn't happen!
        addEventToDatabase(marriageF);
        addEventToDatabase(marriageM);
    }

    private void createParents(String userName, Person child, int generation, int year) throws Exception {
        Person mother = createPerson(userName, false, year);
        Person father = createPerson(userName, true, year);

        marry(mother, father, year);

        // add them to database
        addPersonToDatabase(mother);
        addPersonToDatabase(father);

        // add to family
        child.setMotherID(mother.getpersonID());
        child.setFatherID(father.getpersonID());
        updateChildInDatabase(child);

        // inc stuff
        generation--;
        personsCreated += 2;
        eventsCreated += 5;
        year -= 50;

        if (generation > 0) {
            createParents(userName, mother, generation, year);
            createParents(userName, father, generation, year);
        }
    }

    private Person createUserPerson(User user, int year) throws Exception {
        Person userPerson = new Person();
        userPerson.setFirstName(user.getFirstName());
        userPerson.setLastName(user.getLastName());
        userPerson.setGender(user.getGender());
        userPerson.setpersonID(user.getpersonID());
        userPerson.setAssociatedUsername(user.getUserName());

        // create birth event for user
        Event birth = createEvent(userPerson, year, BIRTH);

        // add to database and stuff
        addEventToDatabase(birth);
        addPersonToDatabase(userPerson);
        return userPerson;
    }

    private void deleteAllUserData(String userName) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            db.clearUserHistory(userName);
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
            throw e;
        }
    }

    /**
     * Populates the server's database with generated data for the specified user name.
     * The required "username" parameter must be a user already registered with the server. If there is
     * any data in the database already associated with the given user name, it is deleted. The
     * optional 'generations' parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     * @param r request body
     * @return response body
     */
    public FillResult fill(FillRequest r){
        FillResult result = new FillResult();
        try{
            // check if user exists
            if (userExists(r.getUserName())){
                // delete all data with user
                deleteAllUserData(r.getUserName());
            } else {
                throw new Exception("User does not exist in database.");
            }
            // check if generations parameter was filled out properly
            if (r.getGenerations() < 0) {
                throw new Exception("Invalid generations argument.");
            }

            int year = Calendar.getInstance().get(Calendar.YEAR);
            Person user = createUserPerson(getUser(r.getUserName()), year);

            if (r.getGenerations() > 0){
                createParents(r.getUserName(), user, r.getGenerations(), year - 25);
            }

            result.setMessage(personsCreated, eventsCreated);
        } catch(Exception e) {
            result.setMessage("Error: " + e.toString());
        }
        return result;
    }

    public FillService() {
    }
}
