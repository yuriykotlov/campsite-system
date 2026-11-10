package org.example.model;

import org.example.data_structures.LinkedList;
import org.example.util.StringChecker;

public class Campsite {
    private final int id;
    private String name;
    private int stars;
    private ContactInformation contactInformation;
    private LinkedList<CampingArea> campingAreas;

    public Campsite(int id, String name, int stars, String address, String phoneNumber, String email){
        this.id = id;
        this.name = StringChecker.resolveIfNullOrEmpty(name, "Campsite " + id);
        this.stars = Math.max(stars, 0);
        this.contactInformation = new ContactInformation(address, phoneNumber, email);
    }

/*
    Getters and Setters

    Refuse updating a variable if the input is empty or null,
*/

    public String getName(){
        return name;
    }
    public void setName(String name){
        if(StringChecker.notNullOrEmpty(name)){
            this.name = name;
        }
    }

    public int getId(){ return id; }
    // no setter for id.

    public int getStars(){
        return stars;
    }
    public void setStars(int stars){
        this.stars = Math.max(stars, 0);
    }

    public ContactInformation getContactInformation(){
        return contactInformation;
    }
    public void setContactInformation(String address, String phoneNumber, String email){
        contactInformation = new ContactInformation(address, phoneNumber, email);
    }

    public LinkedList<CampingArea> getCampingAreas(){
        return campingAreas;
    }

    // no 'setter' for camping area, simply just add one at a time
    public void addCampingArea(CampingArea campingArea){
        campingAreas.add(campingArea);
    }
}
