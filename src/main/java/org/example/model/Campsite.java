package org.example.model;

import org.example.data_structures.LinkedList;
import org.example.util.StringChecker;

public class Campsite {
    private static int GLOBAL_CAMPSITE_COUNT = 0;

    private String name;
    private int stars = 0;
    private ContactInformation contactInformation;
    private LinkedList<CampingArea> campingAreas;

    public Campsite(String name, int stars, String address, String phoneNumber, String email){
        this.name = StringChecker.resolveIfNullOrEmpty(name, "Campsite " + GLOBAL_CAMPSITE_COUNT);
        this.stars = Math.max(stars, 0);
        this.contactInformation = new ContactInformation(address, phoneNumber, email);

        GLOBAL_CAMPSITE_COUNT += 1;
    }

//
// Getters and Setters
//
// Refuse updating a variable if the input is empty or null,
//

    public String getName(){
        return name;
    }
    public void setName(String name){
        if(StringChecker.notNullOrEmpty(name)){
            this.name = name;
        }
    }

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
}
