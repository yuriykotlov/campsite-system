package org.example.managers;

import org.example.data_structures.LinkedList;
import org.example.model.*;
import org.example.util.StringChecker;

import java.time.LocalDate;

public class GuestRecord {
    private final Guest guest;

    private LinkedList<Booking> pendingBookings = new LinkedList<>();
    private double totalPendingCost;

    private LinkedList<Booking> pastBookings = new LinkedList<>();
    private double totalPastCost;

    public GuestRecord(Guest guest, LocalDate today){
        this.guest = guest;
    }

}
