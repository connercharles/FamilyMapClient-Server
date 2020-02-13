package com.example.familymapclient.model;

import com.example.familymapclient.datacache.DataCache;
import com.example.shared.model.Person;

import java.util.ArrayList;

public class PersonCli extends Person {
    private PersonCli father;
    private PersonCli mother;
    private PersonCli spouse;
    private ArrayList<PersonCli> children;
    private ArrayList<PersonCli> family;
    private ArrayList<EventCli> events;

    public PersonCli() {
        children = new ArrayList<>();
        events = new ArrayList<>();
        father = null;
        mother = null;
        spouse = null;
    }

    public void copyPerson(Person person){
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setAssociatedUsername(person.getAssociatedUsername());
        this.setGender(person.getGender());
        this.setpersonID(person.getpersonID());
        this.setSpouseID(person.getSpouseID());
        this.setFatherID(person.getFatherID());
        this.setMotherID(person.getMotherID());
    }

    public PersonCli getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father.copyPerson(father);
    }

    public void setFather(PersonCli father) {
        this.father = father;
    }

    public PersonCli getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother.copyPerson(mother);
    }

    public void setMother(PersonCli mother) {
        this.mother = mother;
    }

    public PersonCli getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse.copyPerson(spouse);
    }

    public void setSpouse(PersonCli spouse) {
        this.spouse = spouse;
    }

    public ArrayList<PersonCli> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<PersonCli> children) {
        this.children = children;
    }

    public void addChild(PersonCli child){
        children.add(child);
    }

    public void addEvent(EventCli event) { events.add(event); }

    public ArrayList<PersonCli> getFamily() {
        ArrayList<PersonCli> familyUpdated = new ArrayList<>();

        if (father != null){
            familyUpdated.add(father);
        }
        if (mother != null){
            familyUpdated.add(mother);
        }
        if (spouse != null){
            familyUpdated.add(spouse);
        }
        if (children != null || !children.isEmpty()){
            familyUpdated.addAll(children);
        }
        return familyUpdated;
    }

    public void setFamily(ArrayList<PersonCli> family) {
        this.family = family;
    }

    public ArrayList<EventCli> getEvents() {
        return events;
    }

    public ArrayList<EventCli> getFilteredEvents() {
        ArrayList<EventCli> all = new ArrayList<>();
        for (EventCli event : events) {
            if (DataCache.getInstance().fatherLines) {
                if (DataCache.getInstance().maleLines && DataCache.getInstance().fatherSideMales.contains(event.getPerson())){
                    all.add(event);
                }
                if (DataCache.getInstance().femaleLines && DataCache.getInstance().fatherSideFemales.contains(event.getPerson())){
                    all.add(event);
                }
            }
            if (DataCache.getInstance().motherLines){
                if (DataCache.getInstance().maleLines && DataCache.getInstance().motherSideMales.contains(event.getPerson())){
                    all.add(event);
                }
                if (DataCache.getInstance().femaleLines && DataCache.getInstance().motherSideFemales.contains(event.getPerson())){
                    all.add(event);
                }
            }
            if (spouse.getGender().equals("m") && DataCache.getInstance().maleLines){
                all.add(event);
            }
            if (spouse.getGender().equals("f") && DataCache.getInstance().femaleLines){
                all.add(event);
            }
        }
        return all;
    }

    public void setEvents(ArrayList<EventCli> events) {
        this.events = events;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
}
