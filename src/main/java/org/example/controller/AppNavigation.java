package org.example.controller;

import javafx.fxml.FXML;
import org.example.App;

public class AppNavigation {
    @FXML private void showMain(){ App.setRoot("Main"); }
    @FXML private void showBrowse(){ App.setRoot("Browse"); }
    @FXML private void showBooking(){ App.setRoot("Booking"); }
    @FXML private void showGuests(){ App.setRoot("Guests"); }
}
