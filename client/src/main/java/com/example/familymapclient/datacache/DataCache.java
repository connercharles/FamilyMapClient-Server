package com.example.familymapclient.datacache;

import com.example.familymapclient.model.EventCli;
import com.example.familymapclient.model.PersonCli;
import com.example.shared.model.Event;
import com.example.shared.model.Person;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class DataCache {
    // list of events and persons and stuff
    public String serverHost;
    public String serverPort;

    public PersonCli user;
    public String authToken;

    public ArrayList<PersonCli> fatherSideMales;
    public ArrayList<PersonCli> fatherSideFemales;
    public ArrayList<PersonCli> motherSideMales;
    public ArrayList<PersonCli> motherSideFemales;

    public ArrayList<EventCli> events;

    public PersonCli currentPerson;
    public EventCli currentEvent;

    //for filtering
    public boolean lifeLines;
    public boolean familyLines;
    public boolean spouseLines;
    public boolean fatherLines;
    public boolean motherLines;
    public boolean maleLines;
    public boolean femaleLines;

    public ArrayList<Marker> markers;

    private static final DataCache ourInstance = new DataCache();

    public static DataCache getInstance() {
        return ourInstance;
    }

    private DataCache() {
        serverHost = null;
        serverPort = null;

        user = new PersonCli();
        authToken = null;
        fatherSideMales = new ArrayList<>();
        fatherSideFemales = new ArrayList<>();
        motherSideMales = new ArrayList<>();
        motherSideFemales = new ArrayList<>();
        events = new ArrayList<>();
        currentPerson = new PersonCli();
        currentEvent = null;

        lifeLines = true;
        familyLines = true;
        spouseLines = true;
        fatherLines = true;
        motherLines = true;
        maleLines = true;
        femaleLines = true;

        markers = new ArrayList<>();
    }

    public Person findUser(ArrayList<Person> data, String personID){
        // go through all the data and get one with correct personID
        for (Person person : data) {
            if (person.getpersonID().equals(personID)){
                user.copyPerson(person);
                return person;
            }
        }

        return null;
    }

    private void setUserTree(ArrayList<Person> data, PersonCli currentPerson){
        // order spouse from Russian website
        if (currentPerson.getSpouseID() != null) {
            for (Person person : data) {
                if (person.getpersonID().equals(currentPerson.getSpouseID())){
                    // create spouse and copy info
                    PersonCli spouse = new PersonCli();
                    spouse.copyPerson(person);

                    // set spouse
                    currentPerson.setSpouse(spouse);
                }
            }
        }

        // check father's side
        if (currentPerson.getFatherID() == null) {
            return;
        }
        for (Person person : data) {
            if (person.getpersonID().equals(currentPerson.getFatherID())){
                // create father and copy info
                PersonCli father = new PersonCli();
                father.copyPerson(person);
                father.addChild(currentPerson);

                // set father, then go into father
                currentPerson.setFather(father);
                setUserTree(data, father);
            }
        }

        // check mother's side
        if (currentPerson.getMotherID() == null) {
            return;
        }
        for (Person person : data) {
            if (person.getpersonID().equals(currentPerson.getMotherID())){
                // create mother and copy info
                PersonCli mother = new PersonCli();
                mother.copyPerson(person);
                mother.addChild(currentPerson);

                // set mother, then go into mother
                currentPerson.setMother(mother);
                setUserTree(data, mother);
            }
        }
    }

    private void addSides(PersonCli currentPerson, boolean isFathersSide){
        // check father's side
        if (currentPerson.getFather() == null) {
            return;
        } else {
            if (isFathersSide) {
                // add father to father's male side
                fatherSideMales.add(currentPerson.getFather());
            } else {
                // add father to mother's male side
                motherSideMales.add(currentPerson.getFather());
            }
            // go up the line
            addSides(currentPerson.getFather(), isFathersSide);
        }

        // check mother's side
        if (currentPerson.getMother() == null) {
            return;
        } else {
            if (isFathersSide) {
                // add mother to father's female side
                fatherSideFemales.add(currentPerson.getMother());
            } else {
                // add mother to mother's female side
                motherSideFemales.add(currentPerson.getMother());
            }
            // go up the line
            addSides(currentPerson.getMother(), isFathersSide);
        }
    }

    private void sortSides(){
        // add the user's father
        fatherSideMales.add(user.getFather());
        addSides(user.getFather(), true);

        // now mother's side
        motherSideFemales.add(user.getMother());
        addSides(user.getMother(), false);
    }


    // do it recursively so just look at the parents and add them then keep going
    public void organizePersons(ArrayList<Person> data){
        setUserTree(data, user);
        sortSides();
    }

    public void organizeEvents(ArrayList<Event> data){
        for (Event event : data) {
            // create EventCli
            EventCli newEvent = new EventCli();
            newEvent.copyEvent(event);

            // go through people and add event to person and vise versa
            boolean found = false;

            if (user.getpersonID().equals(newEvent.getPersonID())){
                newEvent.setPerson(user);
                events.add(newEvent);
                user.addEvent(newEvent);
                found = true;
            }
            if (user.getSpouseID() != null && user.getSpouseID().equals(newEvent.getPersonID())){
                newEvent.setPerson(user.getSpouse());
                events.add(newEvent);
                user.getSpouse().addEvent(newEvent);
                found = true;
            }
            if (!found){
                for (PersonCli person : fatherSideMales) {
                    if (person.getpersonID().equals(newEvent.getPersonID())){
                        newEvent.setPerson(person);
                        events.add(newEvent);
                        person.addEvent(newEvent);
                        found = true;
                    }
                }
            }
            if (!found) {
                for (PersonCli person : fatherSideFemales) {
                    if (person.getpersonID().equals(newEvent.getPersonID())){
                        newEvent.setPerson(person);
                        events.add(newEvent);
                        person.addEvent(newEvent);
                        found = true;
                    }
                }
            }
            if (!found){
                for (PersonCli person : motherSideMales) {
                    if (person.getpersonID().equals(newEvent.getPersonID())){
                        newEvent.setPerson(person);
                        events.add(newEvent);
                        person.addEvent(newEvent);
                        found = true;
                    }
                }
            }
            if (!found){
                for (PersonCli person : motherSideFemales) {
                    if (person.getpersonID().equals(newEvent.getPersonID())){
                        newEvent.setPerson(person);
                        events.add(newEvent);
                        person.addEvent(newEvent);
                        found = true;
                    }
                }
            }

        }
    }

    public ArrayList<PersonCli> getAllPeople(){
        ArrayList<PersonCli> all = new ArrayList<>();
        all.add(user);
        all.add(user.getSpouse());
        all.addAll(fatherSideMales);
        all.addAll(fatherSideFemales);
        all.addAll(motherSideMales);
        all.addAll(motherSideFemales);

        return all;
    }

    public ArrayList<EventCli> getFilteredEvents(){
        ArrayList<EventCli> all = new ArrayList<>();
        for (EventCli event : events) {
            if (fatherLines) {
                if (maleLines && fatherSideMales.contains(event.getPerson())){
                    all.add(event);
                    continue;
                }
                if (femaleLines && fatherSideFemales.contains(event.getPerson())){
                    all.add(event);
                    continue;
                }
            }
            if (motherLines){
                if (maleLines && motherSideMales.contains(event.getPerson())){
                    all.add(event);
                    continue;
                }
                if (femaleLines && motherSideFemales.contains(event.getPerson())){
                    all.add(event);
                    continue;
                }
            }
            if (user.getGender().equals("m") && maleLines){
                    all.addAll(user.getEvents());
                    continue;
            }
            if (user.getGender().equals("f") && femaleLines){
                    all.addAll(user.getEvents());
                    continue;
            }
//            if (user.getSpouse().getGender().equals("m") && maleLines){
//                    all.add(event);
//                    continue;
//            }
//            if (user.getSpouse().getGender().equals("f") && femaleLines){
//                    all.add(event);
//                    continue;
//            }
        }
        return all;
    }

    // returns event based on passed in ID
    public EventCli getEvent(String eventID){
        for (EventCli event : events) {
            if (event.getEventID().equals(eventID)){
                return event;
            }
        }
        return null;
    }

    // returns event list based on passed in personID
    public ArrayList<EventCli> getEvents(String personID){
        ArrayList<EventCli> result = new ArrayList<>();
        for (EventCli event : events) {
            if (event.getPersonID().equals(personID)){
                result.add(event);
            }
        }
        return result;
    }

    public ArrayList<PersonCli> searchPeople(String query){
        query = query.toLowerCase();
        ArrayList<PersonCli> result = new ArrayList<>();
        for (PersonCli person : DataCache.getInstance().getAllPeople()) {
            if (person.getFirstName().toLowerCase().contains(query)
                    || person.getLastName().toLowerCase().contains(query)){
                result.add(person);
            }
        }
        return result;
    }

    public ArrayList<EventCli> searchEvents(String query){
        query = query.toLowerCase();
        ArrayList<EventCli> result = new ArrayList<>();
        for (EventCli event : DataCache.getInstance().getFilteredEvents()) {
            if (event.getCountry().toLowerCase().contains(query)
                    || event.getCity().toLowerCase().contains(query)
                    || event.getEventType().toLowerCase().contains(query)
                    || String.valueOf(event.getYear()).contains(query)){
                result.add(event);
            }
        }
        return result;
    }



    public void selfDestruct(){
        new DataCache();
    }

}
