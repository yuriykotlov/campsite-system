package org.example.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.example.AppState;
import org.example.data_structures.*;
import org.example.managers.*;
import org.example.model.*;

import java.time.LocalDate;

public class AppMain {
    private final CampsiteSystem campsiteSystem = AppState.getSystem();
    private final BookingService bookingService = AppState.getBookingService();

    @FXML private Button saveDataButton;
    @FXML private Button loadDataButton;
    @FXML private Button resetDataButton;

    @FXML private Label campsiteCountLabel;
    @FXML private Label plotCountLabel;
    @FXML private Label guestCountLabel;
    @FXML private Label pendingBookingsLabel;
    @FXML private Label pendingBookingsCostLabel;

    // on startup
    @FXML
    private void initialize(){
        System.out.println("init");

        saveDataButton.setOnAction(event -> saveData());
        loadDataButton.setOnAction(event -> loadData());
        resetDataButton.setOnAction(event -> resetData());

        showSummary();
    }

    private void showSummary(){
        int plotCount = 0;
        for(Campsite campsite : campsiteSystem.getCampsites()){
            for(CampingArea campingArea : campsite.getCampingAreas()){
                plotCount += campingArea.getPlots().size();
            }
        }

        LinkedList<Booking> pendingBookings = bookingService.getAllPendingBookings();

        campsiteCountLabel.setText("Campsite Count: " + campsiteSystem.getCampsites().size());
        plotCountLabel.setText("Plot Count: " + plotCount);
        guestCountLabel.setText("Guest Count: " + campsiteSystem.getGuests().size());
        pendingBookingsLabel.setText("Bookings Count: " + pendingBookings.size());
        pendingBookingsCostLabel.setText("Total Bookings Cost: " + BookingService.getTotalBookingsCost(pendingBookings) + " euro");
    }

    @FXML
    private void saveData(){
        System.out.println("save");
    }

    @FXML
    private void loadData(){
        System.out.println("load");
    }

    @FXML
    private void resetData(){
        System.out.println("reset data");
    }
}
