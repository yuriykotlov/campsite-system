package org.example.controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.Campsite;

import org.example.managers.*;
import org.example.model.*;

public class AppAddCampingArea {
    @FXML private Label titleLabel;
    @FXML private TextField identifierField;
    @FXML private TextField descriptionField;

    @FXML private Label warningLabel;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private static Campsite parentCampsite = null;
    private static CampingArea campingAreaEdited = null;

    public static void open(Campsite parent, CampingArea editing){
        parentCampsite = parent;
        campingAreaEdited = editing;
        App.setRoot("AddCampingArea");
    }

    @FXML
    private void initialize(){
        saveButton.setOnAction(event -> saveCampingArea());
        cancelButton.setOnAction(event -> App.setRoot("Browse"));

        if(campingAreaEdited == null){
            titleLabel.setText("Add Camping Area to Campsite " + parentCampsite.getName());
        } else {
            titleLabel.setText("Editing Camping Area in Campsite " + parentCampsite.getName());
            identifierField.setText(campingAreaEdited.getAreaIdentifier());
            descriptionField.setText(campingAreaEdited.getDescription());
        }
    }

    private void saveCampingArea(){
        String identifier = identifierField.getText().trim();
        String description = descriptionField.getText().trim();

        if(identifier.isEmpty()){
            warningLabel.setText("Please enter an identifier (name)");
            return;
        }

        CampingArea result;
        if(campingAreaEdited == null){
            result = parentCampsite.addCampingArea(identifier, description);
        } else {
            result = parentCampsite.updateCampingArea(campingAreaEdited.getId(), identifier, description);
        }

        if(result == null){
            warningLabel.setText("\"" + identifier + "\" is already in the Campsite System");
            return;
        }

        App.setRoot("Browse");
    }
}
