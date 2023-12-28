package com.redbus.user.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusBookingDto {

    private String bookingId;
    private String fromCity;
    private String toCity;
    private String busNumber;
    private String busCompanyName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private double totalTravelTime;
    private String busType;
    private String amenities;
    private double ticketPrice;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String bookingStatus;

}
