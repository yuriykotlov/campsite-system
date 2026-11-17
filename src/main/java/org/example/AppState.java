package org.example;

import org.example.managers.BookingService;
import org.example.managers.CampsiteSystem;

import java.time.LocalDate;

public class AppState {
    private static final CampsiteSystem campsiteSystem = new CampsiteSystem();
    private static final BookingService bookingService = new BookingService(campsiteSystem, LocalDate.now());

    public static CampsiteSystem getSystem(){ return campsiteSystem; }
    public static BookingService getBookingService(){ return bookingService; }
}
