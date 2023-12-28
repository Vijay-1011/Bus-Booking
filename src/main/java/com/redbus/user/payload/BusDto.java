package com.redbus.user.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {
    private String busId;

    private String busNumber;

    private String busOperatorCompanyName;


    private int numberOfSeats;

    private String departureCity;

    private String arrivalCity;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate departureDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate arrivalDate;


    private double totalTravelTime;

    private String busType;


}
