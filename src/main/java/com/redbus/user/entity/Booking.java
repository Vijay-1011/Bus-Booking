package com.redbus.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @Column(name = "booking_id")
    private String bookingId;
    @Column(name = "bus_id")
    private String busId;
    @Column(name = "ticket_id")
    private long ticketId;
    @Column(name = "from_city")
    private String fromCity;
    @Column(name = "to_city")
    private String toCity;
    @Column(name = "bus_number")
    private String busNumber;
    @Column(name = "bus_company_name")
    private String busCompanyName;
    @Column(name = "departure_time")
    private LocalTime departureTime;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @Column(name = "departure_date")
    private LocalDate departureDate;
    @Column(name = "arrival_date")
    private LocalDate arrivalDate;
    @Column(name = "total_travel_time")
    private double totalTravelTime;
    @Column(name = "bus_type")
    private String busType;
    @Column(name = "amenities")
    private String amenities;
    @Column(name = "ticket_price")
    private double ticketPrice;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String mobile;


}
