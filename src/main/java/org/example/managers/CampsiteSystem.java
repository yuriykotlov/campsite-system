package org.example.managers;

import org.example.data_structures.LinkedList;
import org.example.model.*;
import org.example.util.StringChecker;

import java.time.LocalDate;

public class CampsiteSystem {
    private LinkedList<Campsite> campsites;
    private LinkedList<Guest> guests;

    // Used to supply an id to the campsites and guests
    private int totalCampsites = 0;
    private int totalGuests = 0;

/*
    GETTERS

    No setters as the CRUD methods will manage all the variables' data.
 */

    public LinkedList<Campsite> getCampsites(){ return campsites; }
    public LinkedList<Guest> getGuests(){ return guests; }
    public int getTotalCampsites(){ return totalCampsites; }
    public int getTotalGuests(){ return totalGuests; }

/*
    CRUD

    Called from booking.fxml, and adds a new campsite after supplying the required fields.
*/

    /* CAMPSITE CRUD */

    public Campsite addCampsite(String name, int stars, String address, String phoneNumber, String email){
        totalCampsites++;
        campsites.add(
                new Campsite(totalCampsites, name, stars, address, phoneNumber, email)
        );
        return campsites.get(campsites.size());
    }
    public Campsite findCampsite(int targetCampsiteId){
        return campsites.find(campsite -> campsite.getId() == targetCampsiteId);
    }

    // Update a single campsite
    public Campsite updateCampsite(
            int targetCampsiteId,
            String name, int stars, String address, String phoneNumber, String email
    ){
        Campsite campsite = findCampsite(targetCampsiteId);

        if(campsite != null){
            campsite.setName(name);
            campsite.setStars(stars);
            campsite.setContactInformation(address, phoneNumber, email);
        }

        return campsite;
    }

    public void removeCampsite(int targetCampsiteId){ campsites.remove(findCampsite(targetCampsiteId)); }

    /* GUEST CRUD */

    public Guest addGuest(String name, LocalDate dateOfBirth, String address, String phoneNumber, String email){
        totalGuests++;
        guests.add(
                new Guest(totalGuests, name, dateOfBirth, address, phoneNumber, email)
        );
        return guests.get(guests.size());
    }
    public Guest findGuest(int targetGuestId){
        return guests.find(guest -> guest.getId() == targetGuestId);
    }

    // Update a single guest's information
    public Guest updateGuest(
            int targetGuestId,
            String name, LocalDate dateOfBirth, String address, String phoneNumber, String email
    ){
        Guest guest = findGuest(targetGuestId);

        if (guest != null) {
            guest.setName(name);
            guest.setDateOfBirth(dateOfBirth);
            guest.setContactInformation(address, phoneNumber, email);
        }

        return guest;
    }

    public void removeGuest(int targetGuestId){ guests.remove(findGuest(targetGuestId)); }
}
