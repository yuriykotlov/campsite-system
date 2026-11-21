package org.example.controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import org.example.App;
import org.example.AppState;
import org.example.data_structures.*;
import org.example.managers.*;
import org.example.model.*;
import org.example.util.StringChecker;

public class AppAddCampsite {
    @FXML private TextField nameField;
    @FXML private TextField starsField;
    @FXML private TextField addressField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private TextField facilitiesField;

    @FXML private Label warningLabel;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final CampsiteSystem campsiteSystem = AppState.getSystem();

    private static Campsite campsiteEdited = null;

    public static void setCampsiteBeingEdited(Campsite campsite){
        campsiteEdited = campsite;
    }

    // on startup
    @FXML
    private void initialize(){
        System.out.println("init");

        if(campsiteEdited != null){
            filloutFields();
        }
        warningLabel.setText("");

        saveButton.setOnAction(event -> saveCampsite());
        cancelButton.setOnAction(event -> cancelCampsite());
    }

    public void filloutFields(){
        nameField.setText(campsiteEdited.getName());
        starsField.setText(Integer.toString(campsiteEdited.getStars()));
        addressField.setText(campsiteEdited.getContactInformation().getAddress());
        phoneNumberField.setText(campsiteEdited.getContactInformation().getPhoneNumber());
        emailField.setText(campsiteEdited.getContactInformation().getEmail());
        facilitiesField.setText(StringChecker.LinkedListToString(
                campsiteEdited.getFacilities()
        ));
    }

    private void saveCampsite(){
        int stars = 0;

        try {
            stars = Integer.parseInt(starsField.getText().trim());

            if(stars < 0 || stars > 5){
                warningLabel.setText("Stars must be a number from 0 to 5");
                return;
            }

        } catch (NumberFormatException e) {
            warningLabel.setText("Stars must be a number from 0 to 5");
            return;
        }

        LinkedList<String> facilities = new LinkedList<>();
        for(String facility : facilitiesField.getText().trim().split(",")){
            facilities.add(facility);
        }

        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();

        if(campsiteEdited == null){
            campsiteSystem.addCampsite(name, stars, address, phoneNumber, email, facilities);

        } else{
            campsiteEdited.setName(name);
            campsiteEdited.setStars(stars);
            campsiteEdited.getContactInformation().setAddress(address);
            campsiteEdited.getContactInformation().setPhoneNumber(phoneNumber);
            campsiteEdited.getContactInformation().setEmail(email);
            campsiteEdited.setFacilities(facilities);

            campsiteEdited = null;
        }

        backToBrowse();
    }

    private void cancelCampsite(){
        backToBrowse();
    }

    private void backToBrowse(){
        App.setRoot("Browse");
    }
}
