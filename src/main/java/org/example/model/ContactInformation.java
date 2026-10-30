package org.example.model;

import org.example.util.StringChecker;

public class ContactInformation {
    String address;
    String phoneNumber;
    String email;

    public ContactInformation(String address, String phoneNumber, String email){
        this.address = StringChecker.resolveIfNullOrEmpty(address, "No address provided.");
        this.phoneNumber = StringChecker.resolveIfNullOrEmpty(phoneNumber, "No phone number provided.");
        this.email = StringChecker.resolveIfNullOrEmpty(email, "No email provided.");
    }

//
// Getters and Setters
//
// Refuse updating a variable if the input is empty or null,
//

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        if(StringChecker.notNullOrEmpty(address)){
            this.address = address;
        }
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        if(StringChecker.notNullOrEmpty(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        if(StringChecker.notNullOrEmpty(email)) {
            this.email = email;
        }
    }
}
