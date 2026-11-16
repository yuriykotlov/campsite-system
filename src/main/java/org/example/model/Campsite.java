package org.example.model;

import org.example.data_structures.LinkedList;
import org.example.util.StringChecker;

public class Campsite {
    private final int id;
    private String name;
    private int stars;
    private ContactInformation contactInformation;
    private LinkedList<CampingArea> campingAreas = new LinkedList<>();
    private int totalCampingAreas = 0;

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

/*
    CAMPING AREA MANAGEMENT
*/

    public CampingArea addCampingArea(String areaIdentifier, String description){
        // Can't add two of the same campsites with the same identifier
        if(findCampingArea(areaIdentifier) != null){
            return null;
        }

        CampingArea area = new CampingArea(totalCampingAreas + 1, areaIdentifier, description);

        totalCampingAreas++;
        campingAreas.add(area);
        return area;
    }

    public CampingArea updateCampingArea(int targetAreaId, String areaIdentifier, String description){
        CampingArea area = findCampingArea(targetAreaId);
        if(area == null){ return null; }

        area.setAreaIdentifier(areaIdentifier);
        area.setDescription(description);
        return area;
    }

    public CampingArea findCampingArea(int targetAreaId){
        return campingAreas.find(area -> area.getId() == targetAreaId);
    }

    public CampingArea findCampingArea(String targetAreaIdentifier){
        return campingAreas.find(area -> area.getAreaIdentifier().equalsIgnoreCase(targetAreaIdentifier));
    }

    public void removeCampingArea(int targetAreaId){ campingAreas.remove(findCampingArea(targetAreaId)); }
}
