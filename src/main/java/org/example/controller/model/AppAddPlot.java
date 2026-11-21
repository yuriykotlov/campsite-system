package org.example.controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.model.CampingArea;

import org.example.managers.*;
import org.example.model.*;
import org.example.data_structures.*;

import org.example.util.ListHelper;

public class AppAddPlot {
    @FXML private Label titleLabel;
    @FXML private TextField identifierField;

    @FXML private ComboBox<PlotType> plotTypeComboBox;
    @FXML private CheckBox hasPowerCheckBox;
    @FXML private Spinner<Integer> bedroomCountSpinner;
    @FXML private Spinner<Integer> bedCountSpinner;

    @FXML private TextField sizeSqMField;
    @FXML private TextField costPerNightField;
    @FXML private TextField facilitiesField;
    @FXML private TextField photoURLField;

    @FXML private Label warningLabel;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private static CampingArea parentCampingArea = null;
    private static Plot plotEdited = null;

    public static void open(CampingArea parent, Plot editing){
        parentCampingArea = parent;
        plotEdited = editing;
        App.setRoot("AddPlot");
    }

    @FXML
    private void initialize(){
        plotTypeComboBox.getItems().setAll(PlotType.values());
        plotTypeComboBox.setValue(PlotType.EMPTY);
        bedroomCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        bedCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));

        plotTypeComboBox.valueProperty().addListener((obs, oldType, newType) -> applyTypeRules());

        saveButton.setOnAction(event -> savePlot());
        cancelButton.setOnAction(event -> App.setRoot("Browse"));

        if(plotEdited == null){
            titleLabel.setText("Add plot to " + parentCampingArea.getAreaIdentifier());
        } else {
            titleLabel.setText("Edit plot in " + parentCampingArea.getAreaIdentifier());
            identifierField.setText(plotEdited.getIdentifier());
            plotTypeComboBox.setValue(plotEdited.getType());
            hasPowerCheckBox.setSelected(plotEdited.hasPower());
            bedroomCountSpinner.getValueFactory().setValue(plotEdited.getBedroomCount());
            bedCountSpinner.getValueFactory().setValue(plotEdited.getBedCount());
            sizeSqMField.setText(String.valueOf(plotEdited.getSizeSqM()));
            costPerNightField.setText(String.valueOf(plotEdited.getCostPerNight()));
            facilitiesField.setText(ListHelper.joinList(plotEdited.getFacilities()));
            photoURLField.setText(plotEdited.getPhotoURL());
        }

        applyTypeRules();
    }

    private void applyTypeRules(){
        boolean isStatic = plotTypeComboBox.getValue() == PlotType.STATIC_MOBILE_HOME;
        boolean isEmptyPitch = plotTypeComboBox.getValue() == PlotType.EMPTY;

        if(isStatic) hasPowerCheckBox.setSelected(true);
        hasPowerCheckBox.setDisable(isStatic);

        if(isEmptyPitch){
            bedroomCountSpinner.getValueFactory().setValue(0);
            bedCountSpinner.getValueFactory().setValue(0);
        }
        bedroomCountSpinner.setDisable(isEmptyPitch);
        bedCountSpinner.setDisable(isEmptyPitch);
    }

    private void savePlot(){
        String identifier = identifierField.getText().trim();
        if(identifier.isEmpty()){
            warningLabel.setText("Please enter an identifier (name)");
            return;
        }

        int size = parseNonNegative(sizeSqMField, "Size");
        if(size < 0) return;

        int cost = parseNonNegative(costPerNightField, "Cost per night");
        if(cost < 0) return;

        PlotType type = plotTypeComboBox.getValue();
        boolean hasPower = hasPowerCheckBox.isSelected();
        int bedrooms = bedroomCountSpinner.getValue();
        int beds = bedCountSpinner.getValue();
        LinkedList<String> facilities = ListHelper.parseList(facilitiesField.getText());
        String photo = photoURLField.getText().trim();

        Plot plot;
        if(plotEdited == null){
            plot = parentCampingArea.addPlot(
                    identifier, type, hasPower, bedrooms, beds, size, facilities, cost, photo
            );

        } else {
            plot = parentCampingArea.updatePlot(
                    plotEdited, identifier, type, hasPower, bedrooms, beds, size, facilities, cost, photo
            );
        }

        if(plot == null){
            warningLabel.setText("\"" + identifier + "\" is already in this Camping Area");
            return;
        }

        App.setRoot("Browse");
    }

    private int parseNonNegative(TextField field, String fieldName){
        try{
            int value = Integer.parseInt(field.getText().trim());
            if(value < 0){
                warningLabel.setText(fieldName + " can't be less than 0");
                return -1;
            }
            return value;
        } catch(NumberFormatException e){
            warningLabel.setText(fieldName + " must be an integer");
            return -1;
        }
    }
}
