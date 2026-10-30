package org.example.model;

import java.time.LocalDate;

public class Guest {
    private static int TOTAL_GUEST_COUNT = 0;

    private int id = -1;
    private String name;
    private LocalDate dateOfBirth;
    private ContactInformation contactInformation;

    public Guest(String name, LocalDate dateOfBirth, String address, String phoneNumber, String email){
        TOTAL_GUEST_COUNT += 1;
        id += TOTAL_GUEST_COUNT;

        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactInformation = new ContactInformation(address, phoneNumber, email);
    }

//
// Getters and Setters
//
// Refuse updating a variable if the input is empty or null,
//

    // There is no setter for id, only the getter.
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public ContactInformation getContactInformation(){
        return contactInformation;
    }
    public void setContactInformation(String address, String phoneNumber, String email){
        contactInformation = new ContactInformation(address, phoneNumber, email);
    }
}
