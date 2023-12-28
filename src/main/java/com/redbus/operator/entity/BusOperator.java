package com.redbus.operator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name="bus_operators")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperator {
    @Id
    private String busId;
    @Column(name = "bus_number")
    private String busNumber;
    @Column(name = "bus_operator_company_name")
    private String busOperatorCompanyName;
    @Column(name = "driver_name")
    private String driverName;
    @Column(name = "support_staff")
    private String supportStaff;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "departure_city")
    private String departureCity;
    @Column(name = "arrival_city")
    private String arrivalCity;
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

    @JsonIgnore
    @OneToOne(mappedBy = "busOperator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TicketCost ticketCost;
}
