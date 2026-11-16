package org.example.controller;

import javafx.scene.control.Button;
import org.example.managers.*;

import javafx.fxml.FXML;

import java.time.LocalDate;

public class AppMain {
    @FXML private AppBrowse appBrowse;
    @FXML private AppBooking appBooking;

    private final CampsiteSystem campsiteSystem = new CampsiteSystem();
    private final BookingService bookingService = new BookingService(campsiteSystem, LocalDate.now());

    @FXML private Button saveButton;
    @FXML private Button loadButton;
    @FXML private Button resetDataButton;

    // on startup
    @FXML
    private void initialize(){
        System.out.println("init");

        saveButton.setOnAction(event -> save());
        loadButton.setOnAction(event -> load());
        resetDataButton.setOnAction(event -> resetData());
    }

    @FXML
    private void save(){
        System.out.println("save");
    }

    @FXML
    private void load(){
        System.out.println("load");
    }

    @FXML
    private void resetData(){
        System.out.println("reset data");
    }
}
