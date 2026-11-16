package org.example.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private final int id;
    private final Guest guest;

    private LocalDate startDate;
    private LocalDate endDate;

    private CampingArea campingArea;
    private Plot plot;

    private Preferences preferences;

    // A booking can't change its Id or be transferred to another guest, another booking would have to be made
    public Booking(
            int id, Guest guest,
            LocalDate startDate, LocalDate endDate, CampingArea campingArea, Plot plot, Preferences preferences
    ){
        this.id = id;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campingArea = campingArea;
        this.plot = plot;
        this.preferences = preferences;
    }

/*
    Getters and Setters

    Refuse updating a variable if the input is empty or null.
*/

    public int getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CampingArea getCampingArea() {
        return campingArea;
    }
    public void setCampingArea(CampingArea campingArea) {
        this.campingArea = campingArea;
    }

    public Plot getPlot() {
        return plot;
    }
    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public Preferences getPreferences() {
        return preferences;
    }
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    /*
        UTILITY
    */

    public long getTotalNightsBooked() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public double getTotalBookedCost() {
        return (double)(getTotalNightsBooked() * plot.getCostPerNight());
    }
}
