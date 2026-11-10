package org.example.model;

import org.example.util.StringChecker;

import java.time.LocalDate;

public class Guest {
    private final int id;
    private String name;
    private LocalDate dateOfBirth;
    private ContactInformation contactInformation;

    public Guest(int id, String name, LocalDate dateOfBirth, String address, String phoneNumber, String email){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactInformation = new ContactInformation(address, phoneNumber, email);
    }

/*
    GETTERS AND SETTERS

    Refuse updating a variable if the input is empty or null,
*/

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
