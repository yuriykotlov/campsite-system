package org.example.model;

import org.example.util.StringChecker;

import org.example.data_structures.LinkedList;
import java.util.Objects;

public class CampingArea {
    private static int GLOBAL_AREA_COUNT = 0;

    private String areaIdentifier;
    private String description;
    private Plot plot;

    public CampingArea(String areaIdentifier, String description, Plot plot){
        this.areaIdentifier = StringChecker.resolveIfNullOrEmpty(areaIdentifier, "Camping Area " + GLOBAL_AREA_COUNT);
        this.description = StringChecker.resolveIfNullOrEmpty(description, "No description");
        this.plot = plot;

        GLOBAL_AREA_COUNT += 1;
    }

//
// Getters and Setters
//
// Refuse updating a variable if the input is empty or null,
// with the exception of `plots`.
//

    public String getAreaIdentifier() {
        return areaIdentifier;
    }
    public void setAreaIdentifier(String areaIdentifier) {
        if(StringChecker.notNullOrEmpty(areaIdentifier)){
            this.areaIdentifier = areaIdentifier;
        }
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if(StringChecker.notNullOrEmpty(description)){
            this.description = description;
        }
    }

    public Plot getPlot(){
        return plot;
    }
    public void setPlot(Plot plot){
        this.plot = plot;
    }
}
