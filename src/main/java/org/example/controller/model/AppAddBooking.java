package org.example.controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;

import org.example.AppState;
import org.example.managers.*;
import org.example.model.*;
import org.example.data_structures.*;

import org.example.util.ListHelper;

import java.time.LocalDate;

public class AppAddBooking {
    @FXML private Label titleLabel;
    @FXML private ComboBox<Guest> guestComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Spinner<Integer> guestCountSpinner;
    @FXML private ComboBox<PlotType> plotTypeComboBox;
    @FXML private CheckBox hasPowerCheckBox;
    @FXML private Spinner<Integer> minSqMSpinner;
    @FXML private Spinner<Integer> minBedroomsSpinner;
    @FXML private Spinner<Integer> minBedsSpinner;
    @FXML private Spinner<Integer> minBudgetSpinner;

    @FXML private Label warningLabel;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final CampsiteSystem campsiteSystem = AppState.getSystem();
    private final BookingService bookingService = AppState.getBookingService();

    private static CampingArea targetCampingArea = null;
    private static Plot targetPlot = null;

    // open with an existing plot selected
    public static void open(CampingArea campingArea, Plot plot){
        targetCampingArea = campingArea;
        targetPlot = plot;

        App.setRoot("AddBooking");
    }

    @FXML
    private void initialize(){
        // Guests: show "#3 Jane Smith" instead of the object's toString()
        for(Guest guest : campsiteSystem.getGuests()){
            guestComboBox.getItems().add(guest);
        }

        // null means no preferred type
        plotTypeComboBox.getItems().add(null);
        plotTypeComboBox.getItems().addAll(PlotType.values());
        plotTypeComboBox.setValue(null);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(1));
        guestCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 2));
        minBedroomsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));

        saveButton.setOnAction(event -> saveBooking());
        cancelButton.setOnAction(event -> App.setRoot("Browse"));

        if(targetPlot != null){
            titleLabel.setText("Book plot " + targetPlot.getIdentifier()
                    + " in " + targetCampingArea.getAreaIdentifier());
        } else {
            titleLabel.setText("Book a plot in " + targetCampingArea.getAreaIdentifier());
        }

        if(guestComboBox.getItems().isEmpty()){
            warningLabel.setText("There are no guests in the system yet");
            saveButton.setDisable(true);
        }

        // Don't enter these values if booking for a specific plot
        plotTypeComboBox.setDisable(targetPlot != null);
        hasPowerCheckBox.setDisable(targetPlot != null);
        minSqMSpinner.setDisable(targetPlot != null);
        minBedroomsSpinner.setDisable(targetPlot != null);
        minBedsSpinner.setDisable(targetPlot != null);
        minBudgetSpinner.setDisable(targetPlot != null);
    }

    private void saveBooking(){
        Guest guest = guestComboBox.getValue();
        if(guest == null){
            warningLabel.setText("Please select a guest");
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int guestCount = guestCountSpinner.getValue();

        Booking booking;
        try{
            if(targetPlot != null){
                booking = bookingService.createBookingSpecificPlot(
                        guest, startDate, endDate, targetCampingArea, targetPlot,
                            new Preferences(
                                    targetPlot.getType(),
                                    guestCount,
                                    targetPlot.hasPower(),
                                    targetPlot.getSizeSqM(),
                                    targetPlot.getBedroomCount(),
                                    targetPlot.getBedCount(),
                                    targetPlot.getCostPerNight()
                            )
                        );

            } else {
                booking = bookingService.createBookingBestMatch(
                        guest, startDate, endDate, targetCampingArea,
                            new Preferences(
                                    plotTypeComboBox.getValue(),
                                    guestCount,
                                    hasPowerCheckBox.isSelected(),
                                    minSqMSpinner.getValue(),
                                    minBedroomsSpinner.getValue(),
                                    minBedsSpinner.getValue(),
                                    minBudgetSpinner.getValue()
                            )
                        );
            }
        } catch(IllegalArgumentException e){
            warningLabel.setText(e.getMessage());
            return;
        }

        if(booking == null){
            warningLabel.setText(
                    targetPlot != null
                    ? "There's no plot to book that match your preferences"
                    : "There's no available plot found in this Camping Area"
            );
            return;
        }

        new Alert(Alert.AlertType.INFORMATION,
                "Booked plot " + booking.getPlot().getIdentifier()
                        + " for " + guest.getName()
                        + ". Your total is " + booking.getTotalBookedCost() + "euros").showAndWait();
        App.setRoot("Browse");
    }
}
