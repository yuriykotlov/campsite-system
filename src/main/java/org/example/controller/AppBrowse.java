package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.App;
import org.example.AppState;
import org.example.controller.model.*;
import org.example.managers.*;
import org.example.model.*;

public class AppBrowse {
    @FXML private TreeView<Object> tree;

    @FXML private Label detailsTitle;
    @FXML private Label detailsText;

    @FXML private Button addCampsiteButton;
    @FXML private Button addChildButton;
    @FXML private Button editButton;
    @FXML private Button removeButton;

    private final CampsiteSystem campsiteSystem = AppState.getSystem();
    private final BookingService bookingService = AppState.getBookingService();

    @FXML
    private void initialize(){
        tree.setCellFactory(view -> new TreeCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == null ? null : labelFor(item));
            }
        });

        tree.getSelectionModel().selectedItemProperty().addListener(
                (obs,
                 oldItem,
                 newItem
                ) -> allowButtonUse(newItem == null ? null : newItem.getValue())
        );

        addCampsiteButton.setOnAction(event -> addCampsite());
        addChildButton.setOnAction(event -> addChild());
        editButton.setOnAction(event -> editSelected());
        removeButton.setOnAction(event -> removeSelected());

        buildTree();
        allowButtonUse(null);
    }

    private void allowButtonUse(Object item){
        addChildButton.setDisable(!(item instanceof Campsite || item instanceof CampingArea || item instanceof Plot));
        editButton.setDisable(!(item instanceof Campsite || item instanceof CampingArea || item instanceof Plot));
        removeButton.setDisable(!(item instanceof Campsite || item instanceof CampingArea
                || item instanceof Plot || item instanceof Booking));

        if(item instanceof Campsite){
            Campsite campsite = (Campsite)item;

            String facilitiesString = "";

            for(String facility : campsite.getFacilities()){
                facilitiesString += facility;
            }

            if(facilitiesString.isEmpty()) facilitiesString = "No facilities";

            addChildButton.setText("Add Camping Area");
            detailsTitle.setText(campsite.getName());
            detailsText.setText(
                            "Stars: " + campsite.getStars()
                            + "\nCamping areas: " + campsite.getCampingAreas().size()
                            + "\nPending bookings: " + bookingService.getPendingBookings(campsite).size()
                            + "\nFacilities: " + facilitiesString
                            + "\nContact Information: "
                            + "\n- Address: " + campsite.getContactInformation().getAddress()
                            + "\n- Phone Number: "  + campsite.getContactInformation().getPhoneNumber()
                            + "\n- Email: " + campsite.getContactInformation().getEmail()
            );

        } else if(item instanceof CampingArea){
            CampingArea campingArea = (CampingArea)item;
            addChildButton.setText("Add Plot");
            detailsTitle.setText(campingArea.getAreaIdentifier());
            detailsText.setText(campingArea.getDescription() + "\nPlots Count: " + campingArea.getPlots().size());

        } else if(item instanceof Plot){
            Plot plot = (Plot)item;
            addChildButton.setText("Add Booking");
            detailsTitle.setText(plot.getIdentifier() + " - " + plot.getType());
            detailsText.setText(
                            "Id: " + plot.getId()
                            + "\nType: " + plot.getType()
                            + "\n" + (plot.hasPower() ? "Has Power" : "Doesn't have Power")
                            + "\nBedrooms: " + plot.getBedroomCount()
                            + "\nBeds: " + plot.getBedCount()
                            + "\nSize: " + plot.getSizeSqM() + " Metres Squared"
                            + "\nCost per night: " + plot.getCostPerNight() + " euros"
                            + "\nPending bookings: " + bookingService.getPendingBookings(plot).size()
            );

        } else if(item instanceof Booking){
            Booking booking = (Booking)item;
            addChildButton.setText("Add");
            detailsTitle.setText("Booking #" + booking.getId());
            detailsText.setText(
                            "Guest: " + booking.getGuest().getName()
                            + "\nDates Booked: " + booking.getStartDate() + " to " + booking.getEndDate()
                            + "\nNights: " + booking.getTotalNightsBooked()
                            + "\nTotal Cost: " + booking.getTotalBookedCost() + " euro"
                            + "\nStatus: " + (bookingService.isPending(booking) ? "Pending" : "Past")
            );

        } else {
            addChildButton.setText("Add");
            detailsTitle.setText("Select an item");
            detailsText.setText("Expand a campsite to drill down into its camping areas, plots and bookings.");
        }
    }

    /*
        TREE
    */

    private void buildTree(){
        TreeItem<Object> root = new TreeItem<>("All campsites");
        root.setExpanded(true);

        for(Campsite campsite : campsiteSystem.getCampsites()){
            TreeItem<Object> campsiteItem = new TreeItem<>(campsite);
            campsiteItem.setExpanded(true);

            for(CampingArea campingArea : campsite.getCampingAreas()){
                TreeItem<Object> areaItem = new TreeItem<>(campingArea);

                for(Plot plot : campingArea.getPlots()){
                    TreeItem<Object> plotItem = new TreeItem<>(plot);

                    for(Booking booking : plot.getBookings()){
                        plotItem.getChildren().add(new TreeItem<>(booking));
                    }
                    areaItem.getChildren().add(plotItem);
                }
                campsiteItem.getChildren().add(areaItem);
            }
            root.getChildren().add(campsiteItem);
        }

        tree.setRoot(root);
    }

    private String labelFor(Object item){
        if(item instanceof Campsite){
            Campsite campsite = (Campsite)item;
            return campsite.getName() + " (" + campsite.getStars() + "★)";
        }
        if(item instanceof CampingArea){
            return ((CampingArea)item).getAreaIdentifier();
        }
        if(item instanceof Plot){
            Plot plot = (Plot)item;
            return plot.getIdentifier() + " (" + plot.getType() + ")";
        }
        if(item instanceof Booking){
            Booking booking = (Booking)item;
            return booking.getGuest().getName() + ": " + booking.getStartDate() + " to " + booking.getEndDate();
        }
        return item.toString();
    }

    /*
        BUTTON ACTIONS
    */

    private void addCampsite(){
        App.setRoot("AddCampsite");
    }

    private void addChild(){
        Object item = selected();

        if(item instanceof Campsite){
            AppAddCampingArea.open((Campsite)item, null);

        } else if(item instanceof CampingArea){
            AppAddPlot.open((CampingArea)item, null);
        }
    }

    private void editSelected(){
        Object item = selected();

        if(item instanceof Campsite){
            AppAddCampsite.setCampsiteBeingEdited((Campsite)item);
            App.setRoot("AddCampsite");

        } else if(item instanceof CampingArea){
            CampingArea campingArea = (CampingArea)item;
            AppAddCampingArea.open(findCampsiteOf(campingArea), campingArea);

        } else if(item instanceof Plot){
            Plot plot = (Plot)item;
            AppAddPlot.open(findCampingAreaOf(plot), plot);
        }
    }

    private void removeSelected(){
        Object item = selected();
        if(item == null || !confirm("Remove the selected item?")) return;

        if(item instanceof Booking){
            bookingService.cancelBooking((Booking)item);

        } else if(item instanceof Plot){
            Plot plot = (Plot)item;

            for(Booking booking : bookingService.getPendingBookings(plot)){
                bookingService.cancelBooking(booking);
            }

            CampingArea campingArea = findCampingAreaOf(plot);
            campingArea.removePlot(plot);

        } else if(item instanceof CampingArea){
            CampingArea campingArea = (CampingArea)item;

            removePlotsInCampingArea(campingArea);
            findCampsiteOf(campingArea).removeCampingArea(campingArea.getId());

        } else if(item instanceof Campsite){
            Campsite campsite = (Campsite)item;

            removeAreasInCampsite(campsite);
            campsiteSystem.removeCampsite(((Campsite)item).getId());
        }

        buildTree();
        allowButtonUse(null);
    }

    /*
        REMOVERS
    */

    private void removeAreasInCampsite(Campsite campsite){
        for(CampingArea campingArea : campsite.getCampingAreas()){
            removePlotsInCampingArea(campingArea);
            findCampsiteOf(campingArea).removeCampingArea(campingArea.getId());
        }
    }

    private void removePlotsInCampingArea(CampingArea campingArea){
        for(Plot plot : campingArea.getPlots()){
            for(Booking booking : bookingService.getPendingBookings(plot)){
                bookingService.cancelBooking(booking);
            }

            campingArea.removePlot(plot);
        }
    }

    /*
        HELPERS
    */

    private Object selected(){
        TreeItem<Object> item = tree.getSelectionModel().getSelectedItem();
        return item == null ? null : item.getValue();
    }

    private boolean confirm(String message){
        return new Alert(Alert.AlertType.CONFIRMATION, message).showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private CampingArea findCampingAreaOf(Plot plot){
        return (CampingArea) tree.getSelectionModel().getSelectedItem().getParent().getValue();
    }

    private Campsite findCampsiteOf(Object child){
        TreeItem<Object> item = tree.getSelectionModel().getSelectedItem();
        while(item != null && !(item.getValue() instanceof Campsite)){
            item = item.getParent();
        }
        return item == null ? null : (Campsite) item.getValue();
    }
}
