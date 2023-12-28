package com.redbus.operator.repository;

import com.redbus.operator.entity.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BusOperatorRepository extends JpaRepository<BusOperator,String> {

    List<BusOperator> findByDepartureCityAndArrivalCityAndDepartureDate(String departureCity, String arrivalCity, LocalDate departureDate);
}

