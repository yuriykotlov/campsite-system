package org.example.model;

import java.util.Objects;

public class Preferences {
    private PlotType type;
    private int numberOfGuests;
    private boolean hasPower;
    private int minSizeSqM;
    private int minBedrooms;
    private int budget;

    public Preferences(
            PlotType type,
            int numberOfGuests,
            boolean hasPower,
            int minSizeSqM,
            int minBedrooms,
            int budget
    ){
        this.type = Objects.requireNonNullElse(type, PlotType.EMPTY);
        this.numberOfGuests = Math.max(numberOfGuests, 0);
        this.hasPower = hasPower;
        this.minSizeSqM = Math.max(minSizeSqM, 0);
        this.minBedrooms = Math.max(minBedrooms, 0);
        this.budget = Math.max(budget, 0);
    }

//
// Getters and Setters
//
// Refuse updating a variable if the input is empty or null,
// and if a passed parameter is less than 0, set it to 0.
//

    public PlotType getType() {
        return type;
    }
    public void setType(PlotType type) {
        if(type != null){
            this.type = type;
        }
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = Math.max(numberOfGuests, 0);
    }

    public boolean isHasPower() {
        return hasPower;
    }
    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public int getMinSizeSqM() {
        return minSizeSqM;
    }
    public void setMinSizeSqM(int minSizeSqM) {
        this.minSizeSqM = Math.max(minSizeSqM, 0);
    }

    public int getMinBedrooms() {
        return minBedrooms;
    }
    public void setMinBedrooms(int minBedrooms) {
        this.minBedrooms = Math.max(minBedrooms, 0);
    }

    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = Math.max(budget, 0);
    }
}
