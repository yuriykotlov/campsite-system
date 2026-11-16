package org.example.managers;

import org.example.data_structures.LinkedList;
import org.example.model.*;
import org.example.util.StringChecker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingService {
    private CampsiteSystem campsiteSystem;
    private LocalDate today;

    public BookingService(CampsiteSystem campsiteSystem, LocalDate today){
        this.campsiteSystem = campsiteSystem;
        this.today = today;
    }

    /*
        UTIL
     */

    private static void areDatesValid(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null){
            throw new IllegalArgumentException("startDate or endDate cannot be null");
        }
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("startDate cannot be after endDate");
        }
    }

    private LinkedList<Plot> findPreferredPlots(
            CampingArea campingArea, Preferences preferences, LocalDate startDate, LocalDate endDate
    ){
        LinkedList<Plot> targetPlots = new LinkedList<>();

        for(Plot plot : campingArea.getPlots()){
            if(!doPreferencesMatch(plot, preferences, startDate, endDate)) continue;
            if(!isPlotAvailable(plot, startDate, endDate)) continue;
            targetPlots.add(plot);
        }

        return targetPlots;
    }

    private boolean doPreferencesMatch(Plot plot, Preferences preferences, LocalDate startDate, LocalDate endDate){
        if(preferences.getType() != null && preferences.getType() != plot.getType()) return false;
        if(preferences.getNumberOfGuests() >= 1 && preferences.getNumberOfGuests() < plot.getBedCount()) return false;
        if(preferences.hasPower() != plot.hasPower()) return false;
        if(preferences.getMinSizeSqM() >= 1 && preferences.getMinSizeSqM() < plot.getSizeSqM()) return false;
        if(preferences.getMinBedrooms() >= 1 && preferences.getMinBedrooms() < plot.getBedroomCount()) return false;
        if(preferences.getBudget() >= 0 &&
                preferences.getBudget() < (double)(plot.getCostPerNight() * ChronoUnit.DAYS.between(startDate, endDate))
        ) return false;
        return true;
    }

    public boolean isPlotAvailable(Plot plot, LocalDate thisStartDate, LocalDate thisEndDate){
        for(Booking booking : plot.getBookings()){
            if(thisStartDate.isBefore(booking.getEndDate()) && thisEndDate.isAfter(booking.getStartDate())){
                return false;
            }
        }
        return true;
    }

    /*
        BOOKING MANAGEMENT
     */

    // Create a booking. The plot supplied would be found with the method `findPreferredPlots()`
    public Booking createBooking(
            int id, Guest guest,
            LocalDate startDate, LocalDate endDate, CampingArea campingArea, Plot plot, Preferences preferences
    ){
        areDatesValid(startDate, endDate);

        return new Booking(
                id, guest, startDate, endDate, campingArea, plot, preferences
        );
    }

    public void cancelBooking(Booking booking){
        booking.getPlot().removeBooking(booking);
        booking.getGuest().removeBooking(booking);
    }

    /* FINDING BOOKINGS */

    public boolean isPending(Booking booking){
        return !booking.getEndDate().isBefore(today);
    }

    // Get a list of overall pending bookings
    public LinkedList<Booking> getAllPendingBookings(){
        LinkedList<Booking> bookings = new LinkedList<>();

        for(Campsite campsite : campsiteSystem.getCampsites()){
            for(CampingArea campingArea : campsite.getCampingAreas()){
                for(Plot plot : campingArea.getPlots()){
                    for(Booking booking : pendingBookings(plot)){
                        bookings.add(booking);
                    }
                }
            }
        }

        return sortBookingsByStartDate(bookings);
    }

    // Get all pending bookings in a certain campsite
    public LinkedList<Booking> pendingBookings(Campsite campsite){
        LinkedList<Booking> bookings = new LinkedList<>();

        for(CampingArea campingArea : campsite.getCampingAreas()){
            for(Plot plot : campingArea.getPlots()){
                for(Booking booking : pendingBookings(plot)){
                    bookings.add(booking);
                }
            }
        }

        return sortBookingsByStartDate(bookings);
    }

    // Get a list of all pending bookings in a certain plot
    public LinkedList<Booking> pendingBookings(Plot plot){
        LinkedList<Booking> bookings = new LinkedList<>();

        for(Booking booking : plot.getBookings()){
            if(isPending(booking)){
                bookings.add(booking);
            }
        }

        return sortBookingsByStartDate(bookings);
    }

    // Get a list all bookings in the whole system
    public LinkedList<Booking> getAllBookings(){
        LinkedList<Booking> bookings = new LinkedList<>();

        for(Campsite campsite : campsiteSystem.getCampsites()){
            for(CampingArea campingArea : campsite.getCampingAreas()){
                for(Plot plot : campingArea.getPlots()){
                    for(Booking booking : plot.getBookings()){
                        bookings.add(booking);
                    }
                }
            }
        }

        return sortBookingsByStartDate(bookings);
    }

    // Get a list all bookings in a target campsite
    public LinkedList<Booking> getAllBookings(Campsite campsite){
        LinkedList<Booking> bookings = new LinkedList<>();

        for(CampingArea campingArea : campsite.getCampingAreas()){
            for(Plot plot : campingArea.getPlots()){
                for(Booking booking : plot.getBookings()){
                    bookings.add(booking);
                }
            }
        }

        return sortBookingsByStartDate(bookings);
    }

    // Get a list of all bookings made by a certain guest
    public LinkedList<Booking> bookingsForGuest(int guestId){
        Guest guest = campsiteSystem.findGuest(guestId);

        if(guest == null){
            return new LinkedList<>();
        }

        return sortBookingsByStartDate(guest.getBookings());
    }

    /* SORTING */

    private LinkedList<Booking> sortBookingsByStartDate(LinkedList<Booking> bookings){
        // empty a new linked list instead of emptying the actual bookings linked list
        LinkedList<Booking> bookingsLeft = new LinkedList<>();
        for(Booking booking : bookings){
            bookingsLeft.add(booking);
        }

        LinkedList<Booking> sortedList = new LinkedList<>();

        while(sortedList.size() != bookings.size()){
            Booking earliest = null;

            for(Booking booking : bookingsLeft){
                if(earliest == null || booking.getStartDate().isBefore(earliest.getStartDate())){
                    earliest = booking;
                }
            }

            sortedList.add(earliest);
            bookingsLeft.remove(earliest);
        }

        return sortedList;
    }
}
