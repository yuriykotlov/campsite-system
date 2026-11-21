package org.example.model;

import org.example.data_structures.LinkedList;
import org.example.util.StringChecker;

public class CampingArea {
    private final int id;

    private String areaIdentifier;
    private String description;

    private LinkedList<Plot> plots = new LinkedList<>();
    private int totalPlots = 0;

    public CampingArea(int id, String areaIdentifier, String description){
        this.id = id;
        this.areaIdentifier = StringChecker.resolveIfNullOrEmpty(areaIdentifier, "Camping Area " + this.id);
        this.description = StringChecker.resolveIfNullOrEmpty(description, "No description");
    }

/*
    GETTERS AND SETTERS

    Refuse updating a variable if the input is empty or null,
    with the exception of `plots`.
*/

    public int getId(){ return id; }

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

    public LinkedList<Plot> getPlots(){
        return plots;
    }

/*
    PLOT MANAGEMENT
*/

    public Plot addPlot(
            String identifier, PlotType type, boolean hasPower, int bedroomCount, int bedCount, int sizeSqM, LinkedList<String> facilities, int costPerNight, String photoURL
    ){
        Plot plot = new Plot(totalPlots + 1, identifier, type, hasPower, bedroomCount, bedCount, sizeSqM, facilities, costPerNight, photoURL);

        totalPlots++;
        plots.add(plot);

        return plot;
    }

    public Plot updatePlot(
            Plot plot,
            String identifier, PlotType type, boolean hasPower, int bedroomCount, int bedCount, int sizeSqM, LinkedList<String> facilities, int costPerNight, String photoURL
    ){
        plot.setIdentifier(identifier);
        plot.setType(type);
        plot.setHasPower(hasPower);
        plot.setBedroomCount(bedroomCount);
        plot.setBedCount(bedCount);
        plot.setSizeSqM(sizeSqM);
        plot.setFacilities(facilities);
        plot.setCostPerNight(costPerNight);
        plot.setPhotoURL(photoURL);

        return plot;
    }

    public Plot findPlot(int targetPlotId){
        return plots.find(plot -> plot.getId() == targetPlotId);
    }

    public void removePlot(Plot plot){ plots.remove(plot); }
    public void removePlot(int targetPlotId){ plots.remove(findPlot(targetPlotId)); }
}
