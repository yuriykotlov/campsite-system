package org.example.model;

import org.example.data_structures.LinkedList;

import java.io.Serializable;
import java.util.Objects;

public class Plot implements Serializable {
    private final int id;

    private String identifier;
    private PlotType type;
    private boolean hasPower;
    private int bedroomCount;
    private int bedCount;
    private int sizeSqM;
    private LinkedList<String> facilities = new LinkedList<>();
    private int costPerNight;
    private String photoURL;

    LinkedList<Booking> plotBookings = new LinkedList<>();

    public Plot(
            int id, String identifier, PlotType type,
            boolean hasPower, int bedroomCount, int bedCount, int sizeSqM, LinkedList<String> facilities, int costPerNight, String photoURL
    ){
        this.id = id;
        this.identifier = identifier;
        this.type = Objects.requireNonNullElse(type, PlotType.EMPTY);
        this.hasPower = type == PlotType.STATIC_MOBILE_HOME;

        // Optional
        this.hasPower = hasPower;
        this.bedroomCount = bedroomCount;
        this.bedCount = bedCount;
        this.sizeSqM = sizeSqM;
        this.facilities = facilities;
        this.costPerNight = costPerNight;
        this.photoURL = photoURL;
    }

/*
    Getters and Setters

    Refuse updating a variable if the input is empty or null,
    and if a passed parameter is less than 0, set it to 0.
*/

    // No setters for variable `id`.
    public int getId() {
        return id;
    }

    public String getIdentifier(){ return identifier; }
    public void setIdentifier(String identifier){ this.identifier = identifier; }

    public PlotType getType() {
        return type;
    }
    public void setType(PlotType type) {
        if(type != null){
            this.type = type;
        }
    }

    public boolean hasPower() {
        return hasPower;
    }
    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public int getBedroomCount() {
        return bedroomCount;
    }
    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = Math.max(bedroomCount, 0);
    }

    public int getBedCount() {
        return bedCount;
    }
    public void setBedCount(int bedCount) {
        this.bedCount = Math.max(bedCount, 0);
    }

    public int getSizeSqM() {
        return sizeSqM;
    }
    public void setSizeSqM(int sizeSqM) {
        this.sizeSqM = Math.max(sizeSqM, 0);
    }

    public LinkedList<String> getFacilities() {
        return facilities;
    }
    public void setFacilities(LinkedList<String> facilities) {
        if(facilities != null){
            this.facilities = facilities;
        }
    }

    public int getCostPerNight() {
        return costPerNight;
    }
    public void setCostPerNight(int costPerNight) {
        this.costPerNight = Math.max(costPerNight, 0);
    }

    public String getPhotoURL() {
        return photoURL;
    }
    public void setPhotoURL(String photoURL) {
        this.photoURL = Objects.requireNonNullElse(photoURL, "");
    }

    public LinkedList<Booking> getBookings() {
        return plotBookings;
    }
    public void addBooking(Booking booking) {
        if(booking != null){
            plotBookings.add(booking);
        }
    }
    public void removeBooking(Booking booking) {
        plotBookings.remove(booking);
    }
}
